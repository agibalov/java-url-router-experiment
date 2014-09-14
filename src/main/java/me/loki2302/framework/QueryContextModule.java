package me.loki2302.framework;

import com.google.inject.AbstractModule;

import java.lang.annotation.Annotation;
import java.util.Map;

public class QueryContextModule extends AbstractModule {
    private final Map<String, String> queryContext;

    public QueryContextModule(Map<String, String> queryContext) {
        this.queryContext = queryContext;
    }

    @Override
    protected void configure() {
        for(String queryContextKey : queryContext.keySet()) {
            String value = queryContext.get(queryContextKey);
            bindConstant()
                    .annotatedWith(new QueryParamImpl(queryContextKey))
                    .to(value);
        }
    }

    private static class QueryParamImpl implements QueryParam {
        private final String value;

        public QueryParamImpl(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }

        public Class<? extends Annotation> annotationType() {
            return QueryParam.class;
        }

        public int hashCode() {
            // This is specified in java.lang.Annotation.
            return (127 * "value".hashCode()) ^ value.hashCode();
        }

        public boolean equals(Object o) {
            if (!(o instanceof QueryParam)) {
                return false;
            }

            QueryParam other = (QueryParam)o;
            return value.equals(other.value());
        }

        public String toString() {
            return "@" + QueryParam.class.getName() + "(value=" + value + ")";
        }
    }
}
