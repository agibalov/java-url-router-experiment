package me.loki2302.routing;

import me.loki2302.routing.advanced.Route;

import java.util.Map;

public class RouteMatchResult {
    public boolean match;
    public Route route;
    public Map<String, Object> context;

    public static RouteMatchResult noMatch(Route route) {
        RouteMatchResult result = new RouteMatchResult();
        result.match = false;
        result.route = route;
        return result;
    }

    public static RouteMatchResult match(Route route, Map<String, Object> context) {
        RouteMatchResult result = new RouteMatchResult();
        result.match = true;
        result.route = route;
        result.context = context;
        return result;
    }
}
