package me.loki2302.framework.handling;

import java.util.Map;

public interface RouteHandler {
    Object handle(Map<String, Object> pathContext, Map<String, String> formContext);
}
