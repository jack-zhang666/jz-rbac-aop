package com.jz.annotation;

import java.lang.annotation.*;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 21:41
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Permission {

    String[] roles() default "";

    String[] permission() default "";

}
