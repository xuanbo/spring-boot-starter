package tk.fishfish.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public final class ReflectUtils {

    private ReflectUtils() {
        throw new IllegalStateException("Utils");
    }

    public static Field[] getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        getFields(clazz, fields);
        return fields.toArray(new Field[0]);
    }

    private static void getFields(Class<?> clazz, List<Field> fields) {
        fields.addAll(Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            getFields(superclass, fields);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            return getField(superclass, fieldName);
        }
        return null;
    }

}
