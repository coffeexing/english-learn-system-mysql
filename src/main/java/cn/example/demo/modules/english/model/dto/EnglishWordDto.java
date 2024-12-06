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
 * 英语单词表
 */
@ApiModel(value = "英语词库")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnglishWordDto {
    @FieldAlias(value = "主键")
    @NotNull(message = "主键不能为空！", groups = Update.class)
    private Integer id;    // 主键

    @FieldAlias(value = "书籍编码")
    @NotEmpty(message = "书籍编码不能为空！")
    private String bookCode;    // 书籍编码

    @FieldAlias(value = "词语类型")
    private Short wordType; // 词语类型{1: 单词, 2: 短语}

    @FieldAlias(value = "单词")
    @NotEmpty(message = "单词不能为空！")
    private String word;    // 单词

    @FieldAlias(value = "汉译")
    @NotEmpty(message = "汉译不能为空！")
    private String chinese;    // 汉译

    @FieldAlias(value = "描述")
    private String description; // 描述

    @FieldAlias(value = "状态")
    private Short status;   // 状态
}
