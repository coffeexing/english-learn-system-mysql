package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.model.tree.SimpleTreeNode;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.common.tools.tree.TreeUtils;
import cn.example.demo.modules.sys.dao.SysDepartmentMapper;
import cn.example.demo.modules.sys.dao.SysUserDeptMapper;
import cn.example.demo.modules.sys.model.dto.SysDeptDto;
import cn.example.demo.modules.sys.model.entity.SysDepartment;
import cn.example.demo.modules.sys.model.entity.SysUserDept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-30 0:28
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private SysDepartmentMapper departmentMapper;
    @Resource
    private SysUserDeptMapper userDeptMapper;

    @Override
    public ServiceResult insertDept(SysDeptDto deptDto) {
        SysDepartment department = EntityUtils.entityConvert(deptDto, new SysDepartment(), false);
        department.setIsDirectory(true);
        department.setIsNode(true);

        if (deptDto.getParentNode() == null || deptDto.getParentNode() < 0) {
            department.setParentNode(TreeUtils.ROOT_NODE);
        } else {
            department.setParentNode(deptDto.getParentNode());
        }

        int row = departmentMapper.insert(department);
        if (row == 1) {
            return ServiceResult.isSuccess("部门创建成功!", department);
        }
        return ServiceResult.isInternalError("部门创建失败，未知错误！");
    }

    @Override
    public ServiceResult updateDept(SysDeptDto deptDto) {
        SysDepartment department = EntityUtils.entityConvert(deptDto, new SysDepartment(), true);

        if (deptDto.getParentNode() != null && deptDto.getParentNode() > -1) {
            department.setParentNode(deptDto.getParentNode());
        }
        department.setNode(deptDto.getNode());

        int row = departmentMapper.updateById(department);
        if (row == 1) {
            return ServiceResult.isSuccess("部门创建成功!", department);
        }
        return ServiceResult.isInternalError("部门创建失败，未知错误！");
    }

    @Override
    public ServiceResult deleteDept(Integer deptId) {
        int row = departmentMapper.deleteById(deptId);
        if (row != 0) {
            LambdaQueryWrapper<SysUserDept> wrapper = new LambdaQueryWrapper<>();
            userDeptMapper.delete(wrapper.eq(SysUserDept::getDeptId, deptId));
            return ServiceResult.isSuccess("部门删除成功，id: " + deptId);
        }
        return ServiceResult.isNotModified("部门删除失败");
    }

    @Override
    public ServiceResult queryAllDeptListTree() {
        LambdaQueryWrapper<SysDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDepartment::getRank, SimpleTreeNode::getNode);
        List<SysDepartment> departments = departmentMapper.selectList(wrapper);
        if (departments.isEmpty()) {
            return ServiceResult.isNotFound("没有部门列表数据！");
        }
        return ServiceResult.isSuccess(departments);
    }

    @Override
    public ServiceResult queryAllDeptList() {
        List<SysDepartment> l = departmentMapper.selectList(null);
        if (l.isEmpty()) {
            return ServiceResult.isNotFound("暂无部门列表！");
        }
        List<Map<String, Object>> l1 = new ArrayList<>();
        l.forEach(o -> {
            Map<String, Object> m = new HashMap<>();
            m.put("code", o.getNode());
            m.put("name", o.getDeptName());
            l1.add(m);

        });
        return ServiceResult.isSuccess(l1);
    }

    @Override
    public boolean isExistDept(Integer deptId) {
        return departmentMapper.selectById(deptId) == null ? false : true;
    }

    @Override
    public SysDepartment findDeptById(Integer id) {
        return departmentMapper.selectById(id);
    }
}
