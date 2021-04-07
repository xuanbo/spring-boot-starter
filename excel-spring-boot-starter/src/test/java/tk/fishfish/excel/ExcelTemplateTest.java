package tk.fishfish.excel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.excel.autoconfigure.ExcelAutoConfiguration;
import tk.fishfish.excel.core.ExcelTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测试
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ExcelAutoConfiguration.class
})
public class ExcelTemplateTest {

    @Autowired
    private ExcelTemplate excelTemplate;

    @Test
    public void readToMap() throws IOException {
        ClassPathResource resource = new ClassPathResource("template.xlsx");
        List<Map<String, Object>> list = excelTemplate.read(resource.getInputStream());
        log.info("list: {}", list);
    }

    @Test
    public void readToObj() throws IOException {
        ClassPathResource resource = new ClassPathResource("template.xlsx");
        List<User> users = excelTemplate.read(resource.getInputStream(), User.class);
        log.info("users: {}", users);
    }

    @Test
    public void batchReadToObj() throws IOException {
        ClassPathResource resource = new ClassPathResource("template.xlsx");
        excelTemplate.read(resource.getInputStream(), User.class, 2, users -> log.info("users: {}", users));
    }

    @Test
    public void writeAll() throws FileNotFoundException {
        String sheetName = "数据";
        List<String> head = Arrays.asList("字段1", "字段2", "字段3");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList("1", "1", "1"),
                Arrays.asList(2, 2, 2)
        );
        excelTemplate.write(new FileOutputStream("1.xlsx"), sheetName, head, data);
    }

}
