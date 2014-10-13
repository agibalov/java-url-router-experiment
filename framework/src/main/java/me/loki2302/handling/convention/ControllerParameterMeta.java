package me.loki2302.handling.convention;

import java.lang.annotation.Annotation;

public class ControllerParameterMeta {
    private final Class<?> parameterClass;
    private final Annotation[] annotations;

    ControllerParameterMeta(
            Class<?> parameterClass,
            Annotation[] annotations) {

        this.parameterClass = parameterClass;
        this.annotations = annotations;
    }

    public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> annotationClass) {
        for(Annotation annotation : annotations) {
            if(annotation.annotationType() == annotationClass) {
                return (TAnnotation)annotation;
            }
        }

        return null;
    }

    public Class<?> getParameterClass() {
        return parameterClass;
    }
}
