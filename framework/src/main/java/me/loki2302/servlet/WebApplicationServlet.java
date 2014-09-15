package me.loki2302.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import me.loki2302.context.FormContextModule;
import me.loki2302.context.PathContextModule;
import me.loki2302.context.QueryContextModule;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessor;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.RouteResolutionResult;
import me.loki2302.routing.Router;
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
    private final Router<Class<? extends RouteHandler>> router;
    private final HandlerResultProcessorRegistry handlerResultProcessorRegistry;

    @Inject
    public WebApplicationServlet(
            Injector injector,
            Router<Class<? extends RouteHandler>> router,
            HandlerResultProcessorRegistry handlerResultProcessorRegistry) {

        this.injector = injector;
        this.router = router;
        this.handlerResultProcessorRegistry = handlerResultProcessorRegistry;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        RouteResolutionResult<Class<? extends RouteHandler>> routeResolutionResult = router.resolve(requestURI);
        if(!routeResolutionResult.resolved) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Didn't resolve the route");
            printWriter.close();
            return;
        }

        Class<? extends RouteHandler> handlerClass = routeResolutionResult.handler;
        Module handlerModule = makeHandlerModule(handlerClass);

        PathContextModule pathContextModule = makePathContextModule(routeResolutionResult.context);
        FormContextModule formContextModule = makeFormContextModule(req);
        QueryContextModule queryContextModule = makeQueryContextModule(req);
        Injector requestContextInjector = injector.createChildInjector(
                handlerModule,
                pathContextModule,
                formContextModule,
                queryContextModule);

        RouteHandler routeHandler = requestContextInjector.getInstance(handlerClass);
        Object handlerResult = routeHandler.handle();
        HandlerResultProcessor handlerResultProcessor = handlerResultProcessorRegistry.resolve(handlerResult);
        if(handlerResultProcessor == null) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Failed to resolve result processor");
            printWriter.close();
        }

        handlerResultProcessor.process(handlerResult, req, resp);
    }

    private static PathContextModule makePathContextModule(Map<String, Object> pathContext) {
        PathContextModule pathContextModule = new PathContextModule(pathContext);
        return pathContextModule;
    }

    private static FormContextModule makeFormContextModule(HttpServletRequest req) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, String> formContext = new HashMap<String, String>();
        for(String parameterName : parameterMap.keySet()) {
            String[] parameterValues = parameterMap.get(parameterName);
            if(parameterValues.length != 1) {
                throw new RuntimeException("Don't know how to handle multiple parameter values");
            }

            String singleValue = parameterValues[0];
            formContext.put(parameterName, singleValue);
        }

        FormContextModule formContextModule = new FormContextModule(formContext);
        return formContextModule;
    }

    private static QueryContextModule makeQueryContextModule(HttpServletRequest req) {
        String queryString = req.getQueryString();
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
        Map<String, String> queryMap = new HashMap<String, String>();
        for(NameValuePair nameValuePair : nameValuePairs) {
            queryMap.put(nameValuePair.getName(), nameValuePair.getValue());
        }

        return new QueryContextModule(queryMap);
    }

    private static Module makeHandlerModule(final Class<?> handlerClass) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(handlerClass);
            }
        };
    }
}
