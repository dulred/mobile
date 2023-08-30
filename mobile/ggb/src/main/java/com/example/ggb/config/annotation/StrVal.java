package com.example.ggb.config.annotation;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrVal {
    int min() default 0;
    int max() default 25;
    String regex() default "";
    String info() default "参数";
    //默认是不允许为null的
    boolean ifNull() default false;
}
