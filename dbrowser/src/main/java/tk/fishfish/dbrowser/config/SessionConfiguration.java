package tk.fishfish.dbrowser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.dbrowser.database.DefaultSessionHub;
import tk.fishfish.dbrowser.database.SessionHub;

/**
 * 会话配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Configuration
public class SessionConfiguration {

    @Bean
    public SessionHub sessionHub() {
        return new DefaultSessionHub();
    }

}
