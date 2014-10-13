package me.loki2302.routing;

import com.google.inject.Injector;
import me.loki2302.handling.RouteHandler;

public interface RouteHandlerResolver {
    RouteHandler getRouteHandler(Injector injector);
}
