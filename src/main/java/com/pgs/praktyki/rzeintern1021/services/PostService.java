package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.dto.PostDTO;
import com.pgs.praktyki.rzeintern1021.exceptions.PostDoesntExistException;
import com.pgs.praktyki.rzeintern1021.exceptions.UserDoesntExistException;
import com.pgs.praktyki.rzeintern1021.models.Post;
import com.pgs.praktyki.rzeintern1021.models.User;
import com.pgs.praktyki.rzeintern1021.repo.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public PostService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public Post addPost(final String uuid, PostDTO postDTO) throws UserDoesntExistException {
        if (userService.getUserEntityByUUID(uuid) != null) {
            User user = userService.getUserEntityByUUID(uuid);
            postDTO.setUsername(user.getUsername());
            user.getPostEntityList().add(postDTO.toEntity());
            userService.saveUser(user);

            return postRepository.findPostEntityById(user.getPostEntityList().get(user.getPostEntityList().size() - 1).getId());
        }
        throw new UserDoesntExistException("User not exists");
    }

    public Post editPost(final String uuid, PostDTO postDTO) throws PostDoesntExistException, UserDoesntExistException {
        if (userService.getUserEntityByUUID(uuid) != null) {
            Post post = postRepository.findByIdAndUserId(postDTO.getId(), userService.getUserEntityByUUID(uuid).getId());
            if (post != null) {
                post.setContent(postDTO.getContent());
                post.setTitle(postDTO.getTitle());
                postRepository.save(post);
                return post;
            }
            throw new PostDoesntExistException("No such post existing");
        }
        throw new UserDoesntExistException("No such user existing, or user don't have access to this post");
    }

    public Page<PostDTO> listAllUserPostsPageable(final String uuid, Pageable pageable) {
        return new PageImpl<PostDTO>(postRepository.findAllByUserId(userService.getUserEntityByUUID(uuid).getId(), pageable)
            .stream()
            .map(PostDTO::new)
            .collect(Collectors.toList()));
    }

    public Post getPost(final String id) throws PostDoesntExistException {
        if (postRepository.findPostEntityById(Long.valueOf(id)) != null) {
            return postRepository.findPostEntityById(Long.valueOf(id));
        }
        throw new PostDoesntExistException("Post not found");
    }

    public void removePost(final String uuid, final String id) throws PostDoesntExistException, UserDoesntExistException {
        if (userService.getUserEntityByUUID(uuid) != null) {
            if (postRepository.findByIdAndUserId(Long.valueOf(id), userService.getUserEntityByUUID(uuid).getId()) != null) {
                postRepository.deleteById(Long.valueOf(id));
            } else throw new PostDoesntExistException("Post not removed - bad id, or no access to this post");
        } else throw new UserDoesntExistException("User doesn't exist, can't remove post");

    }

    public Page<PostDTO> searchPosts(final String uuid, final String searchWord, Pageable pageable) {
        return new PageImpl<PostDTO>(postRepository.findByTitleLikeOrContentLikeOrUsernameLike(userService.getUserEntityByUUID(uuid).getId(), searchWord, pageable)
            .stream()
            .map(PostDTO::new)
            .collect(Collectors.toList()));
    }
}
