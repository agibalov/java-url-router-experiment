package me.loki2302;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<Route, String> routes = new HashMap<Route, String>();

    public Router addRoute(Route route, String description) {
        routes.put(route, description);
        return this;
    }

    public String resolve(String url) {
        for(Route route : routes.keySet()) {
            if(route.match(url)) {
                return routes.get(route);
            }
        }

        return null;
    }
}
