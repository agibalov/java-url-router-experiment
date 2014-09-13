package me.loki2302.framework.routing.advanced;

import me.loki2302.framework.routing.Route;
import me.loki2302.framework.routing.RouteMatchResult;

import java.util.Map;

public class AdvancedRoute implements Route {


    @Override
    public RouteMatchResult match(String url) {
        String[] segments = url.split("/");

        return null;
    }

    @Override
    public String build(Map<String, Object> context) {
        throw new RuntimeException("Not implemented");
    }
}
