package com.example.ggb.config.annotation;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PCheck {
    boolean open() default true;
}
