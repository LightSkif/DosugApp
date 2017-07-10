package com.dosug.app.services.users;

import com.dosug.app.domain.*;
import com.dosug.app.exception.*;
import com.dosug.app.repository.*;
import com.dosug.app.services.tags.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class SimpleUserService implements UserService {

    private UserRepository userRepository;

    private UserLikeRepository userLikeRepository;

    private UserTagRepository userTagRepository;

    private EventRepository eventRepository;

    private EventParticipantRepository eventParticipantRepository;

    private TagRepository tagRepository;

    private TagService tagService;

    private AuthTokenRepository authTokenRepository;

    @Override
    public Long updateUser(User user, User requestedUser) {

        if(user.getId() != requestedUser.getId()) {
            throw new InsufficientlyRightsException();
        }

        try {
            userRepository.save(user);
        } catch (Exception e ){
            log.error(e.getMessage());
            throw new UnknownServerException();
        }

        return user.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long updateUserPassword(String oldPassword, String newPassword, User requestedUser) {
        if(!requestedUser.getPassword().equals(oldPassword)) {
            throw new InsufficientlyRightsException();
        }

        try {

            requestedUser.setPassword(newPassword);
            requestedUser.setAuthToken(new HashSet<>());
            userRepository.save(requestedUser);

            authTokenRepository.deleteAllByUser(requestedUser);
        }  catch (Exception e ){
            log.error(e.getMessage());
            throw new UnknownServerException();
        }
        return requestedUser.getId();
    }

    @Override
    public void addLike(long ratedUserId, long eventId, long tagId, User evaluateUser) {

//        UserLike newUserLike = userLikeBuilder(ratedUserId, eventId, tagId, evaluateUser);

        if(ratedUserId == evaluateUser.getId()) {
            throw new ConflictException();
        }

        Event event = Optional.ofNullable(eventRepository.findOne(eventId))
                                .orElseThrow(EventNotFoundException::new);

        // Проверяем закончилось ли событие.
        if (LocalDateTime.now().isBefore(event.getEndDateTime())) {
            throw new InsufficientlyRightsException();
        }

        User ratedUser = Optional.ofNullable(userRepository.findOne(ratedUserId))
                                .orElseThrow(UserNotFoundException::new);

        Tag tag = Optional.ofNullable(tagRepository.findOne(tagId))
                                .orElseThrow(TagNotFoundException::new);

        UserTag ratedUserTag = Optional.ofNullable(userTagRepository.findByUserAndTag(ratedUser, tag))
                                            .orElseThrow(UserTagNotFoundException::new);

        checkUserInEvent(ratedUser, event);
        checkUserInEvent(evaluateUser, event);

        checkTagInEvent(tag, event);

        checkTagInUser(tag, ratedUser);


        UserLike userLike = new UserLike();
        userLike.setEvaluateUser(evaluateUser);
        userLike.setEvent(event);
        userLike.setRatedUserTag(ratedUserTag);

        try {
            userLikeRepository.save(userLike);
        } catch (Exception e) {

            log.error(e.getMessage());

            if(userLikeRepository.findByEventAndEvaluateUserAndRatedUserTag(event, evaluateUser, ratedUserTag) == null) {
                throw new UnknownServerException();
            }
        }

    }




    /**
     * @param searchedTag тег который ищем у пользователя
     * @param user сбсн пользователь
     */
    private void checkTagInUser(Tag searchedTag, User user) {

        if(user.getTagLinks().stream()
                .noneMatch(tagLink -> tagLink.getTag().equals(searchedTag))) {

            throw new LinkNotFoundException();
        }
    }

    /**
     * @param searchedTag тег который ищем в событии
     * @param event сбсн Событие
     */
    private void checkTagInEvent(Tag searchedTag, Event event) {

        if(event.getTags().stream()
                .noneMatch(tag -> tag.equals(searchedTag))) {

            throw new LinkNotFoundException();
        }
    }


    private void checkUserInEvent(User user, Event event) {

        if(event.getParticipantLinks().stream()
                .noneMatch(eventParticipant -> eventParticipant.getUser().equals(user))) {

            throw new LinkNotFoundException();
        }
    }


    @Override
    public User getUser(long Id) {

        User user = userRepository.findById(Id);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getUsersWithPartOfName(String partUserName, int count) {

        PageRequest pageable = new PageRequest(0, count);
        return userRepository.findByUsernameContainingIgnoreCaseOrderByCreateDateDesc(partUserName, pageable).getContent();
    }

    @Override
    public List<User> getParticpantsWithPartName(long eventId, int count, String username, User requestedUser) {

        Event event = eventRepository.findById(eventId);

        if (event == null) {
            throw new EventNotFoundException();
        }

        PageRequest pageable = new PageRequest(0, count);

//        if (username != null) {
//
//            return userRepository.findParticipantsUsernameContaining(event.getId(), username, pageable).getContent();
//        }
//        else {
//            return userRepository.findParticipants(event.getId(), pageable).getContent();
//        }

        Stream<User> streamUsers = eventParticipantRepository.findByEvent(event)
                .stream()
                .map(s -> s.getUser())
                .filter(u -> !u.equals(requestedUser))
                .sorted((u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));

        if (username != null) {

            return streamUsers.filter(t -> t.getUsername()
                    .compareTo(username) > 0)
                    .limit(count)
                    .collect(Collectors.toList());
        } else {

            return streamUsers.limit(count).collect(Collectors.toList());
        }
    }

    @Override
    public void deleteUser(long userId, User requestedUser) {

        // Проверяем совпадение запросившего и удаляемого пользователя.
        if (requestedUser.getId() != userId) {
            throw new InsufficientlyRightsException();
        }

        User user = userRepository.findById(userId);

        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new UserNotFoundException();
        }

    }


    @Override
    public void addTags(List<String> tagNames, User requestedUser) {


        Set<Tag> userTags = requestedUser.getTagLinks().stream()
                .map(UserTag::getTag)
                .collect(Collectors.toSet());

        tagNames.stream()
                .map(tagService::getTag)
                .filter(tag -> !userTags.contains(tag))
                // создаем UserTag из тега и пользователя
                .map(tag -> {
                    UserTag userTag = new UserTag();
                    userTag.setTag(tag);
                    userTag.setUser(requestedUser);

                    return userTag;
                })
                .forEach(userTagRepository::save);

    }


    @Override
    public void deleteTags(List<String> tagNames, User requestedUser) {

        Set<Tag> tagOnDeletion = tagNames.stream()
                    .map(tagRepository::findByTagName)
                    .collect(Collectors.toSet());

        requestedUser.getTagLinks().stream()
                        .filter(userTag -> tagOnDeletion.contains(userTag.getTag()))
                        .forEach(userTagRepository::delete);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserLikeRepository(UserLikeRepository userLikeRepository) {
        this.userLikeRepository = userLikeRepository;
    }

    @Autowired
    public void setUserTagRepository(UserTagRepository userTagRepository) {
        this.userTagRepository = userTagRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setEventParticipantRepository(EventParticipantRepository eventParticipantRepository) {
        this.eventParticipantRepository = eventParticipantRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setAuthTokenRepository(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }
}
