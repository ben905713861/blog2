package com.wuxb.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleQuery extends BaseQuery {
    private String title;
    private Integer typeId;
    private LocalDate startTime;
    private LocalDate endTime;
}
