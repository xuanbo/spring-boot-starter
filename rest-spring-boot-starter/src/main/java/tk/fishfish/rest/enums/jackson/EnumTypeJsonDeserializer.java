package tk.fishfish.rest.enums.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import tk.fishfish.enums.EnumType;
import tk.fishfish.util.ReflectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 枚举反序列化
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class EnumTypeJsonDeserializer extends JsonDeserializer<Enum<?>> {

    @Override
    @SuppressWarnings("unchecked")
    public Enum<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken t = parser.getCurrentToken();
        String value;
        if (t == JsonToken.VALUE_STRING) {
            value = parser.getValueAsString();
        } else if (t == JsonToken.VALUE_NUMBER_INT) {
            value = parser.getIntValue() + "";
        } else if (t == JsonToken.START_OBJECT) {
            // 对象 {"name":"男","value":"0"}
            Map<String, Object> map = parser.readValueAs(Map.class);
            Object name = map.get("name");
            if (name == null) {
                Object ori = map.get("value");
                if (ori == null) {
                    throw MismatchedInputException.from(parser, EnumType.class, "枚举反序列化不匹配: " + parser.readValueAsTree().toString());
                }
                value = ori.toString();
            } else {
                value = name.toString();
            }
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
