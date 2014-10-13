package me.loki2302.handling.convention.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

public class ClassHelper {
    private final Class<?> clazz;

    public ClassHelper(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(clazz.getModifiers());
    }

    public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }
}
