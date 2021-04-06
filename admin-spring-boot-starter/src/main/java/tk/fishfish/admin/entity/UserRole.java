package tk.fishfish.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.entity.Entity;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 用户角色关联
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Entity {

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String userId;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String roleId;

}
