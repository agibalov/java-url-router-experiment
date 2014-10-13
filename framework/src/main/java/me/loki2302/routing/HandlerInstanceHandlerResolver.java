package me.loki2302.routing;

import com.google.inject.Injector;
import me.loki2302.handling.RouteHandler;

public class HandlerInstanceHandlerResolver implements RouteHandlerResolver {
    private final RouteHandler routeHandler;

    public HandlerInstanceHandlerResolver(RouteHandler routeHandler) {
        this.routeHandler = routeHandler;
    }

    @Override
    public RouteHandler getRouteHandler(Injector injector) {
        return routeHandler;
    }
}
