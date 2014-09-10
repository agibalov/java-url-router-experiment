package me.loki2302.framework;

import java.util.Map;

public interface RouteHandler {
    Object handle(Map<String, Object> context);
}
