package tk.fishfish.admin.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 客户端状态
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Getter
@RequiredArgsConstructor
public enum ClientStatus implements EnumType {

    APPLY("申请中", "0"),

    PASS("通过", "1"),

    REJECT("拒绝", "1"),

    ;

    private final String name;

    private final String value;

}
