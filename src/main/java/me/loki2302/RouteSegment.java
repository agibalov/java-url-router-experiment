package me.loki2302;

public interface RouteSegment {
    SegmentMatchResult match(String urlSegment);
}
