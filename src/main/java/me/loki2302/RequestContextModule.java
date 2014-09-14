package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Map;

public class RequestContextModule extends AbstractModule {
    private final Map<String, Object> pathContext;
    private final Map<String, String> formContext;

    public RequestContextModule(
            Map<String, Object> pathContext,
            Map<String, String> formContext) {

        this.pathContext = pathContext;
        this.formContext = formContext;
    }

    @Override
    protected void configure() {
        for(String pathContextKey : pathContext.keySet()) {
            // how do I inject Objects as Strings?
            String value = (String)pathContext.get(pathContextKey);
            bindConstant()
                    .annotatedWith(Names.named("path-" + pathContextKey))
                    .to(value);
        }

        for(String formContextKey : formContext.keySet()) {
            String value = formContext.get(formContextKey);
            bind(String.class)
                    .annotatedWith(Names.named("form-" + formContextKey))
                    .toInstance(value);
        }
    }
}
