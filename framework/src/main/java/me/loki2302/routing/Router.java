package me.loki2302.routing;

import com.google.inject.Key;
import me.loki2302.handling.RouteHandler;
import me.loki2302.routing.advanced.AdvancedRouteDSL;
import me.loki2302.springly.HandlerReader;
import me.loki2302.springly.web.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router<THandler> {
    private final Map<Route, THandler> routes = new HashMap<Route, THandler>();
    private final HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler> handlerReader;

    public Router() {
        ControllerMetadataReader controllerMetadataReader = new ControllerMetadataReader();
        handlerReader = new HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler>(controllerMetadataReader);
    }

    public void addRoute(Route route, THandler handler) {
        routes.put(route, handler);
    }

    public void addController(Class<?> controllerClass) {
        List<ControllerMethodHandler> handlers = handlerReader.readClass(controllerClass);
        for(ControllerMethodHandler handler : handlers) {
            String routeString = handler.getPath();
            Route route = AdvancedRouteDSL.route(routeString);
            addRoute(route, (THandler)handler);
        }
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
