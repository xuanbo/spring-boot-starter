package tk.fishfish.excel.core;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Excel写
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface ExcelWriter {

    /**
     * 一次性将数据写入Excel
     *
     * @param out       Excel输出流
     * @param sheetName sheet名称
     * @param head      头
     * @param data      多行数据
     */
    void write(OutputStream out, String sheetName, List<String> head, List<List<Object>> data);

    /**
     * 分批次将数据写入Excel
     *
     * @param out           Excel输出流
     * @param sheetName     sheet名称
     * @param head          头
     * @param batchIterator 每批次数据
     */
    void write(OutputStream out, String sheetName, List<String> head, Iterator<List<List<Object>>> batchIterator);

}
