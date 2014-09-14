package me.loki2302;

import com.google.inject.Inject;
import me.loki2302.framework.handling.RouteHandler;

import java.util.HashMap;
import java.util.Map;

import static me.loki2302.framework.results.mav.ModelAndView.modelAndView;

public class IndexRouteHandler implements RouteHandler {
    @Inject(optional = true)
    @FormParam("message")
    private String message;

    @Override
    public Object handle() {
        Map<String, Object> model = new HashMap<String, Object>();
        if(message != null) {
            model.put("message", message);
        }

        return modelAndView(model, "index");
    }
}
