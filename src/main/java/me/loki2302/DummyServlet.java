package me.loki2302;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.loki2302.framework.ModelAndView;
import me.loki2302.framework.RouteHandler;
import me.loki2302.framework.routing.RouteResolutionResult;
import me.loki2302.framework.routing.Router;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class DummyServlet extends HttpServlet {
    private final Injector injector;
    private final Router<Class<? extends RouteHandler>> router;

    @Inject
    public DummyServlet(Injector injector, Router<Class<? extends RouteHandler>> router) {
        this.injector = injector;
        this.router = router;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        RouteResolutionResult<Class<? extends RouteHandler>> routeResolutionResult = router.resolve(requestURI);
        if(!routeResolutionResult.resolved) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Didn't resolve the route");
            printWriter.close();
            return;
        }

        Map<String, Object> context = routeResolutionResult.context;
        Class<? extends RouteHandler> handlerClass = routeResolutionResult.handler;
        RouteHandler routeHandler = injector.getInstance(handlerClass);
        ModelAndView modelAndView = routeHandler.handle(context);

        Map<String, Object> model = modelAndView.model;
        req.setAttribute("model", model);

        String view = modelAndView.view;
        RequestDispatcher jspRequestDispatcher = req.getRequestDispatcher("/WEB-INF/" + view + ".jsp");

        resp.setStatus(HttpServletResponse.SC_OK);
        jspRequestDispatcher.forward(req, resp);
    }
}
