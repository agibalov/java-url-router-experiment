package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import me.loki2302.context.RequestContext;
import me.loki2302.handling.ResourceRouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.results.is.InputStreamHandlerResultProcessor;
import me.loki2302.results.mav.ModelAndViewHandlerResultProcessor;
import me.loki2302.results.str.StringHandlerResultProcessor;
import me.loki2302.routing.Router;
import me.loki2302.servlet.WebApplication;
import me.loki2302.handling.convention.framework.HandlerMethodArgumentResolverRegistry;
import me.loki2302.handling.convention.ControllerParameterMeta;
import me.loki2302.handling.convention.PathParamArgumentResolver;
import me.loki2302.handling.convention.QueryParamArgumentResolver;

import javax.servlet.annotation.WebListener;
import java.util.Arrays;
import java.util.List;

@WebListener
public class MyApplication extends WebApplication {
    @Override
    protected List<Module> provideModules() {
        return Arrays.<Module>asList(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IndexRouteHandler.class).asEagerSingleton();
                bind(PageRouteHandler.class).asEagerSingleton();
                bind(HelloController.class).asEagerSingleton();
            }
        });
    }

    @Override
    protected void configureRoutes(Router router) {
        router.addRoute("", IndexRouteHandler.class);
        router.addRoute("page/:id", PageRouteHandler.class);
        router.addRoute("static/*path", new ResourceRouteHandler("/assets", "path"));
        router.addController(HelloController.class);
    }

    @Override
    protected void configureHandlerMethodArgumentResolvers(HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext> registry) {
        registry.register(new PathParamArgumentResolver());
        registry.register(new QueryParamArgumentResolver());
    }

    @Override
    protected void configureResultProcessors(HandlerResultProcessorRegistry registry) {
        registry.register(new ModelAndViewHandlerResultProcessor("/WEB-INF/", ".jsp"));
        registry.register(new InputStreamHandlerResultProcessor());
        registry.register(new StringHandlerResultProcessor());
    }
}
