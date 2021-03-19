package tk.fishfish.mybatis.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举数据
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
public final class EnumConstantsHolder {

    private static final Map<String, Object[]> ENUM_MAP = new ConcurrentHashMap<>();

    private EnumConstantsHolder() {
        throw new IllegalStateException();
    }

    public static void add(String name, Object[] constants) {
        Optional.ofNullable(ENUM_MAP.put(name, constants))
                .ifPresent(e -> log.warn("枚举 {} 名称重复，将会覆盖，请注意修改名称", name));
    }

    public static Object[] get(String name) {
        return ENUM_MAP.get(name);
    }

}
