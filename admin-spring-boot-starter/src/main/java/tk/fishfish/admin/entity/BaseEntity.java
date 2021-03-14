package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import tk.fishfish.mybatis.entity.Entity;

import java.util.Date;

/**
 * 四大金刚
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends Entity {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    private String updatedBy;

}