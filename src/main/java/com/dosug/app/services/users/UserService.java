package com.dosug.app.services.users;


import com.dosug.app.domain.User;

import java.util.List;

public interface UserService {

    Long updateUser(User user, User requestedUser);

    Long updateUserPassword(String oldPassword, String newPassword, User requestedUser);

    void addLike(long ratedUserId, long eventId, long tagId, User evaluateUser);

//    void removeLike(long ratedUserId, long eventId, long tagId, User requestedUser);

    User getUser(long Id);

    List<User> getUsersWithPartOfName(String partUserName, int count);

    List<User> getParticpantsWithPartName(long eventId, int count, String usernamePart, User requestedUser);

    void deleteUser(long userId, User requestedUser);
}
