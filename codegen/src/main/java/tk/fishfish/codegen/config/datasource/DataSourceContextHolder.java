package tk.fishfish.codegen.config.datasource;

/**
 * 数据源key上下文管理
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    private DataSourceContextHolder() {
        throw new IllegalStateException("Utils");
    }

    public static void setup(String key) {
        THREAD_LOCAL.set(key);
    }

    public static String current() {
        return THREAD_LOCAL.get();
    }

    public static void cleanup() {
        THREAD_LOCAL.remove();
    }

}
