package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import tk.fishfish.admin.entity.enums.AuditStatus;
import tk.fishfish.admin.entity.enums.AuditType;
import tk.fishfish.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 审计日志
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_audit")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Audit extends Entity {

    private String username;

    @Column(name = "type")
    private AuditType type;

    @Column(name = "status")
    private AuditStatus status;

    private String ip;

    private String userAgent;

    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

}
