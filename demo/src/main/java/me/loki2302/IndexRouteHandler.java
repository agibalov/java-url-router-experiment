package me.loki2302;

import com.google.inject.Inject;
import me.loki2302.context.FormParam;
import me.loki2302.context.QueryParam;
import me.loki2302.handling.RouteHandler;

import java.util.HashMap;
import java.util.Map;

import static me.loki2302.results.mav.ModelAndView.modelAndView;

public class IndexRouteHandler implements RouteHandler {
    @Inject(optional = true)
    @FormParam("message")
    private String message;

    @Inject(optional = true)
    @QueryParam("page")
    private String page;

    @Override
    public Object handle() {
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
