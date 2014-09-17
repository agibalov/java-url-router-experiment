package me.loki2302;

import com.google.inject.Inject;
import me.loki2302.context.PathParam;
import me.loki2302.handling.RouteHandler;
import me.loki2302.routing.RequestHandler;

import java.util.Collections;

import static me.loki2302.results.mav.ModelAndView.modelAndView;

@RequestHandler("page/:id")
public class PageRouteHandler implements RouteHandler {
    @Inject
    @PathParam("id")
    private String id;

    @Override
    public Object handle() {
        return modelAndView(
                Collections.singletonMap("pageId", (Object)id),
                "page");
    }
}
