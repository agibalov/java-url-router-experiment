package me.loki2302.routing;

import java.util.Map;

public class RouteResolutionResult<THandler> {
    public boolean resolved;
    public THandler handler;
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

    public static <THandler> RouteResolutionResult singleMatchingRoute(THandler handler, Route route, Map<String, Object> context) {
        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = true;
        result.handler = handler;
        result.route = route;
        result.context = context;
        return result;
    }
}
