package me.loki2302;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class DummyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DummyServlet dummyServlet = new DummyServlet();

        ServletContext servletContext = sce.getServletContext();
        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet("dummyServlet", dummyServlet);
        servletRegistration.addMapping("/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
