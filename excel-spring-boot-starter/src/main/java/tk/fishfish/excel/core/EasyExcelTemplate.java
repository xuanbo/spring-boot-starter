package tk.fishfish.excel.core;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 默认实现，底层基于EasyExcel
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@RequiredArgsConstructor
public class EasyExcelTemplate implements ExcelTemplate {

    private final List<Converter<?>> converters;

    @Override
    public List<Map<String, Object>> read(InputStream in) {
        final List<Map<String, Object>> list = new LinkedList<>();
        EasyExcel.read(in, new AnalysisEventListener<Map<Integer, Object>>() {
            private Map<Integer, String> head;

            @Override
            public void invokeHeadMap(Map<Integer, String> head, AnalysisContext context) {
                log.info("读取表头: {}", head);
                this.head = head;
            }

            @Override
            public void invoke(Map<Integer, Object> data, AnalysisContext context) {
                Map<String, Object> row = new LinkedHashMap<>();
                this.head.forEach((k, v) -> row.put(v, data.get(k)));
                list.add(row);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                log.info("所有数据解析完成，读取数据条数: {}", list.size());
            }
        }).sheet().doRead();
        return list;
    }

    @Override
    public void read(InputStream in, Consumer<List<Map<String, Object>>> batchConsumer) {
        read(in, 1000, batchConsumer);
    }

    @Override
    public void read(InputStream in, int batchSize, Consumer<List<Map<String, Object>>> batchConsumer) {
        EasyExcel.read(in, new AnalysisEventListener<Map<Integer, Object>>() {
            private final List<Map<String, Object>> list = new LinkedList<>();
            private int count;
            private Map<Integer, String> head;

            @Override
            public void invokeHeadMap(Map<Integer, String> head, AnalysisContext context) {
                log.info("读取表头: {}", head);
                this.head = head;
            }

            @Override
            public void invoke(Map<Integer, Object> data, AnalysisContext context) {
                Map<String, Object> row = new LinkedHashMap<>();
                this.head.forEach((k, v) -> row.put(v, data.get(k)));
                count++;
                list.add(row);
                if (list.size() >= batchSize) {
                    doConsumer();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (!list.isEmpty()) {
                    doConsumer();
                }
                log.info("所有数据解析完成，读取数据条数: {}", count);
            }

            private void doConsumer() {
                batchConsumer.accept(list);
                list.clear();
            }
        }).sheet().doRead();
    }

    @Override
    public <T> List<T> read(InputStream in, Class<T> clazz) {
        final List<T> list = new LinkedList<>();
        EasyExcel.read(in, clazz, new AnalysisEventListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                list.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                log.info("所有数据解析完成，读取数据条数: {}", list.size());
            }
        }).sheet().doRead();
        return list;
    }

    @Override
    public <T> void read(InputStream in, Class<T> clazz, Consumer<List<T>> batchConsumer) {
        read(in, clazz, 1000, batchConsumer);
    }

    @Override
    public <T> void read(InputStream in, Class<T> clazz, int batchSize, Consumer<List<T>> batchConsumer) {
        EasyExcel.read(in, clazz, new AnalysisEventListener<T>() {
            private final List<T> list = new LinkedList<>();
            private int count;

            @Override
            public void invoke(T data, AnalysisContext context) {
                count++;
                list.add(data);
                if (list.size() >= batchSize) {
                    doConsumer();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (!list.isEmpty()) {
                    doConsumer();
                }
                log.info("所有数据解析完成，读取数据条数: {}", count);
            }

            private void doConsumer() {
                batchConsumer.accept(list);
                list.clear();
            }
        }).sheet().doRead();
    }

    @Override
    public void write(OutputStream out, String sheetName, List<String> head, List<List<Object>> data) {
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
        ExcelWriterBuilder writerBuilder = EasyExcel.write(out);
        // 注册转换器
        registerConverters(writerBuilder);
        writerBuilder.head(head.stream().map(Collections::singletonList).collect(Collectors.toList()))
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        ExcelWriter writer = writerBuilder.build();
        try {
            writer.write(data, sheet);
        } finally {
            if (writer != null) {
                writer.finish();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn("关闭流失败", e);
                }
            }
        }
    }

    @Override
    public void write(OutputStream out, String sheetName, List<String> head, Iterator<List<List<Object>>> batchIterator) {
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
        ExcelWriterBuilder writerBuilder = EasyExcel.write(out);
        // 注册转换器
        registerConverters(writerBuilder);
        writerBuilder.head(head.stream().map(Collections::singletonList).collect(Collectors.toList()))
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        ExcelWriter writer = writerBuilder.build();
        try {
            int i = 0;
            while (batchIterator.hasNext()) {
                i++;
                log.info("写入批次 {} 数据", i);
                writer.write(batchIterator.next(), sheet);
            }
        } finally {
            if (writer != null) {
                writer.finish();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn("关闭流失败", e);
                }
            }
        }
    }

    private void registerConverters(ExcelWriterBuilder writerBuilder) {
        if (CollectionUtils.isEmpty(converters)) {
            return;
        }
        for (Converter<?> converter : converters) {
            writerBuilder.registerConverter(converter);
        }
    }

}
