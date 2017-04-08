package com.dosug.app.services.validation;

import com.dosug.app.respose.model.ApiError;

import java.util.List;

/**
 * Сервис который занимается только валидацией
 */
public interface ValidationService {
    /**
     * Возвращает все ошибки связанные с валидацие которые
     * надо вернуть на клиент, если ошибок нет возвращает
     * пустой список
     */
    List<ApiError> validate(Object form);
}
