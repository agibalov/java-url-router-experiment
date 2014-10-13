package me.loki2302.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.Router;
import me.loki2302.springly.HandlerMethodArgumentResolverRegistry;
import me.loki2302.springly.web.ControllerParameterMeta;

public class WebApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebApplicationServlet.class)
                .asEagerSingleton();

        bind(new TypeLiteral<Router<Key<? extends RouteHandler>>>() {})
                .toInstance(new Router<Key<? extends RouteHandler>>());

        bind(Key.get(new TypeLiteral<HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>>() {}))
                .toInstance(new HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>());

        bind(HandlerResultProcessorRegistry.class)
                .asEagerSingleton();
    }
}
