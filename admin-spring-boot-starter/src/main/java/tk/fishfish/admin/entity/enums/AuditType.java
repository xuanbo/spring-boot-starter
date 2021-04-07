package tk.fishfish.admin.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.enums.EnumType;

/**
 * 审计类型
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Getter
@RequiredArgsConstructor
public enum AuditType implements EnumType {

    LOGIN("登录", "0"),

    LOGOUT("登出", "1"),

    ;

    private final String name;

    private final String value;

}
