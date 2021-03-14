package tk.fishfish.oauth2;

import org.springframework.context.annotation.Import;
import tk.fishfish.oauth2.configuration.resource.ResourceServerConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启资源服务器
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ResourceServerConfiguration.class)
public @interface EnableResourceServer {
}
