package me.loki2302.framework.handling;

import java.util.Map;

public class ResourceRouteHandler implements RouteHandler {
    private final String pathContextVariableName;
    private final String root;

    public ResourceRouteHandler(String pathContextVariableName, String root) {
        this.pathContextVariableName = pathContextVariableName;
        this.root = root;
    }

    @Override
    public Object handle(Map<String, Object> context) {
        if(!context.containsKey(pathContextVariableName)) {
            throw new RuntimeException("Context doesn't have " + pathContextVariableName);
        }

        String relativePath = (String)context.get(pathContextVariableName);
        return getClass().getResourceAsStream(root + relativePath);
    }
}
