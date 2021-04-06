package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import tk.fishfish.admin.entity.enums.Sex;
import tk.fishfish.admin.validator.Group;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity {

    public static final String NAME = "fish:admin:user";

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    @Column(name = "username", updatable = false)
    private String username;

    @NotBlank(groups = Group.Insert.class)
    @Column(name = "password", updatable = false)
    private String password;

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String name;

    @Column(name = "sex")
    private Sex sex;

    private String email;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date accountExpireAt;

    private Boolean accountLock;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date credentialsExpireAt;

    private Boolean enable;

}
