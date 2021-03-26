package tk.fishfish.admin.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 密码
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Validated
public class PasswordDTO {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}
