package me.loki2302.handling.convention.framework;

import java.lang.annotation.Annotation;

public class ParameterHelper {
    private final Class<?> type;
    private final Annotation[] annotations;

    public ParameterHelper(Class<?> type, Annotation[] annotations) {
        this.type = type;
        this.annotations = annotations;
    }

    public Class<?> getType() {
        return type;
    }

    public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> annotationClass) {
        for(Annotation annotation : annotations) {
            if(annotation.annotationType() == annotationClass) {
                return (TAnnotation)annotation;
            }
        }

        return null;
    }
}
