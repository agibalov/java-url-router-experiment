package me.loki2302.handling.convention;

import me.loki2302.routing.advanced.RequestMethod;

import java.lang.reflect.Method;

public class ControllerMethodMeta {
    private final Method method;
    private final RequestMethod[] requestMethods;
    private final String path;

    public ControllerMethodMeta(
            Method method,
            RequestMethod[] requestMethods,
            String path) {

        this.method = method;
        this.requestMethods = requestMethods;
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public RequestMethod[] getRequestMethod() {
        return requestMethods;
    }

    public String getPath() {
        return path;
    }
}
