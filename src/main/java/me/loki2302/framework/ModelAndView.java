package me.loki2302.framework;

import java.util.Map;

public class ModelAndView {
    public Map<String, Object> model;
    public String view;

    public static ModelAndView modelAndView(Map<String, Object> model, String view) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.model = model;
        modelAndView.view = view;
        return modelAndView;
    }
}
