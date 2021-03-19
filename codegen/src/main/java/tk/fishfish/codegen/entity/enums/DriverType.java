package tk.fishfish.codegen.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 驱动类型
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Getter
@RequiredArgsConstructor
public enum DriverType implements EnumType {

    MYSQL("0", "MySQL"),

    ;

    private final String value;

    private final String name;


}
