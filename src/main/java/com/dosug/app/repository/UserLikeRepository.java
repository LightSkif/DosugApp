package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.domain.UserLike;
import com.dosug.app.domain.UserTag;
import org.springframework.data.repository.CrudRepository;

/**
 * Cadres are all-important
 */
public interface UserLikeRepository extends CrudRepository<UserLike, Long> {

    UserLike findByEventAndEvaluateUserAndRatedUserTag(Event event, User evaluateUser, UserTag ratedUserTag);
}
