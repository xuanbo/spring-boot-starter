package tk.fishfish.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * API响应
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> implements Serializable {

    /**
     * 成功或失败
     */
    private Boolean success;

    /**
     * -1，代表全局异常处理的错误
     * 0，成功
     * 正数，业务错误码
     */
    private Integer code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    private ApiResult() {
    }

    private ApiResult(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  类型
     * @return Api
     */
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(true, 0, null, data);
    }

    /**
     * 失败
     *
     * @param code 业务错误码
     * @param msg  信息
     * @return ApiResult
     */
    public static ApiResult<Void> fail(Integer code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 失败
     *
     * @param code 业务错误码
     * @param msg  信息
     * @param data 数据
     * @return ApiResult
     */
    public static <T> ApiResult<T> fail(Integer code, String msg, T data) {
        return new ApiResult<>(false, code, msg, data);
    }

}
