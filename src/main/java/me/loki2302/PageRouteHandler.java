package me.loki2302;

import me.loki2302.framework.handling.RouteHandler;

import java.util.Collections;
import java.util.Map;

import static me.loki2302.framework.results.mav.ModelAndView.modelAndView;

public class PageRouteHandler implements RouteHandler {
    @Override
    public Object handle(Map<String, Object> pathContext, Map<String, String> formContext) {
        return modelAndView(
                Collections.singletonMap("pageId", pathContext.get("id")),
                "page");
    }
}
