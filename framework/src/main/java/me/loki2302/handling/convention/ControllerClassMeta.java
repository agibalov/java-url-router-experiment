package me.loki2302.handling.convention;

import me.loki2302.routing.advanced.RequestMethod;

public class ControllerClassMeta {
    private final Class<?> controllerClass;
    private final RequestMethod requestMethod;
    private final String path;

    public ControllerClassMeta(Class<?> controllerClass, RequestMethod requestMethod, String path) {
        this.controllerClass = controllerClass;
        this.requestMethod = requestMethod;
        this.path = path;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }
}
