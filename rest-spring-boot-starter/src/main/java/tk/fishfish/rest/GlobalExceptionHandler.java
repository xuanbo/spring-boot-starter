package tk.fishfish.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理，优先级为100
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Order(100)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    public ApiResult<Void> handleBizException(BizException e) {
        LOG.warn("handle bizException", e);
        Integer code = e.getCode();
        String msg = e.getMessage();
        return ApiResult.fail(code, msg);
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleException(Exception e) {
        LOG.warn("handle exception", e);
        String msg = e.getMessage();
        return ApiResult.fail(-1, msg);
    }

}
