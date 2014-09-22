package me.loki2302;

import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;

import java.util.Collections;

import static me.loki2302.results.mav.ModelAndView.modelAndView;

public class PageRouteHandler implements RouteHandler {
    @Override
    public Object handle(RequestContext requestContext) {
        Object id = requestContext.pathParams.get("id");
        return modelAndView(
                Collections.singletonMap("pageId", (Object)id),
                "page");
    }
}
