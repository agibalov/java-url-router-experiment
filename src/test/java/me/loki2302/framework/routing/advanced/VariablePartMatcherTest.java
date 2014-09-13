package me.loki2302.framework.routing.advanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariablePartMatcherTest {
    @Test
    public void canMatch() {
        VariablePartMatcher m = new VariablePartMatcher("name");
        PartMatchResult result = m.match(new String[]{ "hello" });
        assertTrue(result.match);
        assertEquals(1, result.segmentsConsumed);
        assertEquals(1, result.context.size());
        assertEquals("hello", result.context.get("name"));
    }
}
