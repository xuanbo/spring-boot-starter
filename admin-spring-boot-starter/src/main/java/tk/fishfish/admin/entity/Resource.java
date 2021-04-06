package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.util.tree.Tree;
import tk.fishfish.admin.validator.Group;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_resource")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource extends BaseEntity implements Tree<Resource> {

    public static final String NAME = "fish:admin:resource";

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String code;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String name;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String path;

    private String description;

    private String parentId;

    @Transient
    private List<Resource> children;

}
