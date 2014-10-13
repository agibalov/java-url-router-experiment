package me.loki2302.servlet;

public class ResultProcessorNotFoundException extends RuntimeException {
    public final Object result;

    public ResultProcessorNotFoundException(Object result) {
        this.result = result;
    }
}
