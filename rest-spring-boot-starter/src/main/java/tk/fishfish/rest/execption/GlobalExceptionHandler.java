package tk.fishfish.rest.execption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tk.fishfish.rest.model.ApiResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOG.warn("handle MethodArgumentNotValidException", e);
        List<Map<String, Object>> list = e.getBindingResult().getAllErrors().stream().map(err -> {
            Map<String, Object> map = new HashMap<>();
            String field;
            if (err instanceof FieldError) {
                field = ((FieldError) err).getField();
            } else {
                field = err.getObjectName();
            }
            map.put("field", field);
            map.put("error", err.getDefaultMessage());
            return map;
        }).collect(Collectors.toList());
        return ApiResult.fail(400, "参数校验不合法", list);
    }

    @ExceptionHandler(BizException.class)
    public ApiResult<Void> handleBizException(BizException e) {
        LOG.warn("handle BizException", e);
        Integer code = e.getCode();
        String msg = e.getMessage();
        return ApiResult.fail(code, msg);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ApiResult<Void>> handleHttpException(HttpException e) {
        LOG.warn("handle HttpException", e);
        HttpStatus status = e.getStatus();
        Integer code = e.getCode();
        String msg = e.getMessage();
        return ResponseEntity.status(status).body(ApiResult.fail(code, msg));
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleException(Exception e) {
        LOG.warn("handle Exception", e);
        String msg = e.getMessage();
        return ApiResult.fail(-1, msg);
    }

}
