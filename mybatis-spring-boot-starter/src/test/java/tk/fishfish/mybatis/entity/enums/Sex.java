package tk.fishfish.mybatis.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 性别枚举
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum Sex implements EnumType {

    MAN("男", "0"),

    WOMAN("女", "1"),

    SECRET("保密", "2"),

    ;

    private final String name;
    private final String value;

}
