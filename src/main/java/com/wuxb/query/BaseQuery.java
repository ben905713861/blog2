/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description 分页查询用
 */

package com.wuxb.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public abstract class BaseQuery {

    private String sort;
    private String order;

    @Min(value = 0, message = "offset不得小于0")
    private Integer offset = 0;

    @Min(value = 0, message = "page不得小于0")
    private Integer page = 1;

    @Min(value = 1, message = "limit不得小于1")
    @Max(value = 100, message = "limit不得大于100")
    private Integer limit = 10;

    public void setOffset(Integer offset) {
        this.offset = offset;
        if (offset != null) {
            page = offset / limit + 1;
        }
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
        if (offset != null) {
            page = offset / limit + 1;
        }
    }

}
