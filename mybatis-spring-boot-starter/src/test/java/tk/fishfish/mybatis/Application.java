package tk.fishfish.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.fishfish.mybatis.condition.UserCondition;
import tk.fishfish.mybatis.entity.User;
import tk.fishfish.mybatis.entity.enums.Sex;
import tk.fishfish.mybatis.enums.EnableEnumTypes;
import tk.fishfish.mybatis.enums.EnumType;
import tk.fishfish.mybatis.service.UserService;

import java.util.List;

/**
 * 入口
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Slf4j
@EnableEnumTypes
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        UserCondition condition = new UserCondition();
        condition.setUsername("admin");
        condition.setSex(Sex.WOMAN);
        condition.setDepartmentIds(new String[]{"1350364237922308012"});
        List<User> users = userService.query(condition);
        log.info("users: {}", users);
    }

}
