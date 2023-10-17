package com.celuveat.event.command.application;


import com.celuveat.common.util.Base64Util;
import com.celuveat.common.util.FileNameUtil;
import com.celuveat.event.config.EventAwsS3Property;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class EventImageUploadClient {

    private final S3Client s3Client;
    private final EventAwsS3Property awsS3Property;

    @Async("eventThreadPoolTaskExecutor")
    public void upload(
            String imageName,
            MultipartFile file
    ) {
        try {
            String encodedFileName = getEncodedFileName(imageName);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsS3Property.bucket())
                    .key(awsS3Property.key() + encodedFileName)
                    .contentType(file.getContentType())
                    .contentDisposition("inline")
                    .build();
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEncodedFileName(
            String name
    ) {
        return FileNameUtil.attachWebpExtension(Base64Util.encode(name));
    }
}
