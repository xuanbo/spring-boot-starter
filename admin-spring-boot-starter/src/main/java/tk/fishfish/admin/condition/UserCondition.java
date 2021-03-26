package tk.fishfish.admin.condition;

import lombok.Data;
import tk.fishfish.admin.entity.enums.Sex;
import tk.fishfish.mybatis.condition.annotation.Eq;
import tk.fishfish.mybatis.condition.annotation.Like;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class UserCondition {

    @Like(policy = Like.Policy.ALL)
    private String username;

    @Like(policy = Like.Policy.ALL)
    private String name;

    @Eq
    private Sex sex;

    @Like(policy = Like.Policy.ALL)
    private String email;

    @Like(policy = Like.Policy.ALL)
    private String phone;

}
