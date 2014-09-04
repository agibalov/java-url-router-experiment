package me.loki2302;

public class Route {
    private final RouteSegment[] routeSegments;

    public Route(RouteSegment... routeSegments) {
        this.routeSegments = routeSegments;
    }

    public boolean match(String url) {
        String[] urlSegments = url.split("/");
        if(urlSegments.length != routeSegments.length) {
            return false;
        }

        for(int i = 0; i < routeSegments.length; ++i) {
            RouteSegment routeSegment = routeSegments[i];
            String urlSegment = urlSegments[i];
            SegmentMatchResult matchResult = routeSegment.match(urlSegment);
            if(!matchResult.match) {
                return false;
            }
        }

        return true;
    }
}
