package com.dosug.app.repository;


import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.domain.UserTag;
import org.springframework.data.repository.CrudRepository;

public interface UserTagRepository extends CrudRepository<UserTag, Long> {

    UserTag findByUserAndTag(User user, Tag tag);
}
