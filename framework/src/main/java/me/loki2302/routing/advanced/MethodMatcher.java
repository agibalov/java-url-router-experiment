package me.loki2302.routing.advanced;

public interface MethodMatcher {
    boolean match(RequestMethod method);
}
