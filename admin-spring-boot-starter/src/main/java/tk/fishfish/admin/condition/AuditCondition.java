package tk.fishfish.admin.condition;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.fishfish.admin.entity.enums.AuditStatus;
import tk.fishfish.admin.entity.enums.AuditType;
import tk.fishfish.mybatis.condition.annotation.Eq;
import tk.fishfish.mybatis.condition.annotation.Gte;
import tk.fishfish.mybatis.condition.annotation.Like;
import tk.fishfish.mybatis.condition.annotation.Lt;

import java.util.Date;

/**
 * 审计
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class AuditCondition {

    @Like(policy = Like.Policy.ALL)
    private String username;

    @Eq
    private AuditType type;

    @Eq
    private AuditStatus status;

    @Gte(property = "createdAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedAt;

    @Lt(property = "createdAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedAt;

}
