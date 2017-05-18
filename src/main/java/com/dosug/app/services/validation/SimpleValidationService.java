package com.dosug.app.services.validation;

import com.dosug.app.form.ErrorCode;
import com.dosug.app.respose.model.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.ReflectionHelper.getPropertyName;

/**
 * Простая имплементация
 */
@Service
public class SimpleValidationService implements ValidationService {

    private final static Logger logger = LoggerFactory.getLogger(SimpleValidationService.class);

    private final ValidatorFactory validatorFactory
            = Validation.buildDefaultValidatorFactory();

    private MessageSource messageSource;

    @Override
    public List<ApiError> validate(Object form, Locale locale) {
        Validator validator = validatorFactory.getValidator();

        List<ApiError> result = new LinkedList<>();

        result.addAll(getApiErrorsForFields(form, validator));

        result.addAll(getApiErrorsForMethods(form, validator));


        return result.stream()
                .map(error -> localizeError(error, locale))
                .collect(Collectors.toList());

    }

    private ApiError localizeError(ApiError error, Locale locale) {

        return new ApiError(error.getErrorCode(),
                    messageSource.getMessage(error.getMessage(),null, locale));
    }

    private List<ApiError> getApiErrorsForMethods(Object form, Validator validator) {

        List<ApiError> result = new LinkedList<>();

        Set<Method> methods = getAllMethods(form.getClass());
        for (Method method : methods) {
            //получаем аннотацию с кодом ошибки
            ErrorCode errorCodeAnnotation = method.getAnnotation(ErrorCode.class);
            if (errorCodeAnnotation == null) {
                //тогда метод не интересен
                continue;
            }

            //валидируем метод
            // делаем getPropertyName так как иначе hibernate
            // будет говорить что не нашел ничего
            Set<? extends ConstraintViolation<?>> constraintViolations =
                    validator.validateProperty(form, getPropertyName(method));

            List<ApiError> apiErrorsForOneMethod =
                    violationsToApiErrors(constraintViolations, errorCodeAnnotation);

            //добавляем все полученные ошибки для метода
            result.addAll(apiErrorsForOneMethod);
        }

        return result;
    }

    private List<ApiError> getApiErrorsForFields(Object form, Validator validator) {
        List<ApiError> result = new LinkedList<>();
        try {
            Set<Field> fields = getAllFields(form.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                //получаем саму анотацию над полем
                ErrorCode errorCodeAnnotation = field.getAnnotation(ErrorCode.class);

                if (errorCodeAnnotation == null) {
                    //тогда нас не интересует это поле
                    //идем к следующему
                    continue;
                }

                Set<? extends ConstraintViolation<?>> constraintViolations =
                        validator.validateValue(form.getClass(), field.getName(), field.get(form));

                List<ApiError> apiErrorsForOneField =
                        violationsToApiErrors(constraintViolations, errorCodeAnnotation);

                //и наконец добавляем все найденные ошибки в result
                result.addAll(apiErrorsForOneField);
            }
        } catch (IllegalAccessException e) {
            // я уже поставил ассеss true в начале цикла так что здесь не падает
            logger.error(e.getMessage());
        }

        return result;
    }

    private List<ApiError> violationsToApiErrors(Collection<? extends ConstraintViolation<?>> constraintViolations, ErrorCode errorCodeAnnotation) {
        return constraintViolations.stream()
                .map(violation -> new ApiError(errorCodeAnnotation.code(), violation.getMessage()))
                .collect(Collectors.toList());
    }


    private Set<Method> getAllMethods(Class formClass) {
        Set<Method> allMethods = new HashSet<>();
        // добавляем методы в множество
        allMethods.addAll(Arrays.asList(formClass.getDeclaredMethods()));

        if(formClass.getSuperclass() != null) {
            Set<Method> superClassMethods = getAllMethods(formClass.getSuperclass());

            superClassMethods.stream()
                    .filter(method -> !allMethods.contains(method))
                    .forEach(allMethods::add);
        }


        return allMethods;
    }

    private Set<Field> getAllFields(Class formClass) {
        Set<Field> allFields = new HashSet<>();
        // добавляем методы в множество
        allFields.addAll(Arrays.asList(formClass.getDeclaredFields()));

        if(formClass.getSuperclass() != null) {
            Set<Field> superClassFields = getAllFields(formClass.getSuperclass());

            superClassFields.stream()
                    .filter(method -> !allFields.contains(method))
                    .forEach(allFields::add);
        }


        return allFields;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
