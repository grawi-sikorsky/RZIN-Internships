package com.pgs.praktyki.rzeintern1021.controllers;

import com.pgs.praktyki.rzeintern1021.dto.PostDTO;
import com.pgs.praktyki.rzeintern1021.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{uuid}/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@PathVariable String uuid, @RequestBody PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostDTO(postService.addPost(uuid, postDTO)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable String uuid, @PathVariable("id") String id) { // czy tu trzeba uuid?
        return ResponseEntity.status(HttpStatus.OK).body(new PostDTO(postService.getPost(id)));
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> listAllUserPostsPageable(@PathVariable String uuid, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.listAllUserPostsPageable(uuid, pageable));
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<Page<PostDTO>> searchPosts(@PathVariable("uuid") String uuid, @PathVariable("search") String search, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.searchPosts(uuid, search, pageable));
    }

    @PatchMapping
    public ResponseEntity<PostDTO> editPost(@PathVariable String uuid, @RequestBody PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new PostDTO(postService.editPost(uuid, postDTO)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String uuid, @PathVariable("id") String id) {
        postService.removePost(uuid, id);
        return ResponseEntity.noContent().build();
    }
}
