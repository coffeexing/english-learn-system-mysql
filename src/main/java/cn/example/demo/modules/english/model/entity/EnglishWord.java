package cn.example.demo.modules.english.model.entity;

import cn.example.demo.common.tools.obj.reflect.FieldAlias;
import cn.example.demo.common.tools.obj.reflect.FieldIgnore;
import cn.example.demo.common.tools.obj.reflect.Immutable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 英语单词表
 */
@ApiModel(value = "英语词库")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnglishWord {
    @FieldIgnore
    @Immutable
    @TableId(type = IdType.AUTO)
    private Integer id;    // 主键

    @FieldIgnore
    private String bookCode;    // 书籍编码

    @FieldIgnore
    private Short wordType; // 词语类型{1: 单词, 2: 短语}

    @FieldAlias("英文")
    private String word;    // 单词

    @FieldAlias("中文名")
    private String chinese;    // 汉译

    @FieldIgnore
    private String pronounceFile;   // 发音文件

    @FieldAlias("解释")
    private String description; // 描述

    @FieldIgnore
    private Short status;   // 状态

    @TableField(exist = false)
    private String bookName;

    @TableField(exist = false)
    private String matchWord;
}
