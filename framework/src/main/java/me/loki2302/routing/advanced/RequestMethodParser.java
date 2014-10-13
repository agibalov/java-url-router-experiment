package me.loki2302.routing.advanced;

public class RequestMethodParser {
    public RequestMethod parse(String requestMethodString) {
        if(requestMethodString.equals("GET")) {
            return RequestMethod.GET;
        }

        if(requestMethodString.equals("POST")) {
            return RequestMethod.POST;
        }

        return null;
    }
}
