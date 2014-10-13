package me.loki2302.routing;

import me.loki2302.routing.advanced.Route;

import java.util.Map;

public class RouteResolutionResult {
    public boolean resolved;
    public RouteHandlerResolver handlerResolver;
    public Route route;
    public Map<String, Object> context;

    public static RouteResolutionResult noMatchingRoutes() {
        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = false;
        return result;
    }

    public static RouteResolutionResult multipleMatchingRoutes() {
        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = false;
        return result;
    }

    public static RouteResolutionResult singleMatchingRoute(
            RouteHandlerResolver handlerResolver,
            Route route,
            Map<String, Object> context) {

        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = true;
        result.handlerResolver = handlerResolver;
        result.route = route;
        result.context = context;
        return result;
    }
}
