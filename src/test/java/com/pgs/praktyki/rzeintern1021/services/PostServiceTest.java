package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.dto.PostDTO;
import com.pgs.praktyki.rzeintern1021.exceptions.PostDoesntExistException;
import com.pgs.praktyki.rzeintern1021.exceptions.UserDoesntExistException;
import com.pgs.praktyki.rzeintern1021.models.Post;
import com.pgs.praktyki.rzeintern1021.models.User;
import com.pgs.praktyki.rzeintern1021.repo.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private User USER_ENTITY;

    @Mock
    private Post POST_ENTITY;

    @Mock
    private PostDTO POST_DTO;

    @InjectMocks
    private PostService postService;


    @Test
    void addPost_should_add_post() throws UserDoesntExistException {
        User user = new User(1L, "uuid", "testuser1", "testpass1", "email", "fn", "ln", 55);
        Post post = new Post(0L, "title", "content", "testuser1");
        user.setPostEntityList(new ArrayList<Post>(Arrays.asList(post)));

        when(userService.getUserEntityByUUID(user.getUuid())).thenReturn(user);
        when(postRepository.findPostEntityById(any())).thenReturn(POST_ENTITY);

        postService.addPost(user.getUuid(), new PostDTO(post));

        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void addPost_should_throw_user_doesnt_exist() throws UserDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(null);

        assertThrows(UserDoesntExistException.class, () -> {
            postService.addPost(USER_ENTITY.getUuid(), POST_DTO);
        });
    }

    @Test
    void editPost_should_edit_existing_post() throws UserDoesntExistException, PostDoesntExistException {
        User user = new User(1L, "uuid", "testuser1", "testpass1", "email", "fn", "ln", 55);
        PostDTO post = new PostDTO();
        post.setId(1L);
        post.setTitle("title");

        when(userService.getUserEntityByUUID(user.getUuid())).thenReturn(user);
        when(postRepository.findByIdAndUserId(post.getId(), user.getId())).thenReturn(post.toEntity());

        postService.editPost(user.getUuid(), post);

        verify(postRepository, times(1)).save(any());
    }

    @Test
    void editPost_should_throw_exception_when_user_doesnt_exist() throws UserDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(null);

        assertThrows(UserDoesntExistException.class, () -> {
            postService.editPost(null, POST_DTO);
        });
    }

    @Test
    void editPost_should_throw_exception_when_post_doesnt_exist() throws PostDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(USER_ENTITY);
        when(postRepository.findByIdAndUserId(POST_DTO.getId(), USER_ENTITY.getId())).thenReturn(null);

        assertThrows(PostDoesntExistException.class, () -> {
            postService.editPost(USER_ENTITY.getUuid(), POST_DTO);
        });
    }

    @Test
    void getPost_should_return_post_and_OK_status() throws PostDoesntExistException {
        when(postRepository.findPostEntityById(POST_ENTITY.getId())).thenReturn(POST_ENTITY);

        Post responseEntity = postService.getPost(POST_ENTITY.getId().toString());

        assertEquals(responseEntity, POST_ENTITY);
    }

    @Test
    void getPost_should_throw_exception() throws PostDoesntExistException {
        when(postRepository.findPostEntityById(POST_DTO.getId())).thenReturn(null);

        assertThrows(PostDoesntExistException.class, () -> {
            postService.getPost(POST_DTO.getId().toString());
        });
    }

    @Test
    void removePost_should_remove_user_and_return_status() throws PostDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(USER_ENTITY);
        when(postRepository.findByIdAndUserId(POST_ENTITY.getId(), USER_ENTITY.getId())).thenReturn(POST_ENTITY);

        postService.removePost(USER_ENTITY.getUuid(), POST_ENTITY.getId().toString());

        verify(postRepository, times(1)).deleteById(any());
    }

    @Test
    void removePost_should_throw_no_user_exception() throws UserDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(null);

        assertThrows(UserDoesntExistException.class, () -> {
            postService.removePost(USER_ENTITY.getUuid(), POST_DTO.getId().toString());
        });
    }

    @Test
    void removePost_should_throw_no_post_exception() throws PostDoesntExistException {
        when(userService.getUserEntityByUUID(USER_ENTITY.getUuid())).thenReturn(USER_ENTITY);
        when(postRepository.findByIdAndUserId(POST_DTO.getId(), USER_ENTITY.getId())).thenReturn(null);

        assertThrows(PostDoesntExistException.class, () -> {
            postService.removePost(USER_ENTITY.getUuid(), POST_DTO.getId().toString());
        });
    }
}