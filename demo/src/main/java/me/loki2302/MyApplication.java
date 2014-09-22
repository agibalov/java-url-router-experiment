package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import me.loki2302.servlet.WebApplication;
import me.loki2302.handling.ResourceRouteHandler;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.results.is.InputStreamHandlerResultProcessor;
import me.loki2302.results.mav.ModelAndViewHandlerResultProcessor;
import me.loki2302.routing.Router;

import javax.servlet.annotation.WebListener;

import java.util.Arrays;
import java.util.List;

import static me.loki2302.routing.advanced.AdvancedRouteDSL.*;

@WebListener
public class MyApplication extends WebApplication {
    @Override
    protected List<Module> provideModules() {
        return Arrays.<Module>asList(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IndexRouteHandler.class).asEagerSingleton();
                bind(PageRouteHandler.class).asEagerSingleton();
                bind(ResourceRouteHandler.class).annotatedWith(Names.named("res")).toInstance(new ResourceRouteHandler("/assets", "path"));
            }
        });
    }

    @Override
    protected void configureRoutes(Router<Key<? extends RouteHandler>> router) {
        router.addRoute(route(""), Key.get(IndexRouteHandler.class));
        router.addRoute(route("page/:id"), Key.get(PageRouteHandler.class));
        router.addRoute(route("static/*path"), Key.get(ResourceRouteHandler.class, Names.named("res")));
    }

    @Override
    protected void configureResultProcessors(HandlerResultProcessorRegistry registry) {
        registry.register(new ModelAndViewHandlerResultProcessor("/WEB-INF/", ".jsp"));
        registry.register(new InputStreamHandlerResultProcessor());
    }
}
