package com.pgs.praktyki.rzeintern1021.repo;

import com.pgs.praktyki.rzeintern1021.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    @Query("SELECT post FROM Post post WHERE post.id = ?1")
    Post findPostEntityById(Long id);

    @Query("select p from Post p where p.id = ?1 and p.userId = ?2")
    Post findByIdAndUserId(Long id, Long userId);

    @Query("SELECT post FROM Post post WHERE post.userId = ?1")
    Page<Post> findAllByUserId(Long id, Pageable pageable);

    @Query("select p from Post p where " +
        "p.userId = ?1 and " +
        "(lower(p.title) like lower(?2) or " +
        "lower(p.content) like lower(?2) or " +
        "lower(p.username) like lower(?2) ) ")
    Page<Post> findByTitleLikeOrContentLikeOrUsernameLike(Long uuid, String searchWord, Pageable pageable);
}