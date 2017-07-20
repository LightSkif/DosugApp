package com.dosug.app.services.images.DownloadProvider;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class LocalImageDownloadProvider implements ImageDownloadProvider {

    public MultipartFile downloadImage(String filename) {

        File image = new File(filename);

        if (image.exists() && !image.isDirectory()) {

        }

        return null;
    }
}
