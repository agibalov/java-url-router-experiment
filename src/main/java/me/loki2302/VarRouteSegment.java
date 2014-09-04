package me.loki2302;

public class VarRouteSegment implements RouteSegment {
    private String name;

    public VarRouteSegment(String name) {
        this.name = name;
    }

    @Override
    public SegmentMatchResult match(String urlSegment) {
        if(urlSegment.isEmpty()) {
            return SegmentMatchResult.noMatch();
        }

        return SegmentMatchResult.matchSingletonContext(name, urlSegment);
    }
}
