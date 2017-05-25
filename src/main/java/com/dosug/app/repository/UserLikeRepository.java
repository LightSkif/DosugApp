package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.domain.UserLike;
import org.springframework.data.repository.CrudRepository;

/**
 * Cadres are all-important
 */
public interface UserLikeRepository extends CrudRepository<UserLike, Long> {

    UserLike findByEventAndEvaluateUserAndRatedUserAndTag(Event event, User evaluateUser, User ratedUser, Tag tag);
}
