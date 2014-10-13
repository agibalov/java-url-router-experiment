package me.loki2302.servlet;

import com.google.inject.*;
import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.Router;
import me.loki2302.springly.HandlerMethodArgumentResolver;
import me.loki2302.springly.HandlerMethodArgumentResolverRegistry;
import me.loki2302.springly.web.ControllerParameterMeta;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;

public abstract class WebApplication implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<Module> modules = new ArrayList<Module>();
        modules.add(new WebApplicationModule());
        modules.addAll(provideModules());
        Injector injector = Guice.createInjector(modules.toArray(new Module[modules.size()]));

        Router<Key<? extends RouteHandler>> router =
                injector.getInstance(Key.get(new TypeLiteral<Router<Key<? extends RouteHandler>>>() {}));
        configureRoutes(router);

        HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext> handlerMethodArgumentResolverRegistry =
                injector.getInstance(Key.get(new TypeLiteral<HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>>() {}));
        configureHandlerMethodArgumentResolvers(handlerMethodArgumentResolverRegistry);

        HandlerResultProcessorRegistry resultProcessorRegistry =
                injector.getInstance(HandlerResultProcessorRegistry.class);
        configureResultProcessors(resultProcessorRegistry);

        WebApplicationServlet webApplicationServlet = injector.getInstance(WebApplicationServlet.class);

        ServletContext servletContext = sce.getServletContext();
        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet("webApplicationServlet", webApplicationServlet);
        servletRegistration.addMapping("/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    protected abstract List<Module> provideModules();
    protected abstract void configureRoutes(Router<Key<? extends RouteHandler>> router);
    protected abstract void configureHandlerMethodArgumentResolvers(HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext> handlerMethodArgumentResolverRegistry);
    protected abstract void configureResultProcessors(HandlerResultProcessorRegistry registry);
}
