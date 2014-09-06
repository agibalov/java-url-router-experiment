package me.loki2302;

import me.loki2302.routing.RouteResolutionResult;
import me.loki2302.routing.Router;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static me.loki2302.routing.RouterDSL.*;

@WebServlet(name = "dummy", urlPatterns = "/*")
public class DummyServlet extends HttpServlet {
    private final Router router = new Router()
            .addRoute(route(c("")), "Root")
            .addRoute(route(c("page1")), "Page 1")
            .addRoute(route(c("page2")), "Page 2")
            .addRoute(route(c("extras")), "Extras with no ID")
            .addRoute(route(c("extras"), v("id")), "Extras with ID");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.printf("hello there\n");
        printWriter.printf("method: %s\n", req.getMethod());
        printWriter.printf("path info: %s\n", req.getPathInfo());
        printWriter.printf("path translated: %s\n", req.getPathTranslated());
        printWriter.printf("context path: %s\n", req.getContextPath());
        printWriter.printf("query string: %s\n", req.getQueryString());
        printWriter.printf("request uri: %s\n", req.getRequestURI());
        printWriter.printf("request url: %s\n", req.getRequestURL());
        printWriter.printf("servlet path: %s\n", req.getServletPath());
        printWriter.printf("remote user: %s\n", req.getRemoteUser());
        printWriter.printf("remote addr: %s\n", req.getRemoteAddr());
        printWriter.println();

        Map<String, String[]> parameterMap = req.getParameterMap();
        for(String key : parameterMap.keySet()) {
            printWriter.printf("%s: ", key);

            String[] values = parameterMap.get(key);
            for(String value : values) {
                printWriter.printf("'%s', ", value);
            }

            printWriter.println();
        }

        RouteResolutionResult routeResolutionResult = router.resolve(req.getPathInfo());
        if(!routeResolutionResult.resolved) {
            printWriter.println("Didn't resolve the route");
        } else {
            printWriter.printf("Resolved route is '%s'\n", routeResolutionResult.description);

            Map<String, Object> context = routeResolutionResult.context;
            if(context.isEmpty()) {
                printWriter.println("Context is empty");
            } else {
                printWriter.println("Context is:");
                for (String key : context.keySet()) {
                    printWriter.printf("  '%s': '%s'\n", key, context.get(key));
                }
            }
        }

        printWriter.close();
    }
}