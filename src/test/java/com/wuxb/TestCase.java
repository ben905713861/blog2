package com.wuxb;

import com.wuxb.entity.ArticleEntity;
import com.wuxb.mapper.ArticleMapper;
import com.wuxb.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BlogApplication.class)
public class TestCase {
//    @LocalServerPort
//    private Integer port;

    @Autowired
    private ArticleMapper articleMapper;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void test2(int id) {
        ArticleEntity articleEntity = articleMapper.selectById(id);
        System.out.println(articleEntity);
    }

    @ParameterizedTest
    @MethodSource("providePersonObjects")
    public void test3(Book book) {
        System.out.println(book);
    }

    // 静态方法用于提供参数
    static List<Book> providePersonObjects() {
        return List.of(
            new Book("100", "t1"),
            new Book("200", "t2"),
            new Book("300", "t3")
        );
    }

    @Test
    @DisplayName("test")
    public void test() {
        ArticleService articleService = mock(ArticleService.class);
        when(articleService.getOne(3)).thenReturn(ArticleEntity.builder()
            .articleId(3)
            .title("a3")
            .build());

        ArticleEntity user = articleService.getOne(3);
        System.out.println(user);
        assert true;
    }
}

@Data
@AllArgsConstructor
class Book {
    private String id;
    private String title;
}
