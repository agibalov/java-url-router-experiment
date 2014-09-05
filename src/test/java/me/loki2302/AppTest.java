package me.loki2302;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static me.loki2302.RouterDSL.*;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void canResolveRouteByUrl() {
        Router router = new Router()
                .addRoute(route(c("api"), c("posts"), v("id"), c("comments")), "comments")
                .addRoute(route(c("api"), c("posts"), v("id")), "post");

        assertEquals("post", router.resolve("api/posts/123"));
        assertEquals("comments", router.resolve("api/posts/123/comments"));
        assertNull(router.resolve("api/posts"));
    }

    @Test
    public void canBuildUrlFromRoute() {
        Route route = route(c("api"), c("posts"), v("id"), c("comments"));
        Map<String, Object> context = Collections.<String, Object>singletonMap("id", 123);
        String url = route.build(context);
        assertEquals("api/posts/123/comments/", url);
    }
}
