package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import me.loki2302.framework.FormContextModule;
import me.loki2302.framework.PathContextModule;
import me.loki2302.framework.handling.RouteHandler;
import me.loki2302.framework.results.HandlerResultProcessor;
import me.loki2302.framework.results.HandlerResultProcessorRegistry;
import me.loki2302.framework.results.is.InputStreamHandlerResultProcessor;
import me.loki2302.framework.results.mav.ModelAndViewHandlerResultProcessor;
import me.loki2302.framework.routing.RouteResolutionResult;
import me.loki2302.framework.routing.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DummyServlet extends HttpServlet {
    private final Injector injector;
    private final Router<Class<? extends RouteHandler>> router;
    private final HandlerResultProcessorRegistry handlerResultProcessorRegistry;

    @Inject
    public DummyServlet(
            Injector injector,
            Router<Class<? extends RouteHandler>> router,
            HandlerResultProcessorRegistry handlerResultProcessorRegistry) {

        this.injector = injector;
        this.router = router;
        this.handlerResultProcessorRegistry = handlerResultProcessorRegistry;

        // TODO: get rid of this asap
        handlerResultProcessorRegistry.register(injector.getInstance(ModelAndViewHandlerResultProcessor.class));
        handlerResultProcessorRegistry.register(injector.getInstance(InputStreamHandlerResultProcessor.class));
        //
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
        Injector requestContextInjector = injector.createChildInjector(
                handlerModule,
                pathContextModule,
                formContextModule);

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

    private static Module makeHandlerModule(final Class<?> handlerClass) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(handlerClass);
            }
        };
    }
}
