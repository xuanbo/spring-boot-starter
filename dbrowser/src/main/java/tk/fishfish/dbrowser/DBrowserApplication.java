package tk.fishfish.dbrowser;

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
public class DBrowserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBrowserApplication.class, args);
    }

}
