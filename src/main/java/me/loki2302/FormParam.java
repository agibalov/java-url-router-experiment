package me.loki2302;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface FormParam {
    String value();
}
