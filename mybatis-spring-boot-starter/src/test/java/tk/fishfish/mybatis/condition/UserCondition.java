package tk.fishfish.mybatis.condition;

import lombok.Data;
import tk.fishfish.mybatis.condition.annotation.Eq;
import tk.fishfish.mybatis.condition.annotation.In;
import tk.fishfish.mybatis.condition.annotation.Like;
import tk.fishfish.mybatis.entity.enums.Sex;

/**
 * 用户条件
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Data
public class UserCondition {

    @Like(property = "username", policy = Like.Policy.LEFT)
    private String username;

    @Eq(property = "sex")
    private Sex sex;

    @Like(property = "email")
    private String email;

    @In(property = "departmentId")
    private String[] departmentIds;

}
