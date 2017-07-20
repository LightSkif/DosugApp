package com.dosug.app.services.images;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.exception.UnknownServerException;
import com.dosug.app.exception.ValidateErrorException;
import com.dosug.app.repository.EventRepository;
import com.dosug.app.repository.UserRepository;
import com.dosug.app.response.model.ApiError;
import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.services.images.UploadProvider.ImageUploadProvider;
import com.dosug.app.utils.Consts;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@Service
public class SimpleImageService implements ImageService {

    @Resource(name = "ImageTypes")
    static HashMap<String, String> imageTypes;

    private ImageUploadProvider imageUploadService;

    private UserRepository userRepository;

    private EventRepository eventRepository;

    public String saveUserImage(User user, MultipartFile image, Locale locale) throws ValidateErrorException {

        String extension = checkImage(image, locale);
        String imagename = new String(getHashByName(user.getUsername()), StandardCharsets.UTF_8) + extension;
        String imageURL = imageUploadService.uploadImage(image, imagename);
        imageURL = imageURL.replaceAll("\\s", "");
        user.setAvatar(imageURL);
        userRepository.save(user);

        return imageURL;
    }

    @Override
    public String saveEventImage(User user, long eventId, MultipartFile image, Locale locale) throws ValidateErrorException {

        Event event = checkEventAndUserPermission(user, eventId, locale);
        String extension = checkImage(image, locale);
        String imagename = new String(getHashByName(user.getUsername()), StandardCharsets.UTF_8) + extension;
        String imageURL = imageUploadService.uploadImage(image, imagename);
        imageURL = imageURL.replaceAll("\\s", "");
        event.setAvatar(imageURL);
        eventRepository.save(event);

        return imageURL;
    }

    public MultipartFile imageLoad(String imageName) {

        return null;
    }

    private String checkImage(MultipartFile image, Locale locale) throws ValidateErrorException {

        List<ApiError> result = new ArrayList<>();

        if (image.getSize() > Consts.MEGABYTE_SIZE * Consts.MAX_MEGABYTE_COUNT) {
            result.add(new ApiError(ApiErrorCode.INVALID_IMAGE_SIZE, "Size of image is too big"));
        }

        ContentHandler contenthandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, image.getOriginalFilename());
        Parser parser = new AutoDetectParser();

        try {
            parser.parse(image.getInputStream(), contenthandler, metadata, new ParseContext());

            String mime = metadata.get(Metadata.CONTENT_TYPE);
            String extension = mime.substring(mime.indexOf('/'));
            extension = imageTypes.get(extension);

            if (extension == null) {
                result.add(new ApiError(ApiErrorCode.INVALID_IMAGE_EXTENSION, "Invalid image format"));
            }

            if (result.size() > 0) {
                throw new ValidateErrorException(result);
            }

            return extension;
        } catch (Exception e) {
            e.printStackTrace();
            result.add(new ApiError(ApiErrorCode.INVALID_IMAGE_EXTENSION, "Invalid image format"));
            throw new ValidateErrorException(result);
        }
    }

    private Event checkEventAndUserPermission(User user, long eventId, Locale locale) throws ValidateErrorException {

        List<ApiError> result = new ArrayList<>();

        Event event = eventRepository.findById(eventId);
        if (event == null) {
            result.add(new ApiError(ApiErrorCode.INVALID_IMAGE_EVENT_ID, "Event for image not found"));
            throw new ValidateErrorException(result);
        }

        User creator = event.getCreator();
        if (creator == null || !creator.equals(user)) {
            result.add(new ApiError(ApiErrorCode.INVALID_IMAGE_EVENT_CREATOR, "You haven't permission to change the pictures"));
            throw new ValidateErrorException(result);
        }

        return event;
    }

    private byte[] getHashByName(String name) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(
                    (name + LocalDateTime.now().toString())
                            .getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UnknownServerException();
        }
    }

    @Autowired
    public void setUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
