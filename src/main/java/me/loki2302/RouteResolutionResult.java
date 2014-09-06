package me.loki2302;

import java.util.Map;

public class RouteResolutionResult {
    public boolean resolved;
    public Route route;
    public Map<String, Object> context;

    public static RouteResolutionResult noMatchingRoutes() {
        return null;
    }

    public static RouteResolutionResult multipleMatchingRoutes() {
        return null;
    }

    public static RouteResolutionResult singleMatchingRoute(Route route, Map<String, Object> context) {
        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = true;
        result.route = route;
        result.context = context;
        return result;
    }
}
