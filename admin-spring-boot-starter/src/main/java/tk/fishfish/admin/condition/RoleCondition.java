package tk.fishfish.admin.condition;

import lombok.Data;
import tk.fishfish.mybatis.condition.annotation.Like;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class RoleCondition {

    @Like(policy = Like.Policy.ALL)
    private String code;

    @Like(policy = Like.Policy.ALL)
    private String name;

}
