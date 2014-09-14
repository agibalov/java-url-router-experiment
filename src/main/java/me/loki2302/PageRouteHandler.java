package me.loki2302;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.loki2302.framework.handling.RouteHandler;

import java.util.Collections;

import static me.loki2302.framework.results.mav.ModelAndView.modelAndView;

public class PageRouteHandler implements RouteHandler {
    @Inject
    @Named("path-id")
    private String id;

    @Override
    public Object handle() {
        return modelAndView(
                Collections.singletonMap("pageId", (Object)id),
                "page");
    }
}
