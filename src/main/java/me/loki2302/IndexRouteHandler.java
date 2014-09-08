package me.loki2302;

import me.loki2302.framework.ModelAndView;
import me.loki2302.framework.RouteHandler;

import java.util.Collections;
import java.util.Map;

import static me.loki2302.framework.ModelAndView.modelAndView;

public class IndexRouteHandler implements RouteHandler {
    @Override
    public ModelAndView handle(Map<String, Object> context) {
        return modelAndView(null, "index");
    }

}
