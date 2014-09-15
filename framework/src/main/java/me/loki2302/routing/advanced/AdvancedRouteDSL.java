package me.loki2302.routing.advanced;

import me.loki2302.routing.Route;

public final class AdvancedRouteDSL {
    private AdvancedRouteDSL() {
    }

    public static Route route(PartMatcher... matchers) {
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
