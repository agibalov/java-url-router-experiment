package me.loki2302;

public class ConstRouteSegment implements RouteSegment {
    private String value;

    public ConstRouteSegment(String value) {
        this.value = value;
    }

    @Override
    public SegmentMatchResult match(String urlSegment) {
        if(!urlSegment.equals(value)) {
            return SegmentMatchResult.noMatch();
        }

        return SegmentMatchResult.matchNoContext();
    }
}
