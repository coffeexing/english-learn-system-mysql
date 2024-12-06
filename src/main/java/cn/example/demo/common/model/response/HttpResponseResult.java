package cn.example.demo.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * WebAPI 统一响应数据结构
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponseResult {
    private int status;
    private String message;

    public HttpResponseResult(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }
}
