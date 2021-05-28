package tk.fishfish.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.admin.entity.User;

import java.util.List;

/**
 * 用户权限信息
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPermission extends User {

    private List<String> roles;

    private List<String> resources;

    private List<String> permissions;

}
