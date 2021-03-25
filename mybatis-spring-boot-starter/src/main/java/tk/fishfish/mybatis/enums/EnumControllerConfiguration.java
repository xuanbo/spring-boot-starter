package tk.fishfish.mybatis.enums;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tk.fishfish.mybatis.controller.EnumController;

/**
 * 枚举controller配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class EnumControllerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EnumController enumController() {
        return new EnumController();
    }

}
