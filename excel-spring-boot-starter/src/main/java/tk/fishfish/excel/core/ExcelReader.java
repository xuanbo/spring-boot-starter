package tk.fishfish.excel.core;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Excel读
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface ExcelReader {

    /**
     * 读取Excel数据，适合较少数据量
     *
     * @param in Excel输入流
     * @return 读取数据
     */
    List<Map<String, Object>> read(InputStream in);

    /**
     * 分批次读取，默认1000条一个批次
     *
     * @param in            Excel输入流
     * @param batchConsumer 批次数据，最后一批可能不满
     */
    void read(InputStream in, Consumer<List<Map<String, Object>>> batchConsumer);

    /**
     * 分批次读取
     *
     * @param in            Excel输入流
     * @param batchSize     批次大小
     * @param batchConsumer 批次数据，最后一批可能不满
     */
    void read(InputStream in, int batchSize, Consumer<List<Map<String, Object>>> batchConsumer);

    /**
     * 读取Excel数据，适合较少数据量
     *
     * @param in    Excel输入流
     * @param clazz 类型
     * @param <T>   类型
     * @return 读取数据
     */
    <T> List<T> read(InputStream in, Class<T> clazz);

    /**
     * 分批次读取，默认1000条一个批次
     *
     * @param in            Excel输入流
     * @param clazz         类型
     * @param batchConsumer 批次数据，最后一批可能不满
     * @param <T>           类型
     */
    <T> void read(InputStream in, Class<T> clazz, Consumer<List<T>> batchConsumer);

    /**
     * 分批次读取
     *
     * @param in            Excel输入流
     * @param clazz         类型
     * @param batchSize     批次大小
     * @param batchConsumer 批次数据，最后一批可能不满
     * @param <T>           类型
     */
    <T> void read(InputStream in, Class<T> clazz, int batchSize, Consumer<List<T>> batchConsumer);

}
