package tk.fishfish.rest.execption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * HTTP异常
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class HttpException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    @Getter
    private final Integer code;

    public HttpException(HttpStatus status, Integer code, String msg) {
        super(msg);
        this.status = status;
        this.code = code;
    }

    public HttpException(HttpStatus status, Integer code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.status = status;
        this.code = code;
    }

    public static HttpException of(HttpStatus status, Integer code, String msg) {
        return new HttpException(status, code, msg);
    }

    public static HttpException of(HttpStatus status, Integer code, String msg, Object... args) {
        return new HttpException(status, code, String.format(msg, args));
    }

    public static HttpException of(HttpStatus status, Integer code, String msg, Throwable throwable) {
        return new HttpException(status, code, msg, throwable);
    }

    public static HttpException of(HttpStatus status, Integer code, String msg, Throwable throwable, Object... args) {
        return new HttpException(status, code, String.format(msg, args), throwable);
    }

}
