package me.loki2302.handling;

import me.loki2302.context.RequestContext;

public class ResourceRouteHandler implements RouteHandler {
    private final String root;
    private final String pathParamName;

    public ResourceRouteHandler(String root, String pathParamName) {
        this.root = root;
        this.pathParamName = pathParamName;
    }

    @Override
    public Object handle(RequestContext requestContext) {
        String path = root + requestContext.pathParams.get(pathParamName);
        return getClass().getResourceAsStream(path);
    }
}
