package cn.example.demo.modules.sys.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.modules.sys.model.entity.SysDepartment;
import cn.example.demo.modules.sys.model.entity.SysMenu;
import cn.example.demo.modules.sys.model.entity.SysRole;
import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.service.DeptService;
import cn.example.demo.modules.sys.service.MenuService;
import cn.example.demo.modules.sys.service.RoleService;
import cn.example.demo.modules.sys.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 12:05
 */
@Validated
@Controller
@Api(tags = {"系统管理模板视图"})
public class ViewController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @Resource
    private DeptService deptService;

    /**
     * <p>
     * 主页
     * </P>
     *
     * @return
     */
    @AuthEnable
    @GetMapping(value = "/")
    public Object indexPage(HttpServletRequest request, Model model) {
        SysUser o = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        if (o != null) {
            model.addAttribute("auth", true);
            model.addAttribute("realName", o.getRealName());
            model.addAttribute("avatar", o.getAvatar());
        } else {
            model.addAttribute("auth", false);
            model.addAttribute("realName", "未登录");
            model.addAttribute("avatar", "/admin/images/account/portrait/default/u8.svg");
        }

        return "index";
    }

    //    ---------------------------------- 用户管理 ----------------------------------------
    @AuthEnable
    @GetMapping(value = "system/user/main")
    public Object getUserManagePage(HttpServletRequest request, Model model) {
        SysUser o = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        if (o != null && o.getUsername().equals("SuperAdmin")) {
            model.addAttribute("auth", true);
        } else {
            model.addAttribute("auth", false);
        }
        return "system/user/main";
    }

    @AuthEnable
    @GetMapping(value = "system/user/add")
    public Object getUserRegisterPage() {
        return "system/user/add";
    }

    @AuthEnable
    @GetMapping(value = "system/user/edit/{username}")
    public Object getUserEditPage(@PathVariable("username") String name, Model model) {
        SysUser user = userService.findUserByUsername(name);
        if (user != null) {
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("realName", user.getRealName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("phone", user.getPhone());
            model.addAttribute("sex", user.getSex());
            model.addAttribute("roleIds", user.getRoleIds());
            model.addAttribute("deptId", user.getDeptId());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "system/user/edit";
    }

    @AuthEnable
    @GetMapping(value = "system/user/own_center")
    public Object getUserOwnPage(HttpServletRequest request, Model model) {
        SysUser o = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        SysUser user = userService.findUserByUsername(o.getUsername());
        model.addAttribute("username", o.getUsername());
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("deptName", user.getDept());
        model.addAttribute("realName", user.getRealName());
        model.addAttribute("sex", user.getSex());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());

        return "system/user/center";
    }

    @AuthEnable
    @GetMapping(value = "system/user/edit_password")
    public Object getOwnPasswordEditPage() {
        return "system/user/editPassword";
    }

    //    ---------------------------------- 菜单管理 ----------------------------------------
    @AuthEnable
    @GetMapping(value = "system/menu/main")
    public Object getMenuManagePage() {
        return "system/menu/main";
    }

    @AuthEnable
    @GetMapping(value = "system/menu/add")
    public Object getMenuAddPage() {
        return "system/menu/add";
    }

    @AuthEnable
    @GetMapping(value = "system/menu/edit/{id}")
    public Object getMenuEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        SysMenu menu = menuService.findMenuById(Integer.valueOf(id));
        if (menu != null) {
            model.addAttribute("node", menu.getNode());
            model.addAttribute("parentNode", menu.getParentNode());
            model.addAttribute("title", menu.getTitle());
            model.addAttribute("type", menu.getType());
            model.addAttribute("href", menu.getHref());
            model.addAttribute("openType", menu.getOpenType());
            model.addAttribute("icon", menu.getIcon());
            model.addAttribute("rank", menu.getRank());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "system/menu/edit";
    }

    //    ---------------------------------- 角色管理 ----------------------------------------
    @AuthEnable
    @GetMapping(value = "system/role/main")
    public Object getRoleManagePage() {
        return "system/role/main";
    }

    @AuthEnable
    @GetMapping(value = "system/role/add")
    public Object getRoleAddPage() {
        return "system/role/add";
    }

    @AuthEnable
    @GetMapping(value = "system/role/edit/{id}")
    public Object getRoleEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        SysRole entity = roleService.findRoleById(Integer.valueOf(id));
        if (entity != null) {
            model.addAttribute("roleId", entity.getRoleId());
            model.addAttribute("roleName", entity.getRole());
            model.addAttribute("description", entity.getDescription());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "system/role/edit";
    }

    @AuthEnable
    @GetMapping(value = "system/role/power/{id}")
    public Object getRolePowerPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        model.addAttribute("roleId", id);
        return "system/role/power";
    }

    //    ---------------------------------- 部门管理 ----------------------------------------
    @AuthEnable
    @GetMapping(value = "system/dept/main")
    public Object getDeptManagePage() {
        return "system/dept/main";
    }

    @AuthEnable
    @GetMapping(value = "system/dept/add")
    public Object getDeptAddPage() {
        return "system/dept/add";
    }

    @AuthEnable
    @GetMapping(value = "system/dept/edit/{id}")
    public Object getDeptEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        SysDepartment entity = deptService.findDeptById(Integer.valueOf(id));
        if (entity != null) {
            model.addAttribute("node", entity.getNode());
            model.addAttribute("parentNode", entity.getParentNode());
            model.addAttribute("deptName", entity.getDeptName());
            model.addAttribute("rank", entity.getRank());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "system/dept/edit";
    }
}
