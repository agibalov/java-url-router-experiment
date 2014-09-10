package me.loki2302.framework;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamHandlerResultProcessor implements HandlerResultProcessor {
    @Override
    public boolean canProcess(Object result) {
        return result != null && result instanceof InputStream;
    }

    @Override
    public void process(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = (InputStream)result;
        try {
            IOUtils.copy(inputStream, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
