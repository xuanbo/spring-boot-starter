package tk.fishfish.admin.security.exception;

import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * 客户端过期异常
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class ClientExpiredException extends ClientRegistrationException {

    public ClientExpiredException(String msg) {
        super(msg);
    }

    public ClientExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
