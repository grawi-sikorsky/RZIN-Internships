package com.pgs.praktyki.rzeintern1021.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pgs.praktyki.rzeintern1021.aws.configuration.AwsTestConfiguration;
import com.pgs.praktyki.rzeintern1021.dto.UserDTO;
import com.pgs.praktyki.rzeintern1021.dto.UserRegisterDTO;
import com.pgs.praktyki.rzeintern1021.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
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
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    private String editUserReqestBody() throws JsonProcessingException {
        UserDTO user = new UserDTO(new User(666L, "uuid7", "userTest", "userTest", "grawires@gmail.com", "userTest", "userTest", 79));
        user.setAvatarLink("http://costam.com/default-avatar.jpg");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(user);
    }

    private String addUserReqestBody() throws JsonProcessingException {
        UserRegisterDTO user = new UserRegisterDTO(new User(666L, "uuid7", "userTest", "userTest", "grawires@gmail.com", "userTest", "userTest", 79));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(user);
    }

    private String userActivationCode() {
        return "activationlink";
    }

    @Test
    public void getUser_should_return_user_if_exists() throws Exception {
        this.mockMvc.perform(get("/user/uuid1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listUsers_should_return_all_users() throws Exception {
        this.mockMvc.perform(get("/user/list_all"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listUsersPage_should_return_all_users() throws Exception {
        this.mockMvc.perform(get("/user/list_page"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchUsers_should_return_ok_status() throws Exception {
        this.mockMvc.perform(get("/user/search/testuser3"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addUser_should_return_ok_status() throws Exception {
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addUserReqestBody()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void activateUser_should_return_ok_status() throws Exception {
        this.mockMvc.perform(patch("/user/uuid7/activate/" + userActivationCode()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void editUserByUUID_should_return_OK1() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("avatar", "avatar.jpg", "image/jpeg", "<<jpg data>>".getBytes());
        MockMultipartFile userDTO = new MockMultipartFile("user", "user", "application/json", editUserReqestBody().getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/user/uuid2/");

        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        this.mockMvc.perform(builder
                .file(imageFile)
                .file(userDTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeUserbyUUID_should_remove_user_and_return_ok() throws Exception {
        this.mockMvc.perform(delete("/user/uuid1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeUser_should_return_no_content_status() throws Exception {
        this.mockMvc.perform(delete("/user/uuid5"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

