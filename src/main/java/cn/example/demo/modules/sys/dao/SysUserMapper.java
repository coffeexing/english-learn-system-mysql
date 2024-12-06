package cn.example.demo.modules.sys.dao;

import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.model.entity.SysUserExample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    int deleteByExample(SysUserExample example);

    List<SysUser> selectByExample(SysUserExample example);

    List<SysUser> selectByExampleExtension(SysUserExample example);

    SysUser selectByPrimaryKey(Long userId);

    SysUser selectOneByUserName(String username);

    SysUser selectOneByUserNameExtension(String username);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * <p>
     * 批量插入用户
     * </P>
     *
     * @param sysUsers
     * @return
     */
    int batchInsert(List<SysUser> sysUsers);
}
