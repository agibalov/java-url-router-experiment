package me.loki2302.context;

import java.util.Map;

public class RequestContext {
    public Map<String, Object> pathParams;
    public Map<String, String> queryParams;
    public Map<String, String> formParams;
}
