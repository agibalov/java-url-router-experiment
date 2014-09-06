package me.loki2302.routing;

import java.util.Map;

public class RouteResolutionResult {
    public boolean resolved;
    public String description;
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

    public static RouteResolutionResult singleMatchingRoute(String description, Route route, Map<String, Object> context) {
        RouteResolutionResult result = new RouteResolutionResult();
        result.resolved = true;
        result.description = description;
        result.route = route;
        result.context = context;
        return result;
    }
}
