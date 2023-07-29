package com.wuxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxb.dto.ArticleDTO;
import com.wuxb.dto.PageListDTO;
import com.wuxb.entity.ArticleContentEntity;
import com.wuxb.entity.ArticleEntity;
import com.wuxb.entity.ArticleTypeEntity;
import com.wuxb.exception.RestException;
import com.wuxb.mapper.ArticleContentMapper;
import com.wuxb.mapper.ArticleMapper;
import com.wuxb.query.ArticleQuery;
import com.wuxb.query.form.ArticleForm;
import com.wuxb.query.form.ValidationGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;

    /**
     * frontend init. return the article type
     *
     * @return article type
     */
    @GetMapping("/articleType")
    public List<ArticleTypeEntity> articleType() {
        return articleMapper.getArticleTypes();
    }

    @GetMapping
    public PageListDTO<ArticleDTO> list(@Validated ArticleQuery query) {
        LambdaQueryWrapper<ArticleEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getTitle() != null && !query.getTitle().isBlank()) {
            queryWrapper.like(ArticleEntity::getTitle, query.getTitle());
        }
        if (query.getTypeId() != null) {
            queryWrapper.eq(ArticleEntity::getTypeId, query.getTypeId());
        }
        if (query.getStartTime() != null) {
            queryWrapper.ge(ArticleEntity::getCreateTime, query.getStartTime());
        }
        if (query.getEndTime() != null) {
            queryWrapper.lt(ArticleEntity::getCreateTime, query.getEndTime().plusDays(1));
        }
        Page<ArticleEntity> page = new Page<>(query.getPage(), query.getLimit());
        Page<ArticleEntity> articleEntityPage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleDTO> articleDTOList = articleEntityPage.getRecords().stream().map(it -> {
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(it, articleDTO);
            return articleDTO;
        }).collect(Collectors.toList());
        return new PageListDTO<>(articleDTOList, articleEntityPage.getTotal());
    }

    @GetMapping("/{articleId}")
    public ArticleDTO getOne(@PathVariable @NotNull Integer articleId) {
        ArticleEntity articleEntity = articleMapper.selectById(articleId);
        if (articleEntity == null) {
            throw new RestException("article is not exists: " + articleId);
        }
        ArticleContentEntity articleContentEntity = articleContentMapper.selectById(articleId);
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(articleEntity, articleDTO);
        articleDTO.setContent(articleContentEntity.getContent());
        return articleDTO;
    }

    @PostMapping
    @Transactional
    public Integer insert(@Validated(ValidationGroup.Insert.class) @RequestBody ArticleForm articleForm) {
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(articleForm, articleEntity);
        articleEntity.setCreateTime(LocalDateTime.now());
        articleMapper.insert(articleEntity);
        ArticleContentEntity articleContentEntity = new ArticleContentEntity();
        articleContentEntity.setArticleId(articleEntity.getArticleId());
        articleContentEntity.setContent(articleForm.getContent());
        articleContentMapper.insert(articleContentEntity);
        return articleEntity.getArticleId();
    }

    @PutMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer update(@Validated(ValidationGroup.Update.class) @RequestBody ArticleForm articleForm) {
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(articleForm, articleEntity);
        articleMapper.updateById(articleEntity);
        ArticleContentEntity articleContentEntity = new ArticleContentEntity();
        BeanUtils.copyProperties(articleForm, articleContentEntity);
        articleContentMapper.updateById(articleContentEntity);


        return articleEntity.getArticleId();
    }

    @DeleteMapping("/{articleId}")
    @Transactional
    public Integer delete(@PathVariable @NotNull Integer articleId) {
        articleContentMapper.deleteById(articleId);
        return articleMapper.deleteById(articleId);
    }
}
