package me.loki2302.results.mav;

import me.loki2302.results.HandlerResultProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ModelAndViewHandlerResultProcessor implements HandlerResultProcessor {
    private final String prefix;
    private final String suffix;

    public ModelAndViewHandlerResultProcessor(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public boolean canProcess(Object result) {
        return result != null && result instanceof ModelAndView;
    }

    @Override
    public void process(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ModelAndView modelAndView = (ModelAndView)result;
        Map<String, Object> model = modelAndView.model;
        request.setAttribute("model", model);

        String view = modelAndView.view;
        RequestDispatcher jspRequestDispatcher = request.getRequestDispatcher(prefix + view + suffix);

        response.setStatus(HttpServletResponse.SC_OK);
        jspRequestDispatcher.forward(request, response);
    }
}
