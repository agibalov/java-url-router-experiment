package me.loki2302;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.loki2302.framework.*;
import me.loki2302.framework.routing.RouteResolutionResult;
import me.loki2302.framework.routing.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

        Map<String, Object> context = routeResolutionResult.context;
        Class<? extends RouteHandler> handlerClass = routeResolutionResult.handler;
        RouteHandler routeHandler = injector.getInstance(handlerClass);
        Object handlerResult = routeHandler.handle(context);
        HandlerResultProcessor handlerResultProcessor = handlerResultProcessorRegistry.resolve(handlerResult);
        if(handlerResultProcessor == null) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Failed to resolve result processor");
            printWriter.close();
        }

        handlerResultProcessor.process(handlerResult, req, resp);
    }
}
