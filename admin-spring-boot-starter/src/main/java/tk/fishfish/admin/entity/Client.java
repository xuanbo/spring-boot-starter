package tk.fishfish.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import tk.fishfish.admin.entity.enums.ClientStatus;
import tk.fishfish.admin.validator.Group;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Table(name = "sys_client")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client extends BaseEntity {

    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String name;

    /**
     * 应用logo
     */
    private String logo;

    private String description;

    /**
     * 应用地址
     */
    @NotBlank(groups = {Group.Insert.class, Group.Update.class})
    private String uri;

    @Column(name = "secret", updatable = false)
    @NotBlank(groups = {Group.Insert.class})
    private String secret;

    @Column(name = "resource_id")
    @NotEmpty(groups = {Group.Insert.class, Group.Update.class})
    private String[] resourceIds;

    @Column(name = "redirect_uri")
    @NotEmpty(groups = {Group.Insert.class, Group.Update.class})
    private String[] redirectUris;

    @Column(name = "grant_type")
    @NotEmpty(groups = {Group.Insert.class, Group.Update.class})
    private String[] grantTypes;

    @Column(name = "scope")
    @NotEmpty(groups = {Group.Insert.class, Group.Update.class})
    private String[] scopes;

    @Column(name = "access_token_validity_seconds")
    @Min(value = 10 * 60, groups = {Group.Insert.class, Group.Update.class})
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity_seconds")
    @Min(value = 10 * 60, groups = {Group.Insert.class, Group.Update.class})
    private Integer refreshTokenValiditySeconds;

    @Column(name = "status")
    private ClientStatus status;

    private String reason;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireAt;

}
