package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.loki2302.framework.RouteHandler;
import me.loki2302.framework.routing.Router;

import static me.loki2302.framework.routing.RouterDSL.*;

public class DummyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DummyServlet.class).asEagerSingleton();

        Router<Class<? extends RouteHandler>> router = new Router<Class<? extends RouteHandler>>()
                .addRoute(route(c("")), IndexRouteHandler.class)
                .addRoute(route(c("page"), v("id")), PageRouteHandler.class);
        bind(new TypeLiteral<Router<Class<? extends RouteHandler>>>() {}).toInstance(router);

        bind(IndexRouteHandler.class).asEagerSingleton();
        bind(PageRouteHandler.class).asEagerSingleton();
    }
}
