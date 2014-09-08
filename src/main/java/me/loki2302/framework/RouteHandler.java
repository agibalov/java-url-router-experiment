package me.loki2302.framework;

import java.util.Map;

public interface RouteHandler {
    ModelAndView handle(Map<String, Object> context);
}
