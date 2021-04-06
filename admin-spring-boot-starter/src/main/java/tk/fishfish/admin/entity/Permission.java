package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.validator.Group;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_permission")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission extends BaseEntity {

    public static final String NAME = "fish:admin:permission";

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String name;

    @Column(name = "method")
    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String[] methods;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String path;

    private String description;

    @Transient
    @JsonIgnoreProperties
    private String roleId;

}
