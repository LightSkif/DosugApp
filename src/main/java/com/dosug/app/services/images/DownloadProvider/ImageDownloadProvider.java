package com.dosug.app.services.images.DownloadProvider;

import org.springframework.web.multipart.MultipartFile;

public interface ImageDownloadProvider {

    MultipartFile downloadImage(String filename);
}
