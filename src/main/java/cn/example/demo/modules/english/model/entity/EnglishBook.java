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

/**
 * 英语词库书籍表
 */
@ApiModel(value = "英语书籍")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnglishBook {
    @Immutable
    @TableId(type = IdType.AUTO)
    private Integer id;    // 主键

    private String bookCode;    // 书籍编码

    private String bookName;    // 书籍名称

    private String filePath;   // 文件路径

    private String description; // 描述

    private Short status;   // 状态

    private Integer basePracticeNum;    // 基础练习题量
    private Integer translatePracticeNum;    // 英译汉题量
    private Integer examNum;    // 考试题量

    @TableField(exist = false)
    private Integer totalWords; // 题量
}
