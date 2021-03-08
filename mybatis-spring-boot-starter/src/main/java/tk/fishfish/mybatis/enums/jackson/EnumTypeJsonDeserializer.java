package tk.fishfish.mybatis.enums.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import tk.fishfish.mybatis.enums.EnumType;
import tk.fishfish.mybatis.util.ReflectUtils;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 枚举反序列化
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class EnumTypeJsonDeserializer extends JsonDeserializer<Enum<?>> {

    @Override
    public Enum<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonToken t = parser.getCurrentToken();
        String value;
        if (t == JsonToken.VALUE_STRING) {
            value = parser.getValueAsString();
        } else if (t == JsonToken.VALUE_NUMBER_INT) {
            value = parser.getIntValue() + "";
        } else {
            throw MismatchedInputException.from(parser, EnumType.class, "枚举反序列化不匹配: " + parser.getValueAsString());
        }
        // 通过反射找到该字段对应的Field
        Field field = ReflectUtils.getField(parser.getCurrentValue().getClass(), parser.getCurrentName());
        if (field == null) {
            return null;
        }
        // 获取Field对应的枚举
        Object[] constants = field.getType().getEnumConstants();
        for (Object constant : constants) {
            if (constant instanceof EnumType) {
                EnumType enumType = (EnumType) constant;
                if (enumType.getValue().equals(value)) {
                    return (Enum<?>) enumType;
                }
                if (enumType.getName().equals(value)) {
                    return (Enum<?>) enumType;
                }
                Enum<?> anEnum = (Enum<?>) constant;
                if (anEnum.name().equals(value)) {
                    return anEnum;
                }
            }
        }
        return null;
    }

}
