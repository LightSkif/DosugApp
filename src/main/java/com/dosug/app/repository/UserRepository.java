package com.dosug.app.repository;

import com.dosug.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by radmir on 23.03.17.
 */

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> simpleSearch(@Param("part") String part, Pageable pageable);

    long simpleSearchCount(@Param("part") String part);

    User findById(Long id);

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    Page<User> findByUsernameContainingIgnoreCaseOrderByCreateDateDesc(String username,
                                                                       Pageable pageable);

//    @Query(value = "select u from User u inner join EventParticipant ep where (u.username > :username) and (ep.event_id = :event) order by u.username asc", nativeQuery = true)
//    Page<User> findParticipantsUsernameContaining(@Param("event") Event event, @Param("username") String username, Pageable pageable);
//
//    @Query("select u from User u inner join EventParticipant ep where (ep.event.id = :event) order by u.username asc")
//    Page<User> findParticipants(@Param("event") long event, Pageable pageable);

    Iterable<User> findAll();
}
