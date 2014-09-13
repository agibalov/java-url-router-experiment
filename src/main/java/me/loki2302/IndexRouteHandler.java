package me.loki2302;

import me.loki2302.framework.handling.RouteHandler;

import java.util.HashMap;
import java.util.Map;

import static me.loki2302.framework.results.mav.ModelAndView.modelAndView;

public class IndexRouteHandler implements RouteHandler {
    @Override
    public Object handle(Map<String, Object> pathContext, Map<String, String> formContext) {
        Map<String, Object> model = new HashMap<String, Object>();
        if(formContext.containsKey("message")) {
            model.put("message", formContext.get("message"));
        }

        return modelAndView(model, "index");
    }
}
