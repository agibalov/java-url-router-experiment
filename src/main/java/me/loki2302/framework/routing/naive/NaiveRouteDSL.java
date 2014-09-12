package me.loki2302.framework.routing.naive;

import me.loki2302.framework.routing.Route;

public final class NaiveRouteDSL {
    public static Route route(RouteSegment... routeSegment) {
        return new NaiveRoute(routeSegment);
    }

    public static RouteSegment c(String value) {
        return new ConstRouteSegment(value);
    }

    public static RouteSegment v(String name) {
        return new VarRouteSegment(name);
    }
}
