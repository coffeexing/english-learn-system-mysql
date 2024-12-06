package cn.example.demo.modules.sys.dao;

import cn.example.demo.modules.sys.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * <p>
     * 批量插入
     * </P>
     *
     * @param roleMenus
     * @return
     */
    int batchInsert(List<SysRoleMenu> roleMenus);
}
