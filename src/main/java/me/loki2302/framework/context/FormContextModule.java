package me.loki2302.framework.context;

import com.google.inject.AbstractModule;

import java.lang.annotation.Annotation;
import java.util.Map;

public class FormContextModule extends AbstractModule {
    private final Map<String, String> formContext;

    public FormContextModule(Map<String, String> formContext) {
        this.formContext = formContext;
    }

    @Override
    protected void configure() {
        for(String formContextKey : formContext.keySet()) {
            String value = formContext.get(formContextKey);
            bindConstant()
                    .annotatedWith(new FormParamImpl(formContextKey))
                    .to(value);
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
