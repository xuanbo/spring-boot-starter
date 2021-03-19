package tk.fishfish.mybatis.condition.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LIKE
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Like {

    /**
     * 字段名
     *
     * @return 字段名
     */
    String property() default "";

    /**
     * 模糊策略
     *
     * @return 默认左模糊
     */
    Policy policy() default Policy.LEFT;

    enum Policy {

        LEFT,

        RIGHT,

        ALL,

    }

}
