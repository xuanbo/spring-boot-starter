package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 资源权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_resource_permission")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourcePermission extends Entity {

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String resourceId;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String permissionId;

}
