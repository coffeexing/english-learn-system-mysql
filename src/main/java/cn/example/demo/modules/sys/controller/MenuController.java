package cn.example.demo.modules.sys.controller;

import cn.example.demo.common.dictionary.RouterBaseDict;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.sys.model.dto.SysMenuDto;
import cn.example.demo.modules.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 菜单控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 14:56
 */
@Validated
@RestController
@RequestMapping(RouterBaseDict.API_SYSTEM_PREFIX + "api/menu")
@Api(tags = {"菜单操作接口"})
public class MenuController {
    @Resource
    private MenuService menuService;

    @Transactional
    @PostMapping("add")
    @ApiOperation(value = "新增菜单")
    public HttpResponseResult addMenu(@RequestBody @Validated(Insert.class) SysMenuDto menuDto) {
        ServiceResult result = menuService.insertMenu(menuDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("成功新增菜单：" + menuDto.getTitle(), null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyMenu(@RequestBody @Validated(Update.class) SysMenuDto menuDto) {
        ServiceResult result = menuService.updateMenu(menuDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("基本信息修改成功，" + "菜单ID：" + menuDto.getNode());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getAllMenu() {
        ServiceResult result = menuService.queryAllMenuList();
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getData());
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteMenu(@PathVariable @ParamRegex.Integer String id) {
        ServiceResult result = menuService.deleteMenuById(Integer.valueOf(id));

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping(value = "menu/list")
    public Object getMenuList() {
        Map<String, Object> map = new HashMap<>();

        ServiceResult result = menuService.queryAllMenuList();
        if (result.getStatus() == HttpStatus.OK.value()) {
            Map<String, Object> map1 = new HashMap<>();

            map1.put("code", HttpStatus.OK.value());
            map1.put("message", "默认");

            map.put("data", result.getData());
            map.put("status", map1);
            return map;
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }
}
