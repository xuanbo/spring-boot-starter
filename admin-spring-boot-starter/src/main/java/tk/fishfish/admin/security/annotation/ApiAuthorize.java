package tk.fishfish.admin.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义API认证信息
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuthorize {

    /**
     * 编码
     *
     * @return 编码
     */
    String code();

    /**
     * 名称
     *
     * @return 名称
     */
    String name();

    /**
     * 描述
     *
     * @return 描述
     */
    String description() default "";

}
