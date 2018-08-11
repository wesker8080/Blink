package com.eliteai.zzkframe.http.help;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***************************************
 * Author MR.ZHANG
 * Description .模拟请求参数
 * Date:2016/5/20
 ***************************************/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PARAMS {
    String value() default "";
}
