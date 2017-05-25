package com.dosug.app.services.users;

import com.dosug.app.domain.*;
import com.dosug.app.exception.*;
import com.dosug.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SimpleUserService implements UserService {

    private UserRepository userRepository;

    private UserLikeRepository userLikeRepository;

    private UserTagRepository userTagRepository;

    private TagRepository tagRepository;

    private EventRepository eventRepository;

    @Override
    public Long updateUser(User user, User requestedUser) {
        return null;
    }

    @Override
    public Long updateUserPassword(User user, User requestedUser) {
        return null;
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
    public void addLike(long ratedUserId, long eventId, long tagId, User evaluateUser) {

        UserLike newUserLike = userLikeBuilder(ratedUserId, eventId, tagId, evaluateUser);

        // Пытаемся найти идентичный лайк в бд.
        UserLike updatedUserLike = userLikeRepository.findByEventAndEvaluateUserAndRatedUserAndTag(
                newUserLike.getEvent(), evaluateUser, newUserLike.getRatedUser(), newUserLike.getTag());

        // Проверяем существование идентичного лайка.
        if (updatedUserLike == null) {
            // Находим связку оценённого пользователя и тега, по которому производится добавление лайка.
            UserTag userTagLink = newUserLike.getRatedUser().getTagLinks().stream().filter(s -> s.getTag().getId() == tagId).findFirst().get();

            // Увеличиваем счётчик лайков в связке.
            userTagLink.setLikeCount(userTagLink.getLikeCount() + 1);
            userTagRepository.save(userTagLink);

            userLikeRepository.save(newUserLike);
        }
    }

    @Override
    public void removeLike(long ratedUserId, long eventId, long tagId, User evaluateUser) {


        // Пытаемся найти идентичный лайк в бд.
        UserLike removeUserLike = userLikeRepository.findByEventAndEvaluateUserAndRatedUserAndTag(
                eventRepository.findById(eventId), evaluateUser, userRepository.findById(ratedUserId), tagRepository.findById(tagId));

        if (removeUserLike != null) {
            // Находим связку оценённого пользователя и тега, по которому производится добавление лайка.
            UserTag userTagLink = removeUserLike.getRatedUser().getTagLinks().stream().filter(s -> s.getTag().getId() == tagId).findFirst().get();

            // Увеличиваем счётчик лайков в связке.
            userTagLink.setLikeCount(userTagLink.getLikeCount() - 1);

            userTagRepository.save(userTagLink);

            userLikeRepository.delete(removeUserLike);
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
    public UserLike userLikeBuilder(long ratedUserId, long eventId, long tagId, User evaluateUser) {
        Event event = eventRepository.findById(eventId);

        if (event == null) {
            throw new EventNotFoundException();
        }

        // Проверяем наличее пользователя, поставившего лайк в списке участников.
        if (event.getParticipantLinks().stream()
                .noneMatch(s -> s.getUser().equals(evaluateUser))) {

            throw new LinkNotFoundException();
        }

        // Проверяем наличее пользователя, получившего лайк в списке участников.
        Optional<User> optionalRatedUser = event.getParticipantLinks().stream()
                .filter(s -> s.getUser().getId() == ratedUserId)
                .findFirst()
                .map(s -> s.getUser());
        User ratedUser;

        if (optionalRatedUser.isPresent()) {

            ratedUser = optionalRatedUser.get();
        } else {

            throw new LinkNotFoundException();
        }

        // Проверяем наличее тега, по которому проходит лайк, в списке тегов события.
        Optional<Tag> optionalTag = event.getTags().stream()
                .filter(s -> s.getId() == tagId)
                .findFirst();
        Tag tag;

        if (optionalTag.isPresent()) {

            tag = optionalTag.get();
        } else {

            throw new TagNotFoundException();
        }

        // Проверяем наличие тега, по которому проходит лайк, у оцениваемого пользователя.
        if (ratedUser.getTagLinks().stream()
                .noneMatch(s -> s.getTag().equals(tag))) {

            throw new TagNotFoundException();
        }

        UserLike userLike = new UserLike();
        userLike.setEvent(event);
        userLike.setEvaluateUser(evaluateUser);
        userLike.setRatedUser(ratedUser);
        userLike.setTag(tag);

        return userLike;
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
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
