package tk.fishfish.mybatis.enums.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tk.fishfish.mybatis.enums.EnumType;

import java.io.IOException;

/**
 * 枚举序列化
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class EnumTypeJsonSerializer extends JsonSerializer<EnumType> {

    @Override
    public void serialize(EnumType enumType, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // 开始写入对象
        gen.writeStartObject();
        gen.writeStringField("name", enumType.getName());
        gen.writeStringField("value", enumType.getValue());
        // 显式结束操作
        gen.writeEndObject();
    }

}
