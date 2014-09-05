package me.loki2302;

import java.util.Map;

public interface RouteSegment {
    SegmentMatchResult match(String urlSegment);
    String getValue(Map<String, Object> context);
}
