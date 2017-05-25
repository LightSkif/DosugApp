package com.dosug.app.services.users;


import com.dosug.app.domain.User;
import com.dosug.app.domain.UserLike;

import java.util.List;

public interface UserService {

    Long updateUser(User user, User requestedUser);

    Long updateUserPassword(User user, User requestedUser);

    User getUser(long Id);

    List<User> getUsersWithPartOfName(String partUserName, int count);

    void addLike(long ratedUserId, long eventId, long tagId, User evaluateUser);

    void removeLike(long ratedUserId, long eventId, long tagId, User requestedUser);

    void deleteUser(long userId, User requestedUser);

    UserLike userLikeBuilder(long ratedUserId, long eventId, long tagId, User evaluateUser);
}
