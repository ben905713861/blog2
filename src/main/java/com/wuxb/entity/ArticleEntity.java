package com.wuxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class ArticleEntity {
    @TableId(type = IdType.AUTO)
    private Integer articleId;
    private String title;
    private Integer typeId;
    private Boolean isRecommend;
    private String keyWords;
    private String description;
    private String thumbPath;
    private Integer likeNum;
    private LocalDateTime createTime;
}
