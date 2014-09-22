package me.loki2302.handling;

import me.loki2302.context.RequestContext;

public interface RouteHandler {
    Object handle(RequestContext requestContext);
}
