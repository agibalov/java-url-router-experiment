package me.loki2302.framework.context;

import com.google.inject.AbstractModule;

import java.lang.annotation.Annotation;
import java.util.Map;

public class PathContextModule extends AbstractModule {
    private final Map<String, Object> pathContext;

    public PathContextModule(Map<String, Object> pathContext) {
        this.pathContext = pathContext;
    }

    @Override
    protected void configure() {
        for(String pathContextKey : pathContext.keySet()) {
            // how do I inject Objects as Strings?
            String value = (String)pathContext.get(pathContextKey);
            bindConstant()
                    .annotatedWith(new PathParamImpl(pathContextKey))
                    .to(value);
        }
    }

    private static class PathParamImpl implements PathParam {
        private final String value;

        public PathParamImpl(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }

        public Class<? extends Annotation> annotationType() {
            return PathParam.class;
        }

        public int hashCode() {
            // This is specified in java.lang.Annotation.
            return (127 * "value".hashCode()) ^ value.hashCode();
        }

        public boolean equals(Object o) {
            if (!(o instanceof PathParam)) {
                return false;
            }

            PathParam other = (PathParam)o;
            return value.equals(other.value());
        }

        public String toString() {
            return "@" + PathParam.class.getName() + "(value=" + value + ")";
        }
    }
}
