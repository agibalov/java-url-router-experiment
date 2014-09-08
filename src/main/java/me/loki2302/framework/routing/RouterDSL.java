package me.loki2302.framework.routing;

public final class RouterDSL {
    public static Route route(RouteSegment... routeSegment) {
        return new Route(routeSegment);
    }

    public static RouteSegment c(String value) {
        return new ConstRouteSegment(value);
    }

    public static RouteSegment v(String name) {
        return new VarRouteSegment(name);
    }
}
