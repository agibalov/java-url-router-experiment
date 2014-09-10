package me.loki2302.framework;

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
