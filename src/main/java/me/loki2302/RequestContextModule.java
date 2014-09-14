package me.loki2302;

import com.google.inject.AbstractModule;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
                    .annotatedWith(new PathParamImpl(pathContextKey))
                    .to(value);
        }

        for(String formContextKey : formContext.keySet()) {
            String value = formContext.get(formContextKey);
            bindConstant()
                    .annotatedWith(new FormParamImpl(formContextKey))
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

    private static class FormParamImpl implements FormParam {
        private final String value;

        public FormParamImpl(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }

        public Class<? extends Annotation> annotationType() {
            return FormParam.class;
        }

        public int hashCode() {
            // This is specified in java.lang.Annotation.
            return (127 * "value".hashCode()) ^ value.hashCode();
        }

        public boolean equals(Object o) {
            if (!(o instanceof FormParam)) {
                return false;
            }

            FormParam other = (FormParam)o;
            return value.equals(other.value());
        }

        public String toString() {
            return "@" + FormParam.class.getName() + "(value=" + value + ")";
        }
    }
}
