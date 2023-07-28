package com.wuxb.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {
    private Integer ArticleId;
    private String title;
    private Integer typeId;
    private Boolean isRecommend;
    private String keyWords;
    private String description;
    private String thumbPath;
    private String thumbUrl;
    private String content;
    private LocalDateTime createTime;
}
