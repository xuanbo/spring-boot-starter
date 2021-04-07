package tk.fishfish.rest.autoconfigure;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.enums.EnumType;
import tk.fishfish.rest.enums.controller.EnumController;
import tk.fishfish.rest.enums.jackson.EnumTypeJsonDeserializer;
import tk.fishfish.rest.enums.jackson.EnumTypeJsonSerializer;

/**
 * 枚举配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Configuration
public class EnumAutoConfiguration {

    /**
     * 注册 jackson 枚举支持
     *
     * @return enumTypeModule
     */
    @Bean
    @ConditionalOnClass(SimpleModule.class)
    public SimpleModule enumTypeModule() {
        SimpleModule module = new SimpleModule("enumType");
        module.addSerializer(EnumType.class, new EnumTypeJsonSerializer());
        module.addDeserializer(Enum.class, new EnumTypeJsonDeserializer());
        return module;
    }

    @Bean
    @ConditionalOnMissingBean
    public EnumController enumController() {
        return new EnumController();
    }

}
