package com.dosug.app.repository;

import com.dosug.app.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by radmir on 23.03.17.
 */

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    Iterable<User> findAll();
}
