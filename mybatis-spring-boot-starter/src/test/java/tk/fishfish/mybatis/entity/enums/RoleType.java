package tk.fishfish.mybatis.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 角色类型枚举
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum RoleType implements EnumType {

    ADMIN("管理员", "0"),

    USER("普通用户", "1"),

    ;

    private final String name;
    private final String value;

}
