package com.blackwhitemap.blackwhitemap_back.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageUrlConverter {

    private final String imageBaseUrl;

    public ImageUrlConverter(
            @Value("${app.image.base-url}")
            String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public List<String> toFullUrls(List<String> fileNames) {
        return fileNames.stream()
                .map(fileName -> imageBaseUrl + "/" + fileName)
                .toList();
    }
}
