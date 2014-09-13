package me.loki2302.framework.routing.advanced;

public interface PartMatcher {
    PartMatchResult match(String[] segments);
}
