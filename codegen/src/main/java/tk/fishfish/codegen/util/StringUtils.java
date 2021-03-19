package tk.fishfish.codegen.util;

import lombok.val;
import lombok.var;

/**
 * 字符串
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utils");
    }

    /**
     * 下划线转驼峰
     *
     * @param input 下划线字符串
     * @return 驼峰
     */
    public static String toCamelCase(String input) {
        if (input == null) {
            return null;
        }
        int length = input.length();

        val sb = new StringBuilder(length);
        var upperCase = false;
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰，并首字母小写
     *
     * @param input 下划线字符串
     * @return 驼峰，并首字母小写
     */
    public static String toProperty(String input) {
        String str = toCamelCase(input);
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 大写转中划线
     *
     * @param input 大写
     * @return 转中划线
     */
    public static String toPath(String input) {
        if (input == null) {
            return null;
        }
        int length = input.length();

        val sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    sb.append('-');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}
