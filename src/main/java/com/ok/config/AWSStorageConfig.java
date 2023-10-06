package com.ok.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSStorageConfig {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String accessSecretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;
}
