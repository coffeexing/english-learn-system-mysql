package cn.example.demo.modules.sys.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.dictionary.RouterBaseDict;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.sys.model.dto.SysUserBaseDto;
import cn.example.demo.modules.sys.model.dto.SysUserDto;
import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.service.MenuService;
import cn.example.demo.modules.sys.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 14:56
 */
@Validated
@RestController
@RequestMapping(RouterBaseDict.API_SYSTEM_PREFIX + "api/user")
@Api(tags = {"用户操作接口"})
public class UserController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;

    @GetMapping("menu-file")
    @ApiOperation(value = "获取用户菜单数据(静态Json文件）")
    public Object getUserMenu() throws IOException {
        String resourcePath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8.name());  // 解码
        FileInputStream fIs = new FileInputStream(new File(resourcePath + "static/data/menu.json"));
        ByteArrayOutputStream bOs = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int i;
        while ((i = fIs.read(buffer, 0, buffer.length)) != -1) {
            bOs.write(buffer, 0, i);
        }
        return new ObjectMapper().readValue(bOs.toByteArray(), List.class);
    }

    @AuthEnable
    @GetMapping("menu")
    public Object getUserMenu(HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        if (user.getRoleIds() == null && !user.getUsername().equals("SuperAdmin")) {
            return HttpDataResponseResult.isUnauthorized("没有权限操作，请联系管理员", null);
        }

        ServiceResult result = menuService.getMenuTree(user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return result.getData();
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @Transactional
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public HttpResponseResult registerUser(@RequestBody @Validated SysUserDto userDto) {
        if (userService.isExistUser(userDto.getUsername())) {
            return HttpDataResponseResult.isBadRequest("该用户名已被注册！", null);
        }
        userDto.setAvatar("/admin/images/account/portrait/default/avatar.jpg");
        // 如果角色为空，默认给学生角色
        if (ObjectUtil.isEmpty(userDto.getRoleIds())) {
            userDto.setRoleIds(Arrays.asList(2));
        }
        ServiceResult result = userService.insertUser(userDto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("成功注册用户：" + userDto.getUsername(), null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "修改用户")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyUser(@RequestBody @Validated(Update.class) SysUserDto user) {
        if (!userService.isExistUser(user.getUsername())) {
            return HttpDataResponseResult.isBadRequest("修改用户信息失败，不存在的用户名！", null);
        }

        ServiceResult result = userService.updateUser(user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("用户：" + user.getUsername() + "，基本信息修改成功！");
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "修改个人信息")
    @PutMapping(value = "own_center/modify")
    @Transactional
    public HttpResponseResult modifyOwnInfo(@RequestBody @Validated SysUserBaseDto baseDto,
                                            HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        ServiceResult result = userService.updateOwnUser(baseDto, user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "验证个人原密码")
    @PostMapping(value = "old_password/verify")
    @Transactional
    public HttpResponseResult verifyOwnOldPassword(@RequestParam(defaultValue = "") @NotEmpty(message = "原密码不能为空！") String oldPassword,
                                                   HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            return HttpDataResponseResult.isSuccess("原密码验证成功!！", null);
        }
        return HttpDataResponseResult.isNotModified("验证失败，原密码不正确！", null);
    }

    @AuthEnable
    @ApiOperation(value = "修改个人密码")
    @PutMapping(value = "own_password/modify")
    @Transactional
    public HttpResponseResult modifyOwnPassword(@RequestParam(defaultValue = "") @NotEmpty(message = "密码不能设置为空！") String newPassword,
                                                HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        ServiceResult result = userService.updateOwnPassword(newPassword, user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getUser(@RequestParam(defaultValue = "") String username,
                                      @RequestParam(defaultValue = "") String realName,
                                      @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") @ParamRegex.Integer String page,
                                      @ApiParam(value = "每页大小") @RequestParam(value = "limit", defaultValue = "10") @ParamRegex.Integer String limit) {
        // 分页条件
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(limit));

        PageBean result = userService.queryUser(username, realName, pageBean);
        if (result.getItems() == null || result.getItems().isEmpty()) {
            return HttpDataResponseResult.isNotFound("没有相关用户信息！", null);
        }
        return HttpDataResponseResult.isSuccess(result);
    }

    @AuthEnable
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteUser(@PathVariable @ParamRegex.Integer String id) {
        SysUser user = userService.findUserByUserId(Integer.valueOf(id));
        if (user.getUsername().equals("SuperAdmin")) {
            return HttpDataResponseResult.isNotModified("超级管理员账号不能删除！", null);
        }

        ServiceResult result = userService.deleteUser(user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @GetMapping("list")
    public HttpResponseResult getAllUserList(@ApiParam(value = "用户Id") @RequestParam(defaultValue = "") List<Integer> userIds,
                                             @ApiParam(value = "是否包含") @RequestParam(defaultValue = "1") String isInclusive) {
        ServiceResult result = userService.queryUserIdList(userIds, isInclusive.equals("1"));
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }
}
