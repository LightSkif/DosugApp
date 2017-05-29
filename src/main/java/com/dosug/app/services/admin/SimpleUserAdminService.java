package com.dosug.app.services.admin;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import com.dosug.app.exception.UnknownServerException;
import com.dosug.app.form.admin.AdminUpdateUserForm;
import com.dosug.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SimpleUserAdminService implements UserAdminService{

    private Logger logger = LoggerFactory.getLogger(SimpleUserAdminService.class);

    private UserRepository userRepository;

    @Override
    public void updateUser(AdminUpdateUserForm form, long userId) {

        User user = userRepository.findOne(userId);
        //set new data
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setDescription(form.getDescription());
        user.setPhone(form.getPhone());
        user.setBirthDate(form.getBirthDate());

        // если не null значит было измененно
        if(form.getPassword() != null) {
            user.setPassword(form.getPassword());
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            //TODO: имплементирую позже
            logger.error(e.getMessage());
            throw new UnknownServerException();
        }
    }

    @Override
    public void banUser(long userId) {
        User user = userRepository.findOne(userId);
        if(user != null) {
            user.setBanned(true);
        }
        userRepository.save(user);
    }

    public void unbanUser(long userId) {
        User user = userRepository.findOne(userId);
        if(user != null) {
            user.setBanned(false);
        }

        userRepository.save(user);
    }

    public User getUser(long userId) {
        User user = userRepository.findOne(userId);
        if(user != null) {
            return user;
        }
        return null;
    }

    @Override
    public List<Event> getEventsForUser(long userId) {
        User user = userRepository.findOne(userId);

        if(user != null) {
            return user.getEventLinks().parallelStream()
                    .map(EventParticipant::getEvent)
                        .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
