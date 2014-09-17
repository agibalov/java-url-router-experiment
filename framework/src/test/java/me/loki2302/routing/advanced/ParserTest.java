package me.loki2302.routing.advanced;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static me.loki2302.routing.advanced.AdvancedRouteDSL.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParserTest {
    private final static AdvancedRouteParser parser = new AdvancedRouteParser();

    @Test
    public void parserWorks() {
        test(make(segment("hello")), parse("hello"));
        test(make(variable("hello")), parse(":hello"));
        test(make(any("hello")), parse("*hello"));
        test(make(segment("api"), variable("id"), any("rest")), parse("api/:id/*rest"));
    }

    private static List<PartMatcher> parse(String template) {
        return parser.parse(template);
    }

    private static List<PartMatcher> make(PartMatcher... matchers) {
        return Arrays.asList(matchers);
    }

    private static void test(
            List<PartMatcher> expected,
            List<PartMatcher> actual) {

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); ++i) {
            PartMatcher expectedMatcher = expected.get(i);
            PartMatcher actualMatcher = actual.get(i);
            assertPartMatcherEquals(expectedMatcher, actualMatcher);
        }
    }

    private static void assertPartMatcherEquals(PartMatcher expected, PartMatcher actual) {
        if(expected instanceof StaticPartMatcher) {
            assertTrue(actual instanceof StaticPartMatcher);
            assertStaticPartMatcherEquals((StaticPartMatcher)expected, (StaticPartMatcher)actual);
        } else if(expected instanceof VariablePartMatcher) {
            assertTrue(actual instanceof VariablePartMatcher);
            assertVariablePartMatcherEquals((VariablePartMatcher)expected, (VariablePartMatcher)actual);
        } else if(expected instanceof AnyPartMatcher) {
            assertTrue(actual instanceof AnyPartMatcher);
            assertAnyPartMatcherEquals((AnyPartMatcher)expected, (AnyPartMatcher)actual);
        } else {
            fail("Don't know how to compare matchers of type " + expected.getClass());
        }
    }

    private static void assertStaticPartMatcherEquals(StaticPartMatcher expected, StaticPartMatcher actual) {
        assertEquals(expected.segmentTemplate, actual.segmentTemplate);
    }

    private static void assertVariablePartMatcherEquals(VariablePartMatcher expected, VariablePartMatcher actual) {
        assertEquals(expected.name, actual.name);
    }

    private static void assertAnyPartMatcherEquals(AnyPartMatcher expected, AnyPartMatcher actual) {
        assertEquals(expected.name, actual.name);
    }
}
