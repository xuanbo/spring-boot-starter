package tk.fishfish.admin.security.exception;

import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * 客户端状态异常
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class ClientStatusException extends ClientRegistrationException {

    public ClientStatusException(String msg) {
        super(msg);
    }

    public ClientStatusException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
