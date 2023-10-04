package com.celuveat.common.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record AwsS3Property(
        String bucket,
        String key
) {
}
