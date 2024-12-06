package cn.example.demo.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * WebAPI 统一响应数据结构（子类）
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpDataResponseResult<T> extends HttpResponseResult {
    private T data;

    public HttpDataResponseResult(HttpStatus httpStatus, T data) {
        super(httpStatus.value(), httpStatus.getReasonPhrase());
        this.data = data;
    }

    public HttpDataResponseResult(HttpStatus httpStatus, String msg, T data) {
        super(httpStatus.value(), msg);
        this.data = data;
    }

    public static <T> HttpDataResponseResult isSuccess(T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.OK, data);
        return result;
    }

    public static <T> HttpDataResponseResult isSuccess(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.OK, msg, data);
        return result;
    }

    public static <T> HttpDataResponseResult isNotFound(T data) {
        return isNotFound("暂无数据！", data);
    }

    public static <T> HttpDataResponseResult isNotFound(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.NOT_FOUND, msg, data);
        return result;
    }

    public static <T> HttpDataResponseResult isBadRequest(T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.BAD_REQUEST, data);
        return result;
    }

    public static <T> HttpDataResponseResult isBadRequest(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.BAD_REQUEST, msg, data);
        return result;
    }

    public static <T> HttpDataResponseResult isInternalError(T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.INTERNAL_SERVER_ERROR, data);
        return result;
    }

    public static <T> HttpDataResponseResult isInternalError(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.INTERNAL_SERVER_ERROR, msg, data);
        return result;
    }

    public static <T> HttpDataResponseResult isNotModified(T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.NOT_MODIFIED, data);
        return result;
    }

    public static <T> HttpDataResponseResult isNotModified(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.NOT_MODIFIED, msg, data);
        return result;
    }

    public static <T> HttpDataResponseResult isUnauthorized(String msg, T data) {
        HttpDataResponseResult result = new HttpDataResponseResult(HttpStatus.UNAUTHORIZED, msg, data);
        return result;
    }
}
