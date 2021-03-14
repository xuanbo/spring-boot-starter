package tk.fishfish.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    private String code;

    private String name;

    private String description;

}
