package com.celuveat.common.client;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadClient {

    void upload(MultipartFile file);

    void upload(List<MultipartFile> file);
}
