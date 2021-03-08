package tk.fishfish.rest;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class BizException extends RuntimeException {

    @Getter
    private final Integer code;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(Integer code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
    }

}
