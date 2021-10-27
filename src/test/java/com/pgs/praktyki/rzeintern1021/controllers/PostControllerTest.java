package com.pgs.praktyki.rzeintern1021.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pgs.praktyki.rzeintern1021.aws.configuration.AwsTestConfiguration;
import com.pgs.praktyki.rzeintern1021.dto.PostDTO;
import com.pgs.praktyki.rzeintern1021.models.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = AwsTestConfiguration.class)
@Sql(value = "/preparedb.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/cleanupdb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
class PostControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    public String makePostEntity() throws JsonProcessingException {
        PostDTO post = new PostDTO(new Post(2L, "test title", "test content", "testuser1"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonEntity = ow.writeValueAsString(post);
        return jsonEntity;
    }

    @Test
    public void addPost_should_return_created_status_when_post_created() throws Exception {
        this.mockMvc.perform(post("/user/uuid1/post/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makePostEntity()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getPost_should_return_ok_status() throws Exception {
        this.mockMvc.perform(get("/user/uuid1/post/2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listAllPostPageable_should_return_ok_status() throws Exception {
        this.mockMvc.perform(get("/user/uuid1/post/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchPosts_should_return_ok_status() throws Exception {
        this.mockMvc.perform(get("/user/uuid1/post/search/testuser1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void editPost_should_return_ok_status_when_succesfuly_modified() throws Exception {
        this.mockMvc.perform(patch("/user/uuid1/post/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(makePostEntity()));
    }

    @Test
    public void deletePost_should_return_no_content_status() throws Exception {
        this.mockMvc.perform(delete("/user/uuid1/post/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andDo(MockMvcResultHandlers.print());
    }
}