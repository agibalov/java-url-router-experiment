package me.loki2302;

import com.google.inject.AbstractModule;
import me.loki2302.routing.Router;

import static me.loki2302.routing.RouterDSL.c;
import static me.loki2302.routing.RouterDSL.route;
import static me.loki2302.routing.RouterDSL.v;

public class DummyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DummyServlet.class).asEagerSingleton();

        Router router = new Router()
                .addRoute(route(c("")), "Root")
                .addRoute(route(c("page1")), "Page 1")
                .addRoute(route(c("page2")), "Page 2")
                .addRoute(route(c("extras")), "Extras with no ID")
                .addRoute(route(c("extras"), v("id")), "Extras with ID");
        bind(Router.class).toInstance(router);
    }
}
