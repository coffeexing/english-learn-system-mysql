package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.QueryServiceUtils;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.modules.english.dao.EnglishBookMapper;
import cn.example.demo.modules.english.dao.ExamScoreMapper;
import cn.example.demo.modules.english.model.dto.ExamScoreDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.ExamScore;
import cn.example.demo.modules.english.model.entity.ExamScoreExample;
import cn.example.demo.modules.sys.model.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 描述：成绩单接口实现类
 *
 * @author Lizuxian
 * @create 2021/05/30 00:41:35
 */
@Service
public class ExamScoreServiceImpl implements ExamScoreService {
    @Resource
    private ExamScoreMapper examScoreMapper;
    @Resource
    private EnglishBookMapper bookMapper;

    @Override
    public ServiceResult insertExamScore(ExamScoreDto examScoreDto, SysUser user) {
        ExamScore examScore = ExamScore.builder()
                .userId(user.getUserId())
                .bookCode(examScoreDto.getBookCode())
                .totalQuestions(examScoreDto.getTotalQuestions())
                .answerTrue(examScoreDto.getAnswerTrue())
                .totalScore(100)
                .examTime(new Date()).build();

        float f = (float) examScoreDto.getAnswerTrue() / examScoreDto.getTotalQuestions() * examScore.getTotalScore();
        int score = Math.round(f);

        examScore.setScore(score);  // 考试得分
        examScore.setPoint(examScoreDto.getAnswerTrue() == examScoreDto.getTotalQuestions() ? 10 : 0);  // 满分加 10 积分

        int row = examScoreMapper.insert(examScore);
        if (row == 1) {
            LambdaQueryWrapper<EnglishBook> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EnglishBook::getBookCode, examScore.getBookCode());
            EnglishBook book = bookMapper.selectOne(wrapper);
            examScore.setBookName(book == null ? "书籍已被移除！" : book.getBookName());
            return ServiceResult.isSuccess("成绩单提交成功!", examScore);
        }
        return ServiceResult.isInternalError("成绩单提交失败，未知错误！");
    }

    @Override
    public ServiceResult updateExamScore(ExamScoreDto examScoreDto) {
        ExamScore examScore = EntityUtils.entityConvert(examScoreDto, new ExamScore(), true);
        examScore.setId(examScoreDto.getId());

        int row = examScoreMapper.updateById(examScore);
        if (row == 1) {
            return ServiceResult.isSuccess("成绩单修改成功！", null);
        }
        return ServiceResult.isNotModified("未知错误，成绩单修改失败！");
    }

    @Override
    public PageBean queryExamScore(Integer userId, String bookCode, Date examTimeStart, Date examTimeEnd, PageBean pageBean) {
        ExamScoreExample scoreExample = new ExamScoreExample();
        ExamScoreExample.Criteria criteria = scoreExample.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotEmpty(bookCode)) {
            criteria.andBookCodeEqualTo(bookCode);
        }
        if (examTimeStart != null) {
            criteria.andExamTimeGreaterThanOrEqualTo(examTimeStart);
        }
        if (examTimeEnd != null) {
            criteria.andExamTimeLessThanOrEqualTo(examTimeEnd);
        }

        Page pageResult = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize())
                .doSelectPage(() -> examScoreMapper.selectAssociateByExample(scoreExample));

        // 汇总积分
        List<ExamScore> aggregateRow = examScoreMapper.aggregateByExample(scoreExample);
        if (!aggregateRow.isEmpty()) {
            aggregateRow.get(0).setTotalScore(null);
        }

        return QueryServiceUtils.encapsulatePageBean(pageBean, pageResult, aggregateRow);
    }

    @Override
    public ServiceResult deleteExamScore(Integer id) {
        int row = examScoreMapper.deleteById(id);
        if (row == 1) {
            return ServiceResult.isSuccess("已删除成绩单，ID: " + id, null);
        }
        return ServiceResult.isNotModified("删除成绩单失败，ID: " + id);
    }

    @Override
    public ExamScore findExamScoreById(Integer id) {
        return id == null ? null : examScoreMapper.selectById(id);
    }

    @Override
    public ServiceResult getOverviewData(SysUser user) {
        LambdaQueryWrapper<ExamScore> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamScore::getUserId, user.getUserId());
        List<ExamScore> examScores = examScoreMapper.selectList(wrapper);

        Map<String, Object> dataMap = new HashMap<>();
        // 累计积分
        int totalPoint = examScores.stream().mapToInt(ExamScore::getPoint).sum();
        // 通过率
        int passNum = (int) examScores.stream().filter(o -> o.getAnswerTrue() == o.getTotalQuestions()).count();
        double pass = BigDecimal.valueOf(passNum).divide(BigDecimal.valueOf(examScores.size() == 0 ? 1 : examScores.size()), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        // 最后一次考试时间
        Optional<ExamScore> lastedExam = examScores.stream().max(Comparator.comparing(ExamScore::getExamTime));

        dataMap.put("totalPoint", totalPoint);
        dataMap.put("pass", pass);
        dataMap.put("totalExam", examScores.size());
        dataMap.put("lastedExamTime", lastedExam.isPresent() ? lastedExam.get().getExamTime() : null);

        return ServiceResult.isSuccess(dataMap);
    }
}
