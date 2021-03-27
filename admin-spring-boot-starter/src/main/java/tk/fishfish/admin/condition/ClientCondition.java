package tk.fishfish.admin.condition;

import lombok.Data;
import tk.fishfish.admin.entity.enums.ClientStatus;
import tk.fishfish.mybatis.condition.annotation.Eq;
import tk.fishfish.mybatis.condition.annotation.Like;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class ClientCondition {

    @Like(policy = Like.Policy.ALL)
    private String name;

    @Eq
    private ClientStatus status;

}
