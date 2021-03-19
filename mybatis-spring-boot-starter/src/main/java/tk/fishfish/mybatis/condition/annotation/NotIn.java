package tk.fishfish.mybatis.condition.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NOT IN
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotIn {

    /**
     * 字段名
     *
     * @return 字段名
     */
    String property() default "";

}
