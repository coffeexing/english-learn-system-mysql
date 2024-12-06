package cn.example.demo.modules.sys.dao;

import cn.example.demo.modules.sys.model.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * <p>
     * 批量插入
     * </P>
     *
     * @param userRoleList
     * @return
     */
    int batchInsert(List<SysUserRole> userRoleList);
}
