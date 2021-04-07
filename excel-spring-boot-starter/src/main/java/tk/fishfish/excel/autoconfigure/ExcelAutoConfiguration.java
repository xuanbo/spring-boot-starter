package tk.fishfish.excel.autoconfigure;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.excel.core.EasyExcelTemplate;
import tk.fishfish.excel.core.ExcelTemplate;

import java.util.List;

/**
 * Excel自动配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Configuration
@ConditionalOnClass(EasyExcel.class)
public class ExcelAutoConfiguration {

    @Autowired(required = false)
    private List<Converter<?>> converters;

    @Bean
    public ExcelTemplate excelTemplate() {
        return new EasyExcelTemplate(converters);
    }

}
