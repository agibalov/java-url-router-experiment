package me.loki2302.framework.routing.advanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyPartMatcherTest {
    @Test
    public void canMatchAnyPath() {
        AnyPartMatcher m = new AnyPartMatcher("path");
        PartMatchResult result = m.match(new String[]{ "hello", "there", "123" });
        assertTrue(result.match);
        assertEquals(3, result.segmentsConsumed);
        assertEquals(1, result.context.size());
        assertEquals("/hello/there/123", result.context.get("path"));
    }
}
