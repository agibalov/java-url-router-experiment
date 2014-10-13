package me.loki2302.context;

import com.google.inject.Injector;

import java.util.Map;

public class RequestContext {
    public Injector injector;
    public Map<String, Object> pathParams;
    public Map<String, String> queryParams;
    public Map<String, String> formParams;
}
