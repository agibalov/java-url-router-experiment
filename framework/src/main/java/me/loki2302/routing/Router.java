package me.loki2302.routing;

import me.loki2302.handling.RouteHandler;
import me.loki2302.handling.convention.*;
import me.loki2302.routing.advanced.PartMatcher;
import me.loki2302.routing.advanced.Route;
import me.loki2302.routing.advanced.RouteParser;
import me.loki2302.handling.convention.framework.HandlerReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    private final RouteParser routeParser;
    private final Map<Route, RouteHandlerResolver> routes = new HashMap<Route, RouteHandlerResolver>();
    private final HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler> handlerReader;

    public Router(RouteParser routeParser) {
        this.routeParser = routeParser;
        ControllerMetadataReader controllerMetadataReader = new ControllerMetadataReader();
        handlerReader = new HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler>(controllerMetadataReader);
    }

    public void addRoute(String routeString, Class<? extends RouteHandler> handlerClass) {
        List<PartMatcher> partMatchers = routeParser.parse(routeString);
        Route route = new Route(partMatchers);
        RouteHandlerResolver handlerClassRouteHandlerResolver =
                new HandlerClassRouteHandlerResolver(handlerClass);
        routes.put(route, handlerClassRouteHandlerResolver);
    }

    public void addRoute(String routeString, RouteHandler handlerInstance) {
        List<PartMatcher> partMatchers = routeParser.parse(routeString);
        Route route = new Route(partMatchers);
        RouteHandlerResolver handlerInstanceHandlerResolver =
                new HandlerInstanceHandlerResolver(handlerInstance);
        routes.put(route, handlerInstanceHandlerResolver);
    }

    public void addController(Class<?> controllerClass) {
        List<ControllerMethodHandler> handlers = handlerReader.readClass(controllerClass);
        for(ControllerMethodHandler handler : handlers) {
            String routeString = handler.getPath();
            addRoute(routeString, handler);
        }
    }

    public RouteResolutionResult<RouteHandlerResolver> resolve(String url) {
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
        RouteHandlerResolver routeHandlerResolver = routes.get(singleResult.route);
        return RouteResolutionResult.singleMatchingRoute(
                routeHandlerResolver,
                singleResult.route,
                singleResult.context);
    }
}
