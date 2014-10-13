package me.loki2302.routing;

import com.google.inject.Injector;
import me.loki2302.handling.RouteHandler;

public class HandlerClassRouteHandlerResolver implements RouteHandlerResolver {
    private final Class<? extends RouteHandler> routeHandlerClass;

    public HandlerClassRouteHandlerResolver(Class<? extends RouteHandler> routeHandlerClass) {
        this.routeHandlerClass = routeHandlerClass;
    }

    @Override
    public RouteHandler getRouteHandler(Injector injector) {
        return injector.getInstance(routeHandlerClass);
    }
}
