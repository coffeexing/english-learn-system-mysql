package cn.example.demo.modules.sys.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.modules.sys.model.dto.SysDeptDto;
import cn.example.demo.modules.sys.model.entity.SysDepartment;

/**
 * <p>
 * 部门服务接口
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/2 11:27
 */
public interface DeptService {
    /**
     * <p>
     * 新增部门
     * </P>
     *
     * @param deptDto
     * @return
     */
    ServiceResult insertDept(SysDeptDto deptDto);

    /**
     * <p>
     * 更新部门
     * </P>
     *
     * @param deptDto
     * @return
     */
    ServiceResult updateDept(SysDeptDto deptDto);

    /**
     * <p>
     * 删除某部门
     * </P>
     *
     * @param deptId
     * @return
     */
    ServiceResult deleteDept(Integer deptId);

    /**
     * <p>
     * 查询所有部门列表
     * </P>
     *
     * @return
     */
    ServiceResult queryAllDeptListTree();

    ServiceResult queryAllDeptList();

    /**
     * <p>
     * 部门否存在
     * </P>
     *
     * @param deptId
     * @return
     */
    boolean isExistDept(Integer deptId);

    /**
     * <p>
     * 根据 ID 查询单个
     * </P>
     *
     * @param id
     * @return
     */
    SysDepartment findDeptById(Integer id);
}
