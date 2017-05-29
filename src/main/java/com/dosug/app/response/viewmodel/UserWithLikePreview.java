package com.dosug.app.response.viewmodel;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Объекты для возвращения клиенты пользователя со списком тегов,
 * по которым можно поставить лайки по заданному событию.
 */
public class UserWithLikePreview {

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String avatar;

    @JsonProperty
    private List<TagUserLikePreview> tags;

    public UserWithLikePreview(User ratedUser, Event event, User evaluateUser) {
        userId = ratedUser.getId();
        username = ratedUser.getUsername();
        avatar = ratedUser.getAvatar();

        // Находим все общие для события и оцениваемого пользователя теги.
        List<Tag> overlappingTags = event.getTags().stream()
                .filter(s -> ratedUser.getTagLinks().stream()
                            .anyMatch(t -> t.getTag().equals(s)))
                .collect(Collectors.toList());

        tags = TagUserLikePreviewsBuilder(overlappingTags, event, evaluateUser, ratedUser);
    }

    private List<TagUserLikePreview> TagUserLikePreviewsBuilder(List<Tag> overlappingTags, Event event, User evaluateUser, User ratedUser) {

        // Переводим все теги в объект с id, названием тега и лайком.
        return overlappingTags.stream()
            .map(tag -> new TagUserLikePreview(tag.getId(),
                    tag.getTagName(),
                    tag.getUserLinks().stream()
                        //выбираем только тот userTag, который связан с ratedUser
                        .filter(userTag -> userTag.getUser().equals(ratedUser))
                        //получаем все лайки по этому тегу для нужного  юзера
                        .flatMap(userTag -> userTag.getLikes().stream())
                        //проверяем был ли такой пользователь который лайкал
                        //этого пользователя по этому тегу в этом событии
                        .anyMatch(userLike -> userLike.getEvaluateUser().equals(evaluateUser)
                                        && userLike.getEvent().equals(event))))

            .collect(Collectors.toList());
    }

}
