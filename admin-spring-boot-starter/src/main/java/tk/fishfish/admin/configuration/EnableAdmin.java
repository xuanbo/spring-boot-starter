package tk.fishfish.admin.configuration;

import org.springframework.context.annotation.Import;
import tk.fishfish.mybatis.enums.EnableEnumTypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启后台管理支持
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AdminConfiguration.class)
@EnableEnumTypes(AdminConfiguration.PACKAGE)
public @interface EnableAdmin {
}
