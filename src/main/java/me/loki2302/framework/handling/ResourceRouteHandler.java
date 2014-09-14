package me.loki2302.framework.handling;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ResourceRouteHandler implements RouteHandler {
    @Inject
    @Named("path-path")
    private String relativePath;

    // TODO: get rid of it
    private final String root = "/assets";

    @Override
    public Object handle() {
        if(relativePath == null) {
            throw new RuntimeException("There's no path");
        }

        return getClass().getResourceAsStream(root + relativePath);
    }
}
