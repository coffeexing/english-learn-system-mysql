package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.modules.sys.model.dto.SysUserBaseDto;
import cn.example.demo.modules.sys.model.dto.SysUserDto;
import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.model.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户服务接口
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 9:28
 */
public interface UserService {
    /**
     * <p>
     * 用户名是否存在
     * </P>
     *
     * @param username
     * @return
     */
    boolean isExistUser(String username);

    /**
     * <p>
     * 根据用户名获取用户
     * </P>
     *
     * @param username
     * @return
     */
    SysUser findUserByUsername(String username);

    /**
     * <p>
     * 根据用户Id获取用户
     * </P>
     *
     * @param userId
     * @return
     */
    SysUser findUserByUserId(Integer userId);

    /**
     * <p>
     * 新增用户
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult insertUser(SysUserDto userDto);

    /**
     * <p>
     * 更新用户
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult updateUser(SysUserDto userDto);

    /**
     * <p>
     * 修改个人信息
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult updateOwnUser(SysUserBaseDto userDto, SysUser user);

    /**
     * <p>
     * 修改个人密码
     * </P>
     *
     * @return
     */
    ServiceResult updateOwnPassword(String password, SysUser user);

    /**
     * <p>
     * 按条件查询用户
     * </P>
     *
     * @param username
     * @param name
     * @param pageBean
     * @return
     */
    PageBean queryUser(String username, String name, PageBean pageBean);

    /**
     * <p>
     * 查找包含或不包含指定参数 userIds 的所有用户列表
     * </P>
     *
     * @param userIds
     * @param isInclusive
     * @return
     */
    ServiceResult queryUserIdList(List<Integer> userIds, boolean isInclusive);

    /**
     * <p>
     * 删除用户
     * </P>
     *
     * @param user
     * @return
     */
    ServiceResult deleteUser(SysUser user);

    /**
     * <p>
     * 用户绑定角色
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult userBindRoles(SysUserDto userDto);

    /**
     * <p>
     * 用户绑定部门
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult userBindDept(SysUserDto userDto);

    /**
     * <p>
     * 用户解绑角色
     * </P>
     *
     * @param userDto
     * @return
     */
    ServiceResult userUnbindRoles(SysUserDto userDto);

    /**
     * <p>
     * 获取所有绑定的角色
     * </P>
     *
     * @param userId
     * @return
     */
    List<SysUserRole> getAllUserBindRoles(Integer userId);
}
