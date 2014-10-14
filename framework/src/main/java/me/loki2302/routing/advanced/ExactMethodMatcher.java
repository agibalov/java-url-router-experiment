package me.loki2302.routing.advanced;

import java.util.Arrays;

public class ExactMethodMatcher implements MethodMatcher {
    private final RequestMethod[] expectedMethods;

    public ExactMethodMatcher(RequestMethod[] expectedMethods) {
        this.expectedMethods = expectedMethods;
    }

    @Override
    public boolean match(RequestMethod method) {
        return Arrays.asList(expectedMethods).contains(method);
    }
}
