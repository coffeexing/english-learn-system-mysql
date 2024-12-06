package cn.example.demo.common.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * 服务结果结构体
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/2 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {
    private int status;
    private String message;
    private T data;

    public ServiceResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ServiceResult isSuccess(T data) {
        ServiceResult result = new ServiceResult();
        HttpStatus ok = HttpStatus.OK;
        result.setStatus(ok.value());
        result.setMessage(ok.getReasonPhrase());
        result.setData(data);
        return result;
    }

    public static <T> ServiceResult isSuccess(String msg, T data) {
        ServiceResult result = new ServiceResult();
        result.setStatus(HttpStatus.OK.value());
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public static ServiceResult isNotFound() {
        ServiceResult result = new ServiceResult();
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        result.setStatus(notFound.value());
        result.setMessage(notFound.getReasonPhrase());
        return result;
    }

    public static ServiceResult isNotFound(String msg) {
        ServiceResult result = new ServiceResult();
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage(msg);
        return result;
    }

    public static ServiceResult isBadRequest() {
        ServiceResult result = new ServiceResult();
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        result.setStatus(badRequest.value());
        result.setMessage(badRequest.getReasonPhrase());
        return result;
    }

    public static ServiceResult isBadRequest(String msg) {
        ServiceResult result = new ServiceResult();
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setMessage(msg);
        return result;
    }

    public static ServiceResult isInternalError(String msg) {
        ServiceResult result = new ServiceResult();
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMessage(msg);
        return result;
    }

    public static ServiceResult isNotModified(String msg) {
        ServiceResult result = new ServiceResult();
        result.setStatus(HttpStatus.NOT_MODIFIED.value());
        result.setMessage(msg);
        return result;
    }
}
