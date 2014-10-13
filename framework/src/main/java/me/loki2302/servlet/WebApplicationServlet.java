package me.loki2302.servlet;

import com.google.inject.*;
import me.loki2302.context.*;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessor;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.RouteResolutionResult;
import me.loki2302.routing.Router;
import me.loki2302.springly.web.ControllerMethodHandler;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

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
    private final Injector injector;
    private final Router<Key<? extends RouteHandler>> router;
    private final HandlerResultProcessorRegistry handlerResultProcessorRegistry;

    @Inject
    public WebApplicationServlet(
            Injector injector,
            Router<Key<? extends RouteHandler>> router,
            HandlerResultProcessorRegistry handlerResultProcessorRegistry) {

        this.injector = injector;
        this.router = router;
        this.handlerResultProcessorRegistry = handlerResultProcessorRegistry;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestURI = req.getRequestURI();

            RouteResolutionResult<Key<? extends RouteHandler>> routeResolutionResult =
                    router.resolve(requestURI);
            if (!routeResolutionResult.resolved) {
                throw new RouteNotFoundException();
            }

            Object handlerObject = routeResolutionResult.handler;
            RouteHandler routeHandler = null;
            if(handlerObject instanceof Key) {
                Key<? extends RouteHandler> handlerKey = routeResolutionResult.handler;
                routeHandler = injector.getInstance(handlerKey);
            } else if(handlerObject instanceof ControllerMethodHandler) {
                routeHandler =  (RouteHandler)handlerObject;
            } else {
                throw new RuntimeException("Unknown handlerObject");
            }

            RequestContext requestContext = new RequestContext();
            requestContext.injector = injector;
            requestContext.pathParams = makePathParams(routeResolutionResult.context);
            requestContext.queryParams = makeQueryParams(req);
            requestContext.formParams = makeFormParams(req);
            Object handlerResult = routeHandler.handle(requestContext);

            HandlerResultProcessor handlerResultProcessor =
                    handlerResultProcessorRegistry.resolve(handlerResult);
            if (handlerResultProcessor == null) {
                throw new ResultProcessorNotFoundException();
            }

            handlerResultProcessor.process(handlerResult, req, resp);

        } catch(RouteNotFoundException e) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Didn't resolve the route");
            printWriter.close();
        } catch(ResultProcessorNotFoundException e) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Failed to resolve result processor");
            printWriter.close();
        } catch(RuntimeException e) {
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
