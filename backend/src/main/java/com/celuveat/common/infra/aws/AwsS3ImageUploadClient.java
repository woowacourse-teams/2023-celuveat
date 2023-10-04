package com.celuveat.common.infra.aws;

import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.common.infra.AwsS3Property;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class AwsS3ImageUploadClient implements ImageUploadClient {

    private final S3Client s3Client;
    private final AwsS3Property awsS3Property;

    public void upload(MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsS3Property.bucket())
                .key(awsS3Property.key() + file.getOriginalFilename())
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        s3Client.putObject(putObjectRequest, requestBody);
    }
}
