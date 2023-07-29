package com.wuxb.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章出参
 *
 * @author liwenyan
 * @since 2023/7/29
 */
@Data
public class ArticleDTO {
    private Integer articleId;
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
