package me.loki2302.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import me.loki2302.context.RequestContext;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.Router;
import me.loki2302.routing.advanced.RouteParser;
import me.loki2302.handling.convention.framework.HandlerMethodArgumentResolverRegistry;
import me.loki2302.handling.convention.ControllerParameterMeta;

public class WebApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebApplicationServlet.class)
                .asEagerSingleton();

        bind(Router.class)
                .toInstance(new Router(new RouteParser()));

        bind(Key.get(new TypeLiteral<HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>>() {}))
                .toInstance(new HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>());

        bind(HandlerResultProcessorRegistry.class)
                .asEagerSingleton();
    }
}
