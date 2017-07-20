package com.dosug.app.services.images;

import com.dosug.app.domain.User;
import com.dosug.app.exception.ValidateErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

/**
 * Created by User on 10.07.2017.
 */
public interface ImageService {

    String saveUserImage(User user, MultipartFile image, Locale locale) throws ValidateErrorException;

    String saveEventImage(User user, long eventId, MultipartFile image, Locale locale) throws ValidateErrorException;

    MultipartFile imageLoad(String imageName);
}
