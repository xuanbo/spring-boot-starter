package tk.fishfish.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.mybatis.entity.enums.Sex;
import tk.fishfish.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Data
@Table(name = "oh_user")
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    private String name;

    private String username;

    private String password;

    @Column(name = "sex")
    private Sex sex;

    private String email;

    private String departmentId;

    private Date createdAt;

    private Date updatedAt;

    @Transient
    private String ignore;

}
