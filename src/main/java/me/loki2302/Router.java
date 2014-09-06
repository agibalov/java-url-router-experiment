package me.loki2302;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    private Map<Route, String> routes = new HashMap<Route, String>();

    public Router addRoute(Route route, String description) {
        routes.put(route, description);
        return this;
    }

    public RouteResolutionResult resolve(String url) {
        List<RouteMatchResult> routeMatches = new ArrayList<RouteMatchResult>();
        for(Route route : routes.keySet()) {
            RouteMatchResult routeMatchResult = route.match(url);
            if(!routeMatchResult.match) {
                continue;
            }

            routeMatches.add(routeMatchResult);
        }

        if(routeMatches.isEmpty()) {
            return RouteResolutionResult.noMatchingRoutes();
        }

        if(routeMatches.size() > 1) {
            return RouteResolutionResult.multipleMatchingRoutes();
        }

        RouteMatchResult singleResult = routeMatches.get(0);
        return RouteResolutionResult.singleMatchingRoute(singleResult.route, singleResult.context);
    }

}
