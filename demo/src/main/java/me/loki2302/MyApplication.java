package me.loki2302;

import me.loki2302.servlet.WebApplication;
import me.loki2302.handling.ResourceRouteHandler;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.results.is.InputStreamHandlerResultProcessor;
import me.loki2302.results.mav.ModelAndViewHandlerResultProcessor;
import me.loki2302.routing.Router;

import javax.servlet.annotation.WebListener;

import static me.loki2302.routing.advanced.AdvancedRouteDSL.*;

@WebListener
public class MyApplication extends WebApplication {
    @Override
    protected void configureRoutes(Router<Class<? extends RouteHandler>> router) {
        router.addRoute(route(""), IndexRouteHandler.class);
        router.addRoute(route("page/:id"), PageRouteHandler.class);
        router.addRoute(route("static/*path"), ResourceRouteHandler.class);
    }

    @Override
    protected void configureResultProcessors(HandlerResultProcessorRegistry registry) {
        registry.register(new ModelAndViewHandlerResultProcessor("/WEB-INF/", ".jsp"));
        registry.register(new InputStreamHandlerResultProcessor());
    }
}
