package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.model.tree.SimpleTreeNode;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.QueryServiceUtils;
import cn.example.demo.common.tools.obj.SimpleStringUtils;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.modules.sys.dao.SysMenuMapper;
import cn.example.demo.modules.sys.dao.SysRoleMapper;
import cn.example.demo.modules.sys.dao.SysRoleMenuMapper;
import cn.example.demo.modules.sys.dao.SysUserRoleMapper;
import cn.example.demo.modules.sys.model.dto.SysRoleDto;
import cn.example.demo.modules.sys.model.entity.SysMenu;
import cn.example.demo.modules.sys.model.entity.SysRole;
import cn.example.demo.modules.sys.model.entity.SysRoleMenu;
import cn.example.demo.modules.sys.model.entity.SysUserRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-29 17:47
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private SysMenuMapper menuMapper;

    @Override
    public ServiceResult insertRole(SysRoleDto roleDto) {
        SysRole role = EntityUtils.entityConvert(roleDto, new SysRole(), false);

        int row = roleMapper.insert(role);
        if (row == 1) {
            return ServiceResult.isSuccess("角色创建成功!", role);
        }
        return ServiceResult.isInternalError("角色创建失败，未知错误！");
    }

    @Override
    public ServiceResult updateRole(SysRoleDto roleDto) {
        SysRole entityTarget = roleMapper.selectById(roleDto.getRoleId());
        if (entityTarget == null) {
            return ServiceResult.isNotFound("不存在的角色");
        }

        int originHash = entityTarget.hashCode();
        EntityUtils.entityConvert(roleDto, entityTarget, true);

        // 比较修改前后的 hashcode
        if (originHash == entityTarget.hashCode()) {
            return ServiceResult.isNotModified("信息没有被修改！");
        }

        int row = roleMapper.updateById(entityTarget);
        if (row == 1) {
            return ServiceResult.isSuccess("角色创建成功!", entityTarget);
        }
        return ServiceResult.isInternalError("角色创建失败，未知错误！");
    }

    @Override
    public ServiceResult deleteRole(Integer roleId) {
        int row = roleMapper.deleteById(roleId);
        if (row != 0) {
            // 删除关系表记录
            LambdaQueryWrapper<SysUserRole> wrapper1 = new LambdaQueryWrapper<>();
            userRoleMapper.delete(wrapper1.eq(SysUserRole::getRoleId, roleId));

            LambdaQueryWrapper<SysRoleMenu> wrapper2 = new LambdaQueryWrapper<>();
            roleMenuMapper.delete(wrapper2.eq(SysRoleMenu::getRoleId, roleId));

            return ServiceResult.isSuccess("角色删除成功，id: " + roleId);
        }
        return ServiceResult.isNotModified("角色删除失败");
    }

    @Override
    public boolean isExistRoleName(String role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRole, role);
        return roleMapper.selectOne(wrapper) == null ? false : true;
    }

    @Override
    public PageBean queryRole(String roleName, PageBean pageBean) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(roleName)) {
            wrapper.like(SysRole::getRole, roleName);
        }

        Page<SysRole> pageResult = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize())
                .doSelectPage(() -> roleMapper.selectList(wrapper));

        return QueryServiceUtils.encapsulatePageBean(pageBean, pageResult);
    }

    @Override
    public SysRole findRoleById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public ServiceResult getMenusByRoleId(Integer roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<Integer> thisRoleMenusIds = roleMenuMapper.selectList(wrapper).stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());

        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.orderByAsc(SysMenu::getRank, SimpleTreeNode::getNode);
        List<SysMenu> menus = menuMapper.selectList(menuWrapper);

        menus.forEach(o -> {
            if (thisRoleMenusIds.contains(o.getNode())) {
                o.setCheckArr("1");
            }
        });

        if (menus.isEmpty()) {
            return ServiceResult.isNotFound("系统暂未找到菜单列表！");
        }
        return ServiceResult.isSuccess(menus);
    }

    @Override
    public ServiceResult saveRoleMenus(Integer roleId, List<Integer> menuIds) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        // 清空
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        int deleteRows = roleMenuMapper.delete(wrapper);

        if (menuIds != null && !menuIds.isEmpty()) {
            // 保存新的关系
            List<Integer> l = menuMapper.selectList(null).stream().map(SimpleTreeNode::getNode).collect(Collectors.toList());
            // 交集
            menuIds.retainAll(l);
            if (!menuIds.isEmpty()) {
                List<SysRoleMenu> roleMenus = new ArrayList<>();
                menuIds.forEach(o -> roleMenus.add(SysRoleMenu.builder()
                        .id(SimpleStringUtils.getRandomId())
                        .roleId(roleId)
                        .menuId(o).build()));

                int rows = roleMenuMapper.batchInsert(roleMenus);

                return ServiceResult.isSuccess("操作成功，授权菜单数量：" + rows, null);
            }
        }

        return ServiceResult.isSuccess("操作成功，解绑菜单数量：" + deleteRows, null);
    }

    @Override
    public ServiceResult saveRoleResource(Integer roleId, List<Integer> resourceIds) {
        return null;
    }

    @Override
    public ServiceResult queryRoleIdList() {
        List<SysRole> l = roleMapper.selectList(null);
        if (l.isEmpty()) {
            return ServiceResult.isNotFound("暂无角色列表！");
        }
        List<Map<String, Object>> l1 = new ArrayList<>();
        l.forEach(o -> {
            Map<String, Object> m = new HashMap<>();
            m.put("code", o.getRoleId());
            m.put("name", o.getRole());
            l1.add(m);

        });
        return ServiceResult.isSuccess(l1);
    }
}
