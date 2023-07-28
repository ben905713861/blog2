package com.wuxb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuxb.dto.ArticleDTO;
import com.wuxb.query.form.ArticleForm;
import com.wuxb.vo.ReturnVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(classes = BlogApplication.class, properties = {"spring.profiles.active=junit"})
public class IntergationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testGetOne() throws Exception {
        String respBodyString = mockMvc.perform(MockMvcRequestBuilders.get("/article/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn().getResponse().getContentAsString();
        ReturnVO<ArticleDTO> articleDTO = objectMapper.readValue(respBodyString,
            new TypeReference<>() {
            });
        System.out.println(articleDTO);
    }

    @Test
    @Transactional
    public void testAdd() throws Exception {
        ArticleForm articleForm = ArticleForm.builder()
            .title("my test title")
            .typeId(1)
            .isRecommend(true)
            .keyWords("test keyWords")
            .content("test content")
            .build();
        String reqBody = objectMapper.writeValueAsString(articleForm);
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/article")
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqBody);
        String respBodyString = mockMvc.perform(post)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();
        ReturnVO<Integer> returnVO = objectMapper.readValue(respBodyString,
            new TypeReference<>() {
            });
        Assertions.assertEquals(returnVO.getStatus(), 0);
    }
}
