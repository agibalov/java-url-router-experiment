package me.loki2302.routing.advanced;

import me.loki2302.routing.Route;

import java.util.Arrays;
import java.util.List;

public final class AdvancedRouteDSL {
    private static final AdvancedRouteParser parser = new AdvancedRouteParser();

    private AdvancedRouteDSL() {
    }

    public static Route route(PartMatcher... matchers) {
        return new AdvancedRoute(Arrays.asList(matchers));
    }

    public static Route route(String template) {
        List<PartMatcher> matchers = parser.parse(template);
        return new AdvancedRoute(matchers);
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
