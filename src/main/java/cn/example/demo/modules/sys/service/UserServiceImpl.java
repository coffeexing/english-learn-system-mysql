package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.QueryServiceUtils;
import cn.example.demo.common.tools.obj.SimpleStringUtils;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.modules.sys.dao.SysRoleMapper;
import cn.example.demo.modules.sys.dao.SysUserDeptMapper;
import cn.example.demo.modules.sys.dao.SysUserMapper;
import cn.example.demo.modules.sys.dao.SysUserRoleMapper;
import cn.example.demo.modules.sys.model.dto.SysUserBaseDto;
import cn.example.demo.modules.sys.model.dto.SysUserDto;
import cn.example.demo.modules.sys.model.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 9:31
 */
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserDeptMapper userDeptMapper;
    @Resource
    private DeptService deptService;

    @Override
    public SysUser findUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, username);
        SysUser user = userMapper.selectOne(userQueryWrapper);

        if (user != null) {
            // 查询用户角色
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, user.getUserId());
            List<Integer> roles = userRoleMapper.selectList(queryWrapper).stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
            if (!roles.isEmpty()) {
                user.setRoleIds(roles);
            }
            // 查询用户部门
            LambdaQueryWrapper<SysUserDept> wrapper = new LambdaQueryWrapper<>();
            SysUserDept userDept = userDeptMapper.selectOne(wrapper.eq(SysUserDept::getUserId, user.getUserId()));
            if (userDept != null) {
                SysDepartment dept = deptService.findDeptById(userDept.getDeptId());
                user.setDeptId(dept.getNode());
                user.setDept(dept.getDeptName());
            }
        }

        return user;
    }

    @Override
    public SysUser findUserByUserId(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public boolean isExistUser(String username) {
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, username);
        return userMapper.selectOne(userQueryWrapper) == null ? false : true;
    }

    @Override
    public ServiceResult insertUser(SysUserDto userDto) {
        SysUser user = EntityUtils.entityConvert(userDto, new SysUser(new Date(), (short) 1), false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        int result = userMapper.insert(user);
        if (result == 1) {
            userDto.setUserId(user.getUserId());
            // 批量保存用户角色关系
            userBindRoles(userDto);
            // 保存用户部门关系
            userBindDept(userDto);
            return ServiceResult.isSuccess(user);
        }
        return ServiceResult.isInternalError("用户【" + userDto.getUsername() + "】新增失败！");
    }

    @Override
    public ServiceResult updateUser(SysUserDto userDto) {
        SysUser entityTarget = new SysUser();
        int originSsoUserHash = entityTarget.hashCode();
        SysUser sysUser = EntityUtils.entityConvert(userDto, entityTarget, true);

        if (StringUtils.isNotEmpty(userDto.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        // 比较修改前后的 hashcode
        if (originSsoUserHash == sysUser.hashCode()) {
            return ServiceResult.isNotModified("用户信息没有被修改！");
        }

        sysUser.setLastModifyTime(new Date());
        // 保存修改的用户信息
        LambdaUpdateWrapper<SysUser> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(SysUser::getUsername, userDto.getUsername());
        int result = userMapper.update(sysUser, userUpdateWrapper);

        // 批量保存用户角色关系
        userBindRoles(userDto);
        // 保存用户部门关系
        userBindDept(userDto);

        return ServiceResult.isSuccess("操作成功，更新记录：" + result);
    }

    @Override
    public ServiceResult updateOwnUser(SysUserBaseDto userDto, SysUser user) {
        SysUser updateUser = EntityUtils.entityConvert(userDto, new SysUser(), true);
        updateUser.setUserId(user.getUserId());

        int row = userMapper.updateById(updateUser);
        if (row == 1) {
            return ServiceResult.isSuccess("个人基本信息已修改！");
        }
        return ServiceResult.isNotModified("个人基本信息没有被修改！");
    }

    @Override
    public ServiceResult updateOwnPassword(String password, SysUser user) {
        String encodedPass = passwordEncoder.encode(password);

        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword, encodedPass)
                .eq(SysUser::getUserId, user.getUserId());
        int row = userMapper.update(new SysUser(), updateWrapper);
        if (row == 1) {
            user.setPassword(encodedPass);
            return ServiceResult.isSuccess("密码已修改！");
        }
        return ServiceResult.isNotModified("密码暂时无法修改！");
    }

    @Override
    public PageBean queryUser(String username, String name, PageBean pageBean) {
        LambdaQueryWrapper<SysUser> userQueryMapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(username)) {
            userQueryMapper.like(SysUser::getUsername, username);
        }
        if (StringUtils.isNotEmpty(name)) {
            userQueryMapper.like(SysUser::getRealName, name);
        }

        Page<SysUser> pageResult = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize())
                .doSelectPage(() -> userMapper.selectList(userQueryMapper));

        return QueryServiceUtils.encapsulatePageBean(pageBean, pageResult);
    }

    @Override
    public ServiceResult queryUserIdList(List<Integer> userIds, boolean isInclusive) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (userIds != null && !userIds.isEmpty()) {
            if (isInclusive) {
                queryWrapper.in(SysUser::getUserId, userIds);
            } else {
                queryWrapper.notIn(SysUser::getUserId, userIds);
            }
        }

        List<SysUser> l = userMapper.selectList(queryWrapper);

        List<Map<String, Object>> l1 = new ArrayList<>();
        l.forEach(o -> {
            Map<String, Object> m = new HashMap<>();
            m.put("code", o.getUserId());
            m.put("name", o.getUsername());
            l1.add(m);

        });
        return ServiceResult.isSuccess(l1);
    }

    @Override
    public ServiceResult deleteUser(SysUser user) {
        // 删除用户角色关系
        LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(SysUserRole::getUserId, user.getUserId());
        userRoleMapper.delete(userRoleLambdaQueryWrapper);
        // 删除用户部门关系
        LambdaQueryWrapper<SysUserDept> deptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deptLambdaQueryWrapper.eq(SysUserDept::getUserId, user.getUserId());
        userDeptMapper.delete(deptLambdaQueryWrapper);

        // 删除用户数据
        int i = userMapper.deleteById(user.getUserId());
        if (i == 1) {
            return ServiceResult.isSuccess("已删除用户：" + user.getUsername(), null);
        }
        return ServiceResult.isInternalError("删除用户【" + user.getUsername() + "】失败");
    }

    @Override
    public ServiceResult userBindRoles(SysUserDto userDto) {
        // 批量保存用户角色关系
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty()) {
            LambdaQueryWrapper<SysRole> roleQueryMapper = new LambdaQueryWrapper<>();
            roleQueryMapper.in(SysRole::getRoleId, userDto.getRoleIds());
            List<Integer> bindRoles = roleMapper.selectList(roleQueryMapper).stream().map(SysRole::getRoleId).distinct().collect(Collectors.toList());
            if (!bindRoles.isEmpty()) {
                // 删除已有绑定关系
                LambdaQueryWrapper<SysUserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>();
                userRoleQueryWrapper.eq(SysUserRole::getUserId, userDto.getUserId());
                userRoleMapper.delete(userRoleQueryWrapper);
                // 保存新的绑定关系
                int rows = userRoleMapper.batchInsert(encapsulateNewUserRoles(bindRoles, userDto.getUserId()));

                return ServiceResult.isSuccess("成功绑定角色数量：" + rows, null);
            }

            return ServiceResult.isNotModified("用户角色绑定失败，不存在的角色列表!");
        }

        return ServiceResult.isNotModified("用户角色绑定失败，关键参数不能为空！");
    }

    @Override
    public ServiceResult userBindDept(SysUserDto userDto) {
        if (userDto.getDeptId() != null) {
            if (!deptService.isExistDept(userDto.getDeptId())) {
                return ServiceResult.isNotFound("不存在的部门ID");
            }

            LambdaQueryWrapper<SysUserDept> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUserDept::getUserId, userDto.getUserId());
            SysUserDept userDept = userDeptMapper.selectOne(wrapper);

            if (userDept == null) {
                SysUserDept e = SysUserDept.builder().deptId(userDto.getDeptId()).userId(userDto.getUserId()).build();
                userDeptMapper.insert(e);

                return ServiceResult.isSuccess("成功分配部门！", null);
            } else {
                userDept.setDeptId(userDto.getDeptId());
                userDeptMapper.updateById(userDept);
                return ServiceResult.isSuccess("成功修改用户所属部门！");
            }
        }

        return ServiceResult.isNotModified("用户部门绑定失败，关键参数不能为空！");
    }

    @Override
    public ServiceResult userUnbindRoles(SysUserDto userDto) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userDto.getUserId());

        int rows = userRoleMapper.delete(queryWrapper);
        return ServiceResult.isSuccess("当前用户成功解绑所有角色，删除记录数：" + rows, null);
    }

    @Override
    public List<SysUserRole> getAllUserBindRoles(Integer userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        return userRoleMapper.selectList(queryWrapper);
    }

    /**
     * <p>
     * 封装用户角色绑定列表
     * </P>
     *
     * @param roleIds
     * @param userId
     * @return
     */
    protected List<SysUserRole> encapsulateNewUserRoles(List<Integer> roleIds, Integer userId) {
        List<SysUserRole> l = new ArrayList<>();
        roleIds.forEach(o -> {
            SysUserRole uR = SysUserRole.builder()
                    .id(SimpleStringUtils.getRandomId())
                    .userId(userId)
                    .roleId(o).build();
            l.add(uR);
        });
        return l;
    }
}
