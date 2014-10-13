package me.loki2302.routing.advanced;

import java.util.Arrays;
import java.util.List;

public final class RouteDSL {
    private static final RouteParser parser = new RouteParser();

    private RouteDSL() {
    }

    public static Route route(MethodMatcher methodMatcher, PartMatcher... matchers) {
        return new Route(methodMatcher, Arrays.asList(matchers));
    }

    public static Route route(MethodMatcher methodMatcher, String template) {
        List<PartMatcher> matchers = parser.parse(template);
        return new Route(methodMatcher, matchers);
    }

    public static PartMatcher sequence(PartMatcher... matchers) {
        return new SequencePartMatcher(matchers);
    }

    public static PartMatcher segment(String value) {
        return new StaticPartMatcher(value);
    }

    public static PartMatcher variable(String name) {
        return new VariablePartMatcher(name);
    }

    public static PartMatcher any(String name) {
        return new AnyPartMatcher(name);
    }
}
