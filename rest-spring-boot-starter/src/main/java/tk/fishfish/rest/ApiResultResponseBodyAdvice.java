package tk.fishfish.rest;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回body处理，包装成 {@link ApiResult} 。如果是 {@link ResponseEntity} 或 {@link ApiResultIgnore} 注解标识则不包装
 *
 * @author 奔波儿灞
 * @see ApiResult
 * @since 1.0.0
 */
@RestControllerAdvice(annotations = RestController.class)
public class ApiResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> clazz) {
        return !methodParameter.hasMethodAnnotation(ApiResultIgnore.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // ResponseEntity、ApiResult则不包装
        if (body instanceof ResponseEntity || body instanceof ApiResult) {
            return body;
        }
        // @since 1.5.0
        // 防止 java.lang.ClassCastException: tk.fishfish.rest.ApiResult cannot be cast to java.lang.String
        if (aClass.isAssignableFrom(StringHttpMessageConverter.class)) {
            return body;
        }
        // todo 基本数据类型是否特殊处理
        // 其余统一包装成ApiResult
        return ApiResult.ok(body);
    }

}
