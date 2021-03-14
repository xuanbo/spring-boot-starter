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

    @Like(property = "username", policy = Like.Policy.ALL)
    private String username;

    @Like(property = "name", policy = Like.Policy.ALL)
    private String name;

    @Eq(property = "sex")
    private Sex sex;

    @Like(property = "email", policy = Like.Policy.ALL)
    private String email;

    @Like(property = "phone", policy = Like.Policy.ALL)
    private String phone;

}
