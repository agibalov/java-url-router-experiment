package me.loki2302.results.str;

import me.loki2302.results.HandlerResultProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class StringHandlerResultProcessor implements HandlerResultProcessor {
    @Override
    public boolean canProcess(Object result) {
        return result instanceof String;
    }

    @Override
    public void process(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Writer writer = response.getWriter();
        writer.write((String)result);
    }
}
