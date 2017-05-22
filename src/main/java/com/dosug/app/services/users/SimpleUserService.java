package com.dosug.app.services.users;

import com.dosug.app.domain.User;
import com.dosug.app.exception.UserNotFoundException;
import com.dosug.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SimpleUserService implements UserService {

    private UserRepository userRepository;

    public Long updateUser(User user) {
        return null;
    }

    public User getUser(long Id) {

        User user = userRepository.findById(Id);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<User> getUsersWithPartOfName(String partUserName, int count) {

        PageRequest pageable = new PageRequest(0, count);
        return userRepository.findByUsernameContainingIgnoreCaseOrderByCreateDateDesc(partUserName, pageable).getContent();
    }

    public void deleteUser(long userId) {


    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
