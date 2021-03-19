package tk.fishfish.codegen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@ConfigurationProperties("fish.codegen")
public class CodegenProperties {

    /**
     * 默认作者
     */
    private String author;

    /**
     * 模板地址
     */
    private Map<String, String> templates;

}
