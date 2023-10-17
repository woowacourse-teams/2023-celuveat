package com.celuveat.event.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event.aws.s3")
public record EventAwsS3Property(
        String bucket,
        String key
) {
}
