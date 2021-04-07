package tk.fishfish.mybatis.condition;

import tk.fishfish.persistence.Entity;
import tk.mybatis.mapper.entity.Condition;

/**
 * 条件解析
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
public interface ConditionParser {

    Condition parse(Class<? extends Entity> entityClazz, Object condition);

}
