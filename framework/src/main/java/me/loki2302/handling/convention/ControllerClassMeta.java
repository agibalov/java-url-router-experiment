package me.loki2302.handling.convention;

import me.loki2302.routing.advanced.RequestMethod;

public class ControllerClassMeta {
    private final Class<?> controllerClass;
    private final RequestMethod[] requestMethods;
    private final String path;

    public ControllerClassMeta(
            Class<?> controllerClass,
            RequestMethod[] requestMethods,
            String path) {

        this.controllerClass = controllerClass;
        this.requestMethods = requestMethods;
        this.path = path;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public String getPath() {
        return path;
    }
}
