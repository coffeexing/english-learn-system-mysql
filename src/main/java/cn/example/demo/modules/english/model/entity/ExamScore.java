package cn.example.demo.modules.english.model.entity;

import cn.example.demo.common.tools.obj.reflect.Immutable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 成绩单
 */
@ApiModel(value = "成绩单")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamScore {
    @Immutable
    @TableId(type = IdType.AUTO)
    private Integer id;    // 主键

    private Integer userId; // 用户ID

    private String bookCode;    // 书籍编号

    private Integer totalQuestions;  // 总题量

    private Integer answerTrue;  // 答对量

    private Integer score;  //  考试分数

    private Integer point;  // 积分

    @Immutable
    private Date examTime;    // 考试时间

    @TableField(exist = false)
    private Integer totalScore = 100;   // 总分

    @TableField(exist = false)
    private String bookName;   // 词汇书名称

    @TableField(exist = false)
    private String userRealName;   // 用户昵称
}
