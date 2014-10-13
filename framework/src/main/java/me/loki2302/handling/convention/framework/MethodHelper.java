package me.loki2302.handling.convention.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodHelper {
    private final Method method;

    public MethodHelper(Method method) {
        this.method = method;
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(method.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(method.getModifiers());
    }

    public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> annotationClass) {
        return method.getAnnotation(annotationClass);
    }
}
