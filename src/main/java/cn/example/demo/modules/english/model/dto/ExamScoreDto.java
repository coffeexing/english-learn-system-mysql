package cn.example.demo.modules.english.model.dto;

import cn.example.demo.common.tools.obj.reflect.FieldAlias;
import cn.example.demo.common.validation.groups.Update;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 成绩单
 */
@ApiModel(value = "成绩单")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamScoreDto {
    @FieldAlias("主键")
    @NotNull(message = "成绩单主键不能为空！", groups = Update.class)
    private Integer id;    // 主键

    @FieldAlias("书籍编号")
    @NotEmpty(message = "书籍编号不能为空！")
    private String bookCode;    // 书籍编号

    @FieldAlias("答对量")
    @NotNull(message = "答对量不能为空！")
    private Integer answerTrue;  // 答对量

    @FieldAlias("总题量")
    @NotNull(message = "总题量不能为空！")
    private Integer totalQuestions;  // 总题量
}
