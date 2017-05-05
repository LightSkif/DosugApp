package com.dosug.app.services;

import com.dosug.app.domain.User;
import com.dosug.app.repository.UserRepository;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.ApiErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author radmirnovii
 */
@Service
public class SimpleRegistrationService implements RegistrationService {

    private static final String USERNAME_ALREADY_USE_MESSAGE = "username already use";

    private static final String EMAIL_ALREADY_USE_MESSAGE = "email already use";

    private static final String UNKNOWN_PROBLEM_MESSAGE = "unknown service problem";

    private UserRepository userRepository;

    @Autowired
    public SimpleRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<ApiError> registration(User user) {

        List<ApiError> apiErrors = new LinkedList<>();

        if (user == null) {
            apiErrors.add(getUnknownServiceProblem());
            return apiErrors;
        }

        format(user);

        //дата создания
        user.setCreateDate(LocalDateTime.now());

        try {
            userRepository.save(user);
        } catch (Exception e) {

            List<ApiError> duplicateErrors = checkOnDuplicated(user);
            if (!duplicateErrors.isEmpty()) {
                return duplicateErrors;
            }
            //Вернем неизвестную проблему
            apiErrors.add(getUnknownServiceProblem());
        }

        return apiErrors;
    }

    /**
     * приводим к нижему регистру добавляем всякие нужные плюшки
     *
     * @param user пользователь которого форматируем
     */
    private void format(User user) {
        //username to lower
        user.setUsername(user.getUsername().toLowerCase());
        //email to lower
        user.setEmail(user.getEmail().toLowerCase());
    }


    private ApiError getUnknownServiceProblem() {
        return new ApiError(ApiErrorCode.UNKNOWN_SERVICE_PROBLEM, UNKNOWN_PROBLEM_MESSAGE);
    }

    /**
     * Ищем ошибки ограничений уникальности в бд
     *
     * @param user пользователь дубликаты полей которого ищем в бд
     * @return ошибки связанные с дубликатами если из нет значит возвращается пустой лист
     */
    private List<ApiError> checkOnDuplicated(User user) {
        List<ApiError> apiErrors = new LinkedList<>();

        ApiError usernameError = checkUsernameDuplicate(user.getUsername());
        if (usernameError != null) {
            apiErrors.add(usernameError);
        }

        ApiError emailError = checkEmailDuplicate(user.getEmail());
        if (emailError != null) {
            apiErrors.add(emailError);
        }

        return apiErrors;
    }

    /**
     * @param username
     * @return null если нет ошибки
     */
    private ApiError checkEmailDuplicate(String username) {
        if (userRepository.findByEmail(username) != null) {
            return new ApiError(ApiErrorCode.EMAIL_ALREADY_USE, EMAIL_ALREADY_USE_MESSAGE);
        }
        return null;
    }

    /**
     * @param email
     * @return null если нет ошибки
     */
    private ApiError checkUsernameDuplicate(String email) {
        if (userRepository.findByUsername(email) != null) {
            return new ApiError(ApiErrorCode.USERNAME_ALREADY_USE, USERNAME_ALREADY_USE_MESSAGE);
        }
        return null;
    }
}
