package cn.example.demo.modules.english.dao;

import cn.example.demo.modules.english.model.entity.ExamScore;
import cn.example.demo.modules.english.model.entity.ExamScoreExample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ExamScoreMapper extends BaseMapper<ExamScore> {
    /**
     * <p>
     * 根据用户：汇总积分
     * </P>
     *
     * @param example
     * @return
     */
    List<ExamScore> aggregateByExample(ExamScoreExample example);

    /**
     * <p>
     * 一对一关联查询
     * </P>
     *
     * @param example
     * @return
     */
    List<ExamScore> selectAssociateByExample(ExamScoreExample example);
}
