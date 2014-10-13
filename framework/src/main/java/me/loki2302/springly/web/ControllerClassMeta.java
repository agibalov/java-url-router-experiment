package me.loki2302.springly.web;

public class ControllerClassMeta {
    private final Class<?> controllerClass;
    private final String path;

    public ControllerClassMeta(Class<?> controllerClass, String path) {
        this.controllerClass = controllerClass;
        this.path = path;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public String getPath() {
        return path;
    }
}
