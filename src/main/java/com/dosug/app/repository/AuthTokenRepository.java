package com.dosug.app.repository;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by radmir on 27.03.17.
 */
public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {

    AuthToken findByUser(User user);
}
