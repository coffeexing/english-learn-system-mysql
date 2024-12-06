package cn.example.demo.common.exception;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-16 16:53
 */
//@RestController
public class CustomErrorController implements ErrorController {
    private static final String NOT_FOUND = "404";

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public HttpResponseResult handleError() {
        return HttpDataResponseResult.isNotFound("不存在的接口!", null);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
