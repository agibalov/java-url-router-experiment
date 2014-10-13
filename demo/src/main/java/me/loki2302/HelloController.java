package me.loki2302;

import me.loki2302.handling.convention.PathParam;
import me.loki2302.handling.convention.QueryParam;
import me.loki2302.handling.convention.RequestMapping;
import me.loki2302.results.mav.ModelAndView;

import java.util.Collections;

@RequestMapping(value = "controller")
public class HelloController {

    @RequestMapping(value = "")
    public ModelAndView index(@QueryParam("id") String id) {
        return ModelAndView.modelAndView(
                Collections.<String, Object>singletonMap("id", id),
                "controller-query");
    }

    @RequestMapping(value = "/:id")
    public ModelAndView withPath(@PathParam("id") String id) {
        return ModelAndView.modelAndView(
                Collections.<String, Object>singletonMap("id", id),
                "controller-path");
    }

}
