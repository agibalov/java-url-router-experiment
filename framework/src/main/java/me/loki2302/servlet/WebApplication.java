package me.loki2302.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import me.loki2302.handling.RouteHandler;
import me.loki2302.results.HandlerResultProcessorRegistry;
import me.loki2302.routing.Router;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

public abstract class WebApplication implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Injector injector = Guice.createInjector(new WebApplicationModule());

        Router<Class<? extends RouteHandler>> router =
                injector.getInstance(
                        Key.get(new TypeLiteral<Router<Class<? extends RouteHandler>>>() {
                        }));
        configureRoutes(router);

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

    protected abstract void configureRoutes(Router<Class<? extends RouteHandler>> router);
    protected abstract void configureResultProcessors(HandlerResultProcessorRegistry registry);
}
