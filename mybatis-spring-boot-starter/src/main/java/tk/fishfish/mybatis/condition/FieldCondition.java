package tk.fishfish.mybatis.condition;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字段条件
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Data
public class FieldCondition {

    private Field field;

    private Op op;

    private Annotation annotation;

    public FieldCondition(Field field, Op op, Annotation annotation) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        this.field = field;
        this.op = op;
        this.annotation = annotation;
    }

}
