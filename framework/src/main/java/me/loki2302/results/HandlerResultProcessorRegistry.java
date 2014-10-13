package me.loki2302.results;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class HandlerResultProcessorRegistry {
    private Set<HandlerResultProcessor> processors = new HashSet<HandlerResultProcessor>();

    public void register(HandlerResultProcessor processor) {
        processors.add(processor);
    }

    public HandlerResultProcessor resolve(Object result) {
        for(HandlerResultProcessor processor : processors) {
            if(processor.canProcess(result)) {
                return processor;
            }
        }

        return null;
    }

}
