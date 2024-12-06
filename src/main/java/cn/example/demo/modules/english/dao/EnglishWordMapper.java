package cn.example.demo.modules.english.dao;

import cn.example.demo.modules.english.model.entity.EnglishWord;
import cn.example.demo.modules.english.model.entity.EnglishWordExample;
import cn.example.demo.modules.english.model.entity.ExamScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface EnglishWordMapper extends BaseMapper<EnglishWord> {
    /**
     * <p>
     * 批量插入
     * </P>
     *
     * @param englishWordList
     * @return
     */
    int batchInsert(List<EnglishWord> englishWordList);

    /**
     * <p>
     * 根据用户：汇总积分
     * </P>
     *
     * @param example
     * @return
     */
    List<ExamScore> selectAssociateByExample(EnglishWordExample example);
}
