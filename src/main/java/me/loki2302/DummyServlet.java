package me.loki2302;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "dummy", urlPatterns = "/*")
public class DummyServlet extends HttpServlet {
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

        printWriter.close();
    }
}
