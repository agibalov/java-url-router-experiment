package me.loki2302.routing.advanced;

public class AnyMethodMatcher implements MethodMatcher {
    @Override
    public boolean match(RequestMethod method) {
        return true;
    }
}
