package me.loki2302;

import me.loki2302.springly.web.PathParam;
import me.loki2302.springly.web.QueryParam;
import me.loki2302.springly.web.RequestMapping;

public class HelloController {

    @RequestMapping(value = "controller")
    public String index(@QueryParam("id") String id) {
        return String.format("Hello controller, query param id = %s!", id);
    }

    @RequestMapping(value = "controller/:id")
    public String withPath(@PathParam("id") String id) {
        return String.format("Hello controller, path param id = %s!\n", id);
    }

}
