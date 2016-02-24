package com.orange.spring.cloud.connector.s3.heroku;

/**
 * Copyright (C) 2016 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'Apache-2.0'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'https://opensource.org/licenses/Apache-2.0'.
 * <p/>
 * Author: Arthur Halet
 * Date: 24/02/2016
 */
public enum S3DetectableService {
    Buckeeter("BUCKETEER_BUCKET_NAME", "BUCKETEER_BUCKET_NAME", "BUCKETEER_AWS_ACCESS_KEY_ID", "BUCKETEER_AWS_SECRET_ACCESS_KEY", "https://$bucketName.s3.amazonaws.com"),
    AWS("S3_BUCKET_NAME", "S3_BUCKET_NAME", "AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY", "https://$bucketName.s3.amazonaws.com");
    private String detectEnvKey;
    private String bucketNameEnvKey;
    private String accessKeyIdEnvKey;
    private String secretAccessKeyEnvKey;
    private String baseUrl;


    S3DetectableService(String detectEnvKey, String bucketNameEnvKey, String accessKeyIdEnvKey, String secretAccessKeyEnvKey, String baseUrl) {
        this.detectEnvKey = detectEnvKey;
        this.bucketNameEnvKey = bucketNameEnvKey;
        this.accessKeyIdEnvKey = accessKeyIdEnvKey;
        this.secretAccessKeyEnvKey = secretAccessKeyEnvKey;
        this.baseUrl = baseUrl;
    }

    public String getDetectEnvKey() {
        return detectEnvKey;
    }

    public String getBucketNameEnvKey() {
        return bucketNameEnvKey;
    }

    public String getAccessKeyIdEnvKey() {
        return accessKeyIdEnvKey;
    }

    public String getSecretAccessKeyEnvKey() {
        return secretAccessKeyEnvKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
