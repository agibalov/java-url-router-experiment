package me.loki2302.handling.convention;

import me.loki2302.routing.advanced.RequestMethod;

import java.lang.reflect.Method;

public class ControllerMethodMeta {
    private final Method method;
    private final RequestMethod requestMethod;
    private final String path;

    public ControllerMethodMeta(
            Method method,
            RequestMethod requestMethod,
            String path) {

        this.method = method;
        this.requestMethod = requestMethod;
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }
}
