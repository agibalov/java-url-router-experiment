package me.loki2302.routing.advanced;

public class ExactMethodMatcher implements MethodMatcher {
    private final RequestMethod expectedMethod;

    public ExactMethodMatcher(RequestMethod expectedMethod) {
        this.expectedMethod = expectedMethod;
    }

    @Override
    public boolean match(RequestMethod method) {
        return method.equals(expectedMethod);
    }
}
