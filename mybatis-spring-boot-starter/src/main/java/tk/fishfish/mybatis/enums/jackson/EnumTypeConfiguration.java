package tk.fishfish.mybatis.enums.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 枚举配置，注册 jackson 序列化、反序列化
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class EnumTypeConfiguration {

    @Bean
    @ConditionalOnClass(SimpleModule.class)
    public SimpleModule enumTypeModule() {
        SimpleModule module = new SimpleModule("enumType");
        module.addSerializer(EnumType.class, new EnumTypeJsonSerializer());
        module.addDeserializer(Enum.class, new EnumTypeJsonDeserializer());
        return module;
    }

}
