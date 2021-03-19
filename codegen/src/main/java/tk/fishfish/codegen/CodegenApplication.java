package tk.fishfish.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.mybatis.enums.EnableEnumTypes;

/**
 * 启动
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@EnableEnumTypes
@SpringBootApplication
public class CodegenApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodegenApplication.class, args);
    }

}
