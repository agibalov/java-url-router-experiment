package me.loki2302.handling.convention;

import java.lang.reflect.Method;

public class ControllerMethodMeta {
    private final Method method;
    private final String path;

    public ControllerMethodMeta(
            Method method,
            String path) {

        this.method = method;
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
