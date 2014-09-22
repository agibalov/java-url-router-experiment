package me.loki2302;

import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;

import java.util.HashMap;
import java.util.Map;

import static me.loki2302.results.mav.ModelAndView.modelAndView;

public class IndexRouteHandler implements RouteHandler {
    @Override
    public Object handle(RequestContext requestContext) {
        String message = requestContext.formParams.get("message");
        String page = requestContext.queryParams.get("page");

        Map<String, Object> model = new HashMap<String, Object>();
        if(message != null) {
            model.put("message", message);
        }

        if(page != null) {
            model.put("page", page);
        }

        return modelAndView(model, "index");
    }
}
