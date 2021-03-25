package tk.fishfish.actuator.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * actuator属性
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Validated
@ConfigurationProperties("fish.actuator")
public class ActuatorProperties {

    private Http http = new Http();

    @Data
    @Validated
    public static class Http {

        /**
         * 超时记录日志，毫秒
         */
        @Min(0)
        private Long timeout = 3 * 1000L;

    }

}
