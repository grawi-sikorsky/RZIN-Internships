package com.pgs.praktyki.rzeintern1021.repo;

import com.pgs.praktyki.rzeintern1021.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByUuid(String uuid);

    User findUserByActivationLink(String activationLink);

    @Transactional
    void deleteByUuid(String uuid);

    Page findAll(Pageable pageable);

    @Query("select u from User u where " +
        "lower(u.username) like lower(?1) or " +
        "lower(u.email) like lower(?1) or " +
        "lower(u.firstname) like lower(?1) or " +
        "lower(u.lastname) like lower(?1)")
    Page<User> findUserBySearchWord(String searchWord, Pageable pageable);
}
