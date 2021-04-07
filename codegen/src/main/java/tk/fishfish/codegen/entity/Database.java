package tk.fishfish.codegen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.fishfish.codegen.entity.enums.DriverType;
import tk.fishfish.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "code_database")
@EqualsAndHashCode(callSuper = true)
public class Database extends Entity {

    @Column(name = "type")
    private DriverType type;

    private String name;

    private String description;

    private String url;

    private String username;

    private String password;

}
