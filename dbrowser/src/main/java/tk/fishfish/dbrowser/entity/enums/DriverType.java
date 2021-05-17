package tk.fishfish.dbrowser.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.enums.EnumType;

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

    DM("1", "达梦"),

    ;

    private final String value;

    private final String name;


}
