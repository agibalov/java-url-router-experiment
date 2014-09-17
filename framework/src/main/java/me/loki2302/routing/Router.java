package me.loki2302.routing;

import me.loki2302.routing.advanced.AdvancedRouteDSL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router<THandler> {
    private Map<Route, THandler> routes = new HashMap<Route, THandler>();

    public void addRoute(Route route, THandler handler) {
        routes.put(route, handler);
    }

    public void addRoute(THandler handler) {
        if(!(handler instanceof Class<?>)) {
            throw new RuntimeException("handler is expected to be a Class");
        }

        Class<?> classHandler = (Class<?>)handler;
        RequestHandler requestHandler =
                classHandler.getAnnotation(RequestHandler.class);
        if(requestHandler == null) {
            throw new RuntimeException("Handler " + classHandler.getName() +
                    " is expected to have a " +
                    RequestHandler.class.getName() + " annotation");
        }

        String template = requestHandler.value();
        Route route = AdvancedRouteDSL.route(template);
        addRoute(route, handler);
    }

    public RouteResolutionResult<THandler> resolve(String url) {
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
        THandler handler = routes.get(singleResult.route);
        return RouteResolutionResult.singleMatchingRoute(
                handler,
                singleResult.route,
                singleResult.context);
    }
}
