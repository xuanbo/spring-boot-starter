package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.validator.Group;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_role")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseEntity {

    public static final String NAME = "fish:admin:role";

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String code;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String name;

    private String description;

}
