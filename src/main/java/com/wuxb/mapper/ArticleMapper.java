package com.wuxb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxb.entity.ArticleEntity;
import com.wuxb.entity.ArticleTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    @Select("select * from article_type")
    List<ArticleTypeEntity> getArticleTypes();
}
