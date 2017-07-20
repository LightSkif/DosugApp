package com.dosug.app.services.images.UploadProvider;

import org.springframework.web.multipart.MultipartFile;

public class AmazonS3ImageUploadProvider implements ImageUploadProvider {

    @Override
    public String uploadImage(MultipartFile image, String filename) {

        return null;
    }
}
