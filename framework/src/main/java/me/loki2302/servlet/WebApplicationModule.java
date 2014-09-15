package me.loki2302.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.Router;

public class WebApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebApplicationServlet.class)
                .asEagerSingleton();

        bind(new TypeLiteral<Router<Class<? extends RouteHandler>>>() {})
                .toInstance(new Router<Class<? extends RouteHandler>>());

        bind(HandlerResultProcessorRegistry.class)
                .asEagerSingleton();
    }
}
