/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description
 */

package com.wuxb.dto;

import lombok.Data;

import java.util.List;

/**
 * 通用带分页信息的包装返回
 *
 * @param <E> 内容的返回类型
 * @author liwenyan
 * @since 2023/7/29
 */
@Data
public class PageListDTO<E> {

    private List<E> rows;
    private Long total;

    public PageListDTO() {
        this.rows = List.of();
        this.total = 0L;
    }

    public PageListDTO(List<E> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

}
