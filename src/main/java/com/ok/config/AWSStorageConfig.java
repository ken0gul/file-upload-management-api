package com.ok.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSStorageConfig {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String accessSecretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;


    @Bean
    AmazonS3 s3Client() {
        // First get the credentials object passing in accessKey and accessSecretKey
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecretKey);

        // Build S3 Client
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
