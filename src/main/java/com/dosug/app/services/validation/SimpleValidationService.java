package com.dosug.app.services.validation;

import com.dosug.app.form.ErrorCode;
import com.dosug.app.respose.model.ApiError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Простая имплементация
 */
@Service
public class SimpleValidationService implements ValidationService {

    private final ValidatorFactory validatorFactory
            = Validation.buildDefaultValidatorFactory();

    @Override
    public List<ApiError> validate(Object form) {
        Validator validator = validatorFactory.getValidator();

        List<ApiError> result = new LinkedList<>();
        try {
            for(Field field: form.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                //получаем саму анотацию над полем
                ErrorCode errorCodeAnnotation = field.getAnnotation(ErrorCode.class);

                if(errorCodeAnnotation == null) {
                    //тогда нас не интересует это поле
                    //идем к следующему
                    continue;
                }

                Set<? extends ConstraintViolation<?>> constraintViolations =
                        validator.validateValue(form.getClass(), field.getName(), field.get(form));

                List<ApiError> apiErrorsForOneField = constraintViolations.stream()
                        .map(violation -> new ApiError(errorCodeAnnotation.code(), violation.getMessage()))
                        .collect(Collectors.toList());

                //и наконец добавляем все найденные ошибки в result
                result.addAll(apiErrorsForOneField);
            }
        } catch (IllegalAccessException e) {
            // я уже поставил ассеss true в начале цикла так что здесь не падает
        }

        return result;
    }

}
