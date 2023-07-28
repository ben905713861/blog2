package com.wuxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_type")
public class ArticleTypeEntity {
    @TableId(type = IdType.AUTO)
    private Integer typeId;

    private String type;

    private Integer sort;
}
