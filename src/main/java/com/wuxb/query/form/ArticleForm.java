package com.wuxb.query.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {
    @Null(groups = {ValidationGroup.Insert.class})
    @NotNull(groups = {ValidationGroup.Update.class})
    private Integer ArticleId;

    @NotEmpty
    private String title;

    @NotNull
    private Integer typeId;

    @NotNull
    private Boolean isRecommend;

    @NotEmpty
    @Size(max = 100)
    private String keyWords;

    @Size(max = 255)
    private String description;

    @Size(max = 100)
    private String thumbPath;

    @Size(max = 65535)
    @NotEmpty
    private String content;
}
