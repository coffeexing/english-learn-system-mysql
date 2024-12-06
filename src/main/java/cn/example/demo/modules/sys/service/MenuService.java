package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.modules.sys.model.dto.SysMenuDto;
import cn.example.demo.modules.sys.model.entity.SysMenu;
import cn.example.demo.modules.sys.model.entity.SysUser;

/**
 * <p>
 * 菜单服务接口
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/2 11:27
 */
public interface MenuService {
    /**
     * <p>
     * 新增菜单
     * </P>
     *
     * @param menuDto
     * @return
     */
    ServiceResult insertMenu(SysMenuDto menuDto);

    /**
     * <p>
     * 更新菜单
     * </P>
     *
     * @param menuDto
     * @return
     */
    ServiceResult updateMenu(SysMenuDto menuDto);

    /**
     * <p>
     * 删除菜单
     * </P>
     *
     * @param menuId
     * @return
     */
    ServiceResult deleteMenuById(Integer menuId);

    /**
     * <p>
     * 查询所有菜单列表
     * </P>
     *
     * @return
     */
    ServiceResult queryAllMenuList();

    /**
     * <p>
     * 获取用户菜单树
     * </P>
     *
     * @return
     */
    ServiceResult getMenuTree(SysUser user);

    /**
     * <p>
     * 根据 ID 查询单个
     * </P>
     *
     * @param node
     * @return
     */
    SysMenu findMenuById(Integer node);
}
