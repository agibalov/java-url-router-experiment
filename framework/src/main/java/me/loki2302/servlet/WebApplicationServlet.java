package me.loki2302.servlet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessor;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.RouteHandlerResolver;
import me.loki2302.routing.RouteResolutionResult;
import me.loki2302.routing.Router;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebApplicationServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Injector injector;
    private final Router router;
    private final HandlerResultProcessorRegistry handlerResultProcessorRegistry;

    @Inject
    public WebApplicationServlet(
            Injector injector,
            Router router,
            HandlerResultProcessorRegistry handlerResultProcessorRegistry) {

        this.injector = injector;
        this.router = router;
        this.handlerResultProcessorRegistry = handlerResultProcessorRegistry;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        logger.info("Request: {}", requestURI);

        try {
            RouteResolutionResult<RouteHandlerResolver> routeResolutionResult = router.resolve(requestURI);
            if (!routeResolutionResult.resolved) {
                throw new RouteNotFoundException();
            }

            RouteHandlerResolver routeHandlerResolver = routeResolutionResult.handler;
            RouteHandler routeHandler = routeHandlerResolver.getRouteHandler(injector);
            logger.info("Resolved to request handler {}", routeHandler);

            RequestContext requestContext = new RequestContext();
            requestContext.injector = injector;
            requestContext.pathParams = makePathParams(routeResolutionResult.context);
            requestContext.queryParams = makeQueryParams(req);
            requestContext.formParams = makeFormParams(req);
            Object result = routeHandler.handle(requestContext);
            logger.info("{} returned {}", routeHandler, result);

            HandlerResultProcessor handlerResultProcessor = handlerResultProcessorRegistry.resolve(result);
            if (handlerResultProcessor == null) {
                throw new ResultProcessorNotFoundException(result);
            }

            logger.info("Resolved to result processor {}", handlerResultProcessor);
            handlerResultProcessor.process(result, req, resp);
        } catch(RouteNotFoundException e) {
            logger.warn("Don't know how to handle {}", requestURI);

            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Didn't resolve the route");
            printWriter.close();
        } catch(ResultProcessorNotFoundException e) {
            logger.warn("Failed to resolve result processor for {}", e.result);

            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Failed to resolve result processor");
            printWriter.close();
        } catch(RuntimeException e) {
            logger.warn("There was an internal error", e);

            PrintWriter printWriter = resp.getWriter();
            printWriter.println("There was an internal error");
            printWriter.println();
            e.printStackTrace(printWriter);
            printWriter.close();
        }
    }

    private static Map<String, Object> makePathParams(Map<String, Object> pathParams) {
        return pathParams;
    }

    private static Map<String, String> makeQueryParams(HttpServletRequest req) {
        String queryString = req.getQueryString();
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
        Map<String, String> queryParams = new HashMap<String, String>();
        for(NameValuePair nameValuePair : nameValuePairs) {
            queryParams.put(nameValuePair.getName(), nameValuePair.getValue());
        }

        return queryParams;
    }

    private static Map<String, String> makeFormParams(HttpServletRequest req) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, String> formParams = new HashMap<String, String>();
        for(String parameterName : parameterMap.keySet()) {
            String[] parameterValues = parameterMap.get(parameterName);
            if(parameterValues.length != 1) {
                throw new RuntimeException("Don't know how to handle multiple parameter values");
            }

            String singleValue = parameterValues[0];
            formParams.put(parameterName, singleValue);
        }

        return formParams;
    }
}
