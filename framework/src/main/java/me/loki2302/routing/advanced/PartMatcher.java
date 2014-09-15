package me.loki2302.routing.advanced;

public interface PartMatcher {
    PartMatchResult match(String[] segments);
}
