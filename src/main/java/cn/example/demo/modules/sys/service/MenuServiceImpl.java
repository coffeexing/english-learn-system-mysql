package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.model.tree.SimpleTreeNode;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.common.tools.tree.TreeUtils;
import cn.example.demo.modules.sys.dao.SysMenuMapper;
import cn.example.demo.modules.sys.dao.SysRoleMenuMapper;
import cn.example.demo.modules.sys.model.dto.SysMenuDto;
import cn.example.demo.modules.sys.model.entity.SysMenu;
import cn.example.demo.modules.sys.model.entity.SysRoleMenu;
import cn.example.demo.modules.sys.model.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-29 13:08
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public ServiceResult insertMenu(SysMenuDto menuDto) {
        SysMenu menu = EntityUtils.entityConvert(menuDto, new SysMenu(), false);
        if (menuDto.getType().equals(0)) {  // 如果为目录
            menu.setIsDirectory(true);
            menu.setIsNode(false);
        } else { // 菜单
            menu.setIsDirectory(false);
            menu.setIsNode(true);
        }

        if (menuDto.getParentNode() == null || menuDto.getParentNode() < 0) {
            menu.setParentNode(TreeUtils.ROOT_NODE);
        } else {
            menu.setParentNode(menuDto.getParentNode());
        }

        int row = menuMapper.insert(menu);
        if (row == 1) {
            return ServiceResult.isSuccess("菜单创建成功!", menu);
        }
        return ServiceResult.isInternalError("菜单创建失败，未知错误！");
    }

    @Override
    public ServiceResult updateMenu(SysMenuDto menuDto) {
        SysMenu entityTarget = new SysMenu();
        int originHash = entityTarget.hashCode();
        SysMenu menu = EntityUtils.entityConvert(menuDto, entityTarget, true);
        if (menuDto.getType().equals(0)) {  // 如果为目录
            menu.setIsDirectory(true);
            menu.setIsNode(false);
        } else { // 菜单
            menu.setIsDirectory(false);
            menu.setIsNode(true);
        }

        if (menuDto.getParentNode() == null || menuDto.getParentNode() < 0) {
            menu.setParentNode(TreeUtils.ROOT_NODE);
        } else {
            menu.setParentNode(menuDto.getParentNode());
        }

        // 比较修改前后的 hashcode
        if (originHash == menu.hashCode()) {
            return ServiceResult.isNotModified("信息没有被修改！");
        }

        // 保存修改信息
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SimpleTreeNode::getNode, menuDto.getNode());
        int row = menuMapper.update(menu, queryWrapper);
        if (row == 1) {
            return ServiceResult.isSuccess("菜单更新成功!", menu);
        }
        return ServiceResult.isNotFound("菜单更新失败，不存在的菜单！");
    }

    @Override
    public ServiceResult deleteMenuById(Integer menuId) {
        int row = menuMapper.deleteById(menuId);
        if (row != 0) {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            roleMenuMapper.delete(wrapper.eq(SysRoleMenu::getMenuId, menuId));
            return ServiceResult.isSuccess("菜单删除成功，id: " + menuId);
        }
        return ServiceResult.isNotModified("菜单删除失败");
    }

    @Override
    public ServiceResult queryAllMenuList() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getRank, SimpleTreeNode::getNode);
        List<SysMenu> menus = menuMapper.selectList(wrapper);
        if (menus.isEmpty()) {
            return ServiceResult.isNotFound("没有菜单列表数据！");
        }
        return ServiceResult.isSuccess(menus);
    }

    @Override
    public ServiceResult getMenuTree(SysUser user) {
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.orderByAsc(SysMenu::getRank, SimpleTreeNode::getNode);
        // 非超级管理员账号，根据绑定角色查询菜单
        if (!user.getUsername().equals("SuperAdmin")) {
            // 角色菜单子查询语句
            LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
            roleMenuWrapper.select(SysRoleMenu::getMenuId).in(SysRoleMenu::getRoleId, user.getRoleIds());
            List<Integer> l = roleMenuMapper.selectList(roleMenuWrapper).stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
            menuWrapper.in(SimpleTreeNode::getNode, l);
        }

        List<SysMenu> menus = menuMapper.selectList(menuWrapper);

        if (!menus.isEmpty()) {
            List<SimpleTreeNode> nodeList = new ArrayList<>();
            menus.forEach(o -> {
                o.setId(o.getNode());
                o.setParentId(o.getParentNode());
                nodeList.add(o);
            });

            // 列表转树结构
            List<SimpleTreeNode> treeNodes = TreeUtils.listConvertTree(nodeList, TreeUtils.ROOT_NODE);

            return ServiceResult.isSuccess(treeNodes);
        }
        return ServiceResult.isNotFound("没有菜单数据！");
    }

    @Override
    public SysMenu findMenuById(Integer node) {
        if (node != null) {
            return menuMapper.selectById(node);
        }
        return null;
    }
}
