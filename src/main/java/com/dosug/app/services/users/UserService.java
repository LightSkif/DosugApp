package com.dosug.app.services.users;


import com.dosug.app.domain.User;

import java.util.List;

public interface UserService {

    Long updateUser(User user);

    User getUser(long Id);

    List<User> getUsersWithPartOfName(String partUserName, int count);

    void deleteUser(long userId);
}
