package com.dosug.app.services;

import com.dosug.app.domain.User;
import com.dosug.app.respose.model.ApiError;

import java.util.List;

/**
 * Сервис для работы с регистрацией который инкапсулирует в себя
 * бизнес-логику чтобы не тащить в контроллер
 * @author radmirnovii
 */
public interface RegistrationService {

    /**
     * Если возникает ошибка то мы просто возвращаем
     * список ошибок которые уже вернут клиенту
     * если ошибок нету возвращаем пустой список
     */
    List<ApiError> registration(User user);
}
