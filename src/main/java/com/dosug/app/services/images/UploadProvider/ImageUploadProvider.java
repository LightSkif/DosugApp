package com.dosug.app.services.images.UploadProvider;

import org.springframework.web.multipart.MultipartFile;


public interface ImageUploadProvider {

    String uploadImage(MultipartFile image, String filename);
}
