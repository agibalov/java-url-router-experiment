package me.loki2302;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static me.loki2302.RouterDSL.*;
import static org.junit.Assert.*;

public class AppTest {
    private final static Router router = new Router()
        .addRoute(route(c("api"), c("posts"), v("id"), c("comments")), "comments")
        .addRoute(route(c("api"), c("posts"), v("id")), "post");

    @Test
    public void canResolveRouteByUrl() {
        RouteResolutionResult r = router.resolve("api/posts/123");
        assertTrue(r.resolved);
        assertEquals(1, r.context.size());
        assertEquals("123", r.context.get("id"));

        r = router.resolve("api/posts/123/comments");
        assertTrue(r.resolved);
        assertEquals(1, r.context.size());
        assertEquals("123", r.context.get("id"));

        r = router.resolve("api/posts");
        assertNull(r);
    }

    @Test
    public void canBuildUrlFromRoute() {
        Route route = route(c("api"), c("posts"), v("id"), c("comments"));
        Map<String, Object> context = Collections.<String, Object>singletonMap("id", 123);
        String url = route.build(context);
        assertEquals("api/posts/123/comments/", url);
    }
}
