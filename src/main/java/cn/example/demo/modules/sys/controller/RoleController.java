package cn.example.demo.modules.sys.controller;

import cn.example.demo.common.dictionary.RouterBaseDict;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.sys.model.dto.SysRoleDto;
import cn.example.demo.modules.sys.model.dto.SysRoleMenuDto;
import cn.example.demo.modules.sys.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 角色控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 14:56
 */
@Validated
@RestController
@RequestMapping(RouterBaseDict.API_SYSTEM_PREFIX + "api/role")
@Api(tags = {"角色操作接口"})
public class RoleController {
    @Resource
    private RoleService roleService;

    @Transactional
    @PostMapping("add")
    @ApiOperation(value = "角色新增")
    public HttpResponseResult addRole(@RequestBody @Validated(Insert.class) SysRoleDto roleDto) {
        if (roleService.isExistRoleName(roleDto.getRole())) {
            return HttpDataResponseResult.isBadRequest("该角色名已被存在！", null);
        }

        ServiceResult result = roleService.insertRole(roleDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("成功新增角色：" + roleDto.getRole(), null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyRole(@RequestBody @Validated(Update.class) SysRoleDto menuDto) {
        ServiceResult result = roleService.updateRole(menuDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("基本信息修改成功，" + "角色ID：" + menuDto.getRole());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "查询角色")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getAllRole(@RequestParam(defaultValue = "") String roleName,
                                         @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") @ParamRegex.Integer String page,
                                         @ApiParam(value = "每页大小") @RequestParam(value = "limit", defaultValue = "10") @ParamRegex.Integer String limit) {
        // 分页条件
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(limit));

        PageBean result = roleService.queryRole(roleName, pageBean);
        if (result.getItems() == null || result.getItems().isEmpty()) {
            return HttpDataResponseResult.isNotFound("没有相关角色信息！", null);
        }
        return HttpDataResponseResult.isSuccess(result);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteRole(@PathVariable @ParamRegex.Integer String id) {
        Integer menuId = Integer.valueOf(id);
        ServiceResult result = roleService.deleteRole(menuId);

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "获取角色已授权菜单")
    @GetMapping(value = "menu/list/{roleId}")
    @Transactional
    public Object getMenuListByRoleId(@PathVariable @ParamRegex.Integer String roleId) {
        ServiceResult result = roleService.getMenusByRoleId(Integer.valueOf(roleId));

        if (result.getStatus() == HttpStatus.OK.value()) {
            Map<String, Object> map = new HashMap<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", HttpStatus.OK.value());
            map1.put("message", "默认");

            map.put("data", result.getData());
            map.put("status", map1);
            return map;
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "角色授权菜单")
    @PutMapping(value = "auth/menu")
    @Transactional
    public HttpResponseResult authMenu(@RequestBody @Validated(Update.class) SysRoleMenuDto roleDto) {
        ServiceResult result = roleService.saveRoleMenus(roleDto.getRoleId(), roleDto.getMenuIds());
        return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
    }

    @AuthEnable
    @GetMapping("list")
    public HttpResponseResult getAllUserList() {
        ServiceResult result = roleService.queryRoleIdList();
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getData());
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }
}
