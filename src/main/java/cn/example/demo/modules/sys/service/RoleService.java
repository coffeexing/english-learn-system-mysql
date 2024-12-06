package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.modules.sys.model.dto.SysRoleDto;
import cn.example.demo.modules.sys.model.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色服务接口
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/2 11:27
 */
public interface RoleService {
    /**
     * <p>
     * 新增角色
     * </P>
     *
     * @param roleDto
     * @return
     */
    ServiceResult insertRole(SysRoleDto roleDto);

    /**
     * <p>
     * 更新角色
     * </P>
     *
     * @param roleDto
     * @return
     */
    ServiceResult updateRole(SysRoleDto roleDto);

    /**
     * <p>
     * 删除某角色
     * </P>
     *
     * @param roleId
     * @return
     */
    ServiceResult deleteRole(Integer roleId);

    /**
     * <p>
     * 存在某角色名
     * </P>
     *
     * @param role
     * @return
     */
    boolean isExistRoleName(String role);

    /**
     * <p>
     * 查询角色
     * </P>
     *
     * @return
     */
    PageBean queryRole(String roleName, PageBean pageBean);

    /**
     * <p>
     * 根据 ID 查询单个
     * </P>
     *
     * @param id
     * @return
     */
    SysRole findRoleById(Integer id);

    /**
     * <p>
     * 根据角色Id获取菜单
     * </P>
     *
     * @param roleId
     * @return
     */
    ServiceResult getMenusByRoleId(Integer roleId);

    /**
     * <p>
     * 保存角色菜单关系
     * </P>
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    ServiceResult saveRoleMenus(Integer roleId, List<Integer> menuIds);

    /**
     * <p>
     * 保存角色资源关系
     * </P>
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    ServiceResult saveRoleResource(Integer roleId, List<Integer> resourceIds);

    /**
     * <p>
     * 查找所有角色列表
     * </P>
     *
     * @return
     */
    ServiceResult queryRoleIdList();
}
