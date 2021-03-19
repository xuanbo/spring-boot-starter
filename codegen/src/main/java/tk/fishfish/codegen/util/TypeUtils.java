package tk.fishfish.codegen.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import tk.fishfish.codegen.dto.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类型工具类
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Slf4j
public final class TypeUtils {

    private static final String CLASS_DATE = "Date";
    private static final String CLASS_BIG_DECIMAL = "BigDecimal";

    private TypeUtils() {
        throw new IllegalStateException("Utils");
    }

    public static String getFieldType(String columnType) {
        val type = columnType.toUpperCase();
        switch (type) {
            case "CHAR":
            case "VARCHAR":
            case "TEXT":
            case "LONGTEXT":
                return String.class.getSimpleName();
            case "TIMESTAMP":
            case "DATETIME":
            case "DATE":
                return Date.class.getSimpleName();
            case "TINYINT":
            case "SMALLINT":
            case "INT":
            case "INTEGER":
                return Integer.class.getSimpleName();
            case "BIGINT":
                return Long.class.getSimpleName();
            case "FLOAT":
            case "DOUBLE":
                return Double.class.getSimpleName();
            case "NUMERIC":
            case "DECIMAL":
                return BigDecimal.class.getSimpleName();
            default:
                log.error("unknown column type: {}", columnType);
                return "unknown";
        }
    }

    public static boolean hasDate(List<Table.Column> columns) {
        return columns.stream()
                .map(Table.Column::getType)
                .anyMatch(TypeUtils::isDate);
    }

    public static boolean isDate(String columnType) {
        String type = getFieldType(columnType);
        return CLASS_DATE.equals(type);
    }

    public static String autoImportPkg(List<Table.Column> columns) {
        val sb = new StringBuilder();
        columns.stream().map(Table.Column::getType).map(TypeUtils::getFieldType).collect(Collectors.toSet()).forEach(type -> {
            switch (type) {
                case CLASS_DATE:
                    sb.append("import java.util.Date;").append("\n");
                    break;
                case CLASS_BIG_DECIMAL:
                    sb.append("import java.math.BigDecimal;").append("\n");
                    break;
                default:
            }
        });
        return sb.toString();
    }

}
