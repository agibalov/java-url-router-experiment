package me.loki2302;

import me.loki2302.framework.handling.RouteHandler;

import java.util.Collections;
import java.util.Map;

import static me.loki2302.framework.results.mav.ModelAndView.modelAndView;

public class PageRouteHandler implements RouteHandler {
    @Override
    public Object handle(Map<String, Object> context) {
        return modelAndView(
                Collections.singletonMap("pageId", context.get("id")),
                "page");
    }
}
