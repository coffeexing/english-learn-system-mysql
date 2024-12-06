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
 * 英语词库书籍表
 */
@ApiModel(value = "英语书籍")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnglishBookDto {
    @FieldAlias(value = "主键")
    @NotNull(groups = Update.class)
    private Integer id;    // 主键

    @FieldAlias(value = "书籍编码")
    @NotEmpty
    private String bookCode;    // 书籍编码

    @FieldAlias(value = "书籍名称")
    @NotEmpty
    private String bookName;    // 书籍名称

    @FieldAlias(value = "文件路径")
    private String filePath;   // 文件路径

    @FieldAlias(value = "描述")
    private String description; // 描述

    @FieldAlias(value = "状态")
    private Short status;   // 状态

    @FieldAlias(value = "基础练习题量")
    private Integer basePracticeNum;    // 基础练习题量

    @FieldAlias(value = "英译汉题量")
    private Integer translatePracticeNum;    // 英译汉题量

    @FieldAlias(value = "考试题量")
    private Integer examNum;    // 考试题量
}
