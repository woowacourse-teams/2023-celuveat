package com.celuveat.common.infra.aws;

import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.common.infra.AwsS3Property;
import com.celuveat.common.util.Base64Util;
import com.celuveat.common.util.FileNameUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public void upload(MultipartFile file) {
        String encodedFileName = getEncodedFileName(file);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsS3Property.bucket())
                .key(awsS3Property.key() + encodedFileName)
                .contentType(file.getContentType())
                .contentDisposition("inline")
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        s3Client.putObject(putObjectRequest, requestBody);
    }

    @Override
    public void upload(final List<MultipartFile> file) {
        if (file == null || file.isEmpty()) {
            return;
        }
        file.forEach(this::upload);
    }

    private String getEncodedFileName(MultipartFile file) {
        String rawFileName = FileNameUtil.removeExtension(file.getOriginalFilename());
        String encodeWithoutExtension = Base64Util.encode(rawFileName);
        return FileNameUtil.attachWebpExtension(encodeWithoutExtension);
    }
}
