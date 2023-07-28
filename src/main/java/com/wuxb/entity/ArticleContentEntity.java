package com.wuxb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_content")
public class ArticleContentEntity {
    @TableId
    private Integer articleId;

    private String content;
}
