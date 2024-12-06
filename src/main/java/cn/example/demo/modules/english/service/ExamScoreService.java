package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.modules.english.model.dto.ExamScoreDto;
import cn.example.demo.modules.english.model.entity.ExamScore;
import cn.example.demo.modules.sys.model.entity.SysUser;

import java.util.Date;

/**
 * 描述：成绩单服务接口
 *
 * @author Lizuxian
 * @create 2021/05/30 00:24:51
 */
public interface ExamScoreService {
    /**
     * 新增成绩单
     *
     * @param examScoreDto
     * @return
     */
    ServiceResult insertExamScore(ExamScoreDto examScoreDto, SysUser user);

    /**
     * 更新成绩单
     *
     * @param examScoreDto
     * @return
     */
    ServiceResult updateExamScore(ExamScoreDto examScoreDto);

    /**
     * @return
     */
    PageBean queryExamScore(Integer userId, String bookCode, Date examTimeStart, Date examTimeEnd, PageBean pageBean);

    /**
     * 删除成绩单
     *
     * @param id
     */
    ServiceResult deleteExamScore(Integer id);

    /**
     * 根据ID查询成绩单
     *
     * @param id
     */
    ExamScore findExamScoreById(Integer id);

    /**
     * <p>
     * 获取个人概览数据
     * </P>
     *
     * @param user
     * @return
     */
    ServiceResult getOverviewData(SysUser user);
}
