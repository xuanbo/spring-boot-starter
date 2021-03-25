package tk.fishfish.rest.execption;

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

    public static BizException of(Integer code, String msg) {
        return new BizException(code, msg);
    }

    public static BizException of(Integer code, String msg, Object... args) {
        return new BizException(code, String.format(msg, args));
    }

    public static BizException of(Integer code, String msg, Throwable throwable) {
        return new BizException(code, msg, throwable);
    }

    public static BizException of(Integer code, String msg, Throwable throwable, Object... args) {
        return new BizException(code, String.format(msg, args), throwable);
    }

}
