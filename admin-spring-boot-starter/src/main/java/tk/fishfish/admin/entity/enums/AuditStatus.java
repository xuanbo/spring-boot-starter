package tk.fishfish.admin.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.enums.EnumType;

/**
 * 审计状态
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Getter
@RequiredArgsConstructor
public enum AuditStatus implements EnumType {

    SUCCESS("成功", "0"),

    FAILURE("失败", "1"),

    ;

    private final String name;

    private final String value;

}
