package cn.example.demo.modules.sys.dao.log;

import cn.example.demo.modules.sys.model.entity.log.SsoSystemAccessLog;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 【系统访问日志】数据持久层
 * </p>
 *
 * @author Lizuxian
 * @create 2020-11-16 16:45
 */
@Repository
public interface SystemAccessLogMapper {
    int insert(SsoSystemAccessLog record);
}
