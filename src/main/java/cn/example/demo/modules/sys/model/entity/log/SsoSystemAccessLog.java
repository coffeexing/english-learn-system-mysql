package cn.example.demo.modules.sys.model.entity.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 系统运行与操作日志
 * </p>
 *
 * @author Lizuxian
 * @create 2020-11-13 14:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsoSystemAccessLog {
    private Long id;
    private String username;    // 用户名
    private String operation;    // 操作内容
    private String requestMethod; // 请求方式
    private String resourcePath;    // 资源路径
    private Integer status; // 状态
    private Long consumeTime;   // 耗时
    private Date operationTime; // 操作时间
    private String clientIp;    // 客户端 IP
    private String detailMsg;   // 详细信息
    private String exceptionMsg;    // 异常信息
    private String systemCode;  // 系统编码
}
