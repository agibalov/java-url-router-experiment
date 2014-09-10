package me.loki2302;

import me.loki2302.framework.handling.RouteHandler;

import java.util.Map;

public class ResourceRouteHandler implements RouteHandler {
    @Override
    public Object handle(Map<String, Object> context) {
        return getClass().getResourceAsStream("/style.css");
    }
}
