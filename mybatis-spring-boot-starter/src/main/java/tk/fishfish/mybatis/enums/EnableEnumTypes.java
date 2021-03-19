package tk.fishfish.mybatis.enums;

import org.springframework.context.annotation.Import;
import tk.fishfish.mybatis.enums.jackson.EnumTypeConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启枚举类型支持
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({EnumTypeRegistrar.class, EnumTypeConfiguration.class, EnumControllerConfiguration.class})
public @interface EnableEnumTypes {

    /**
     * 包路径
     *
     * @return 包路径
     */
    String[] value() default {};

    /**
     * 包路径
     *
     * @return 包路径
     */
    String[] basePackages() default {};

    /**
     * 包路径
     *
     * @return 包路径
     */
    Class<?>[] basePackageClasses() default {};

}
