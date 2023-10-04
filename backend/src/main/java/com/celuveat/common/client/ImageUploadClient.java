package com.celuveat.common.client;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadClient {

    void upload(MultipartFile file);
}
