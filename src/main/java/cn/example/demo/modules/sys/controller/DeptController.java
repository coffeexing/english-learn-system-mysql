package cn.example.demo.modules.sys.controller;

import cn.example.demo.common.dictionary.RouterBaseDict;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.sys.model.dto.SysDeptDto;
import cn.example.demo.modules.sys.service.DeptService;
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
 * 部门控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 14:56
 */
@Validated
@RestController
@RequestMapping(RouterBaseDict.API_SYSTEM_PREFIX + "api/dept")
@Api(tags = {"部门操作接口"})
public class DeptController {
    @Resource
    private DeptService deptService;

    @Transactional
    @PostMapping("add")
    @ApiOperation(value = "新增部门")
    public HttpResponseResult addDept(@RequestBody @Validated(Insert.class) SysDeptDto deptDto) {
        ServiceResult result = deptService.insertDept(deptDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("成功新增部门：" + deptDto.getDeptName(), null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyDept(@RequestBody @Validated(Update.class) SysDeptDto deptDto) {
        ServiceResult result = deptService.updateDept(deptDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("基本信息修改成功，" + "部门ID：" + deptDto.getNode());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "查询部门")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getAllDept() {
        ServiceResult result = deptService.queryAllDeptListTree();
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getData());
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "查询部门")
    @GetMapping(value = "list/tree")
    public Object getAllDeptListTree() {
        Map<String, Object> map = new HashMap<>();

        ServiceResult result = deptService.queryAllDeptListTree();
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

    @ApiOperation(value = "查询部门")
    @GetMapping(value = "list")
    public Object getAllDeptList() {
        ServiceResult result = deptService.queryAllDeptList();
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getData());
        }
        return HttpDataResponseResult.isNotFound(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteDept(@PathVariable @ParamRegex.Integer String id) {
        Integer deptId = Integer.valueOf(id);
        ServiceResult result = deptService.deleteDept(deptId);

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }
}
