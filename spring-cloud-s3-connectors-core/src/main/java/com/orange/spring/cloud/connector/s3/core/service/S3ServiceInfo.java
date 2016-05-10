package com.orange.spring.cloud.connector.s3.core.service;


import org.springframework.cloud.service.UriBasedServiceInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class S3ServiceInfo extends UriBasedServiceInfo {

    public final static String S3_SCHEME = "s3";
    private static final String AWS_S3_PATTERN = "^((([^\\.])*)\\.)?s3([^\\.])*\\.amazonaws\\.com$";
    private static final String VIRTUAL_HOST_BUCKETS_PATTERN = "^([^\\.]*)";
    private String bucket;

    public S3ServiceInfo(String id, String scheme, String host, int port, String username, String password, String path) {
        super(id, scheme, host, port, username, password, path);
        this.extractBucket();
    }

    public S3ServiceInfo(String id, String uriString) {
        super(id, uriString);
        this.extractBucket();
    }

    private void extractBucket() {
        if (!this.isVirtualHostBuckets()) {
            this.bucket = this.getPath();
            return;
        }
        String bucketFromHost = this.getBucketFromVirtualHost();
        if (bucketFromHost != null && !bucketFromHost.isEmpty()) {
            this.bucket = bucketFromHost;
            return;
        }
        this.bucket = this.getPath();
    }

    public String getS3Host() {
        String host = this.getHost();
        if (this.isVirtualHostBuckets()) {
            List<String> splittedHost = new ArrayList<String>(Arrays.asList(host.split("\\.")));
            splittedHost.remove(0);
            host = String.join(".", splittedHost);
        }
        String port = "";
        if (this.getPort() != -1) {
            port += ":" + this.getPort();
        }
        String protocol = this.getProtocol();
        return protocol + "://" + host + port;
    }


    public String getBucketFromVirtualHost() {
        if (!this.isVirtualHostBuckets()) {
            return null;
        }
        Pattern pattern = Pattern.compile(VIRTUAL_HOST_BUCKETS_PATTERN);
        Matcher matcher = pattern.matcher(this.getHost());
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    public boolean isVirtualHostBuckets() {
        if ((this.getPath() == null || this.getPath().isEmpty())
                && (this.getHost().split("\\.").length >= 3)) {
            return true;
        }
        return false;
    }

    private String getProtocol() {
        String protocol = this.getScheme();
        if (protocol.equals("s3") && !this.isAwsS3()) {
            protocol = "https";
        } else if (protocol.equals("s3") && this.isAwsS3()) {
            protocol = "http";
        }
        return protocol;
    }

    public String getAccessKeyId() {
        return this.getUserName();
    }

    public String getSecretAccessKey() {
        return this.getPassword();
    }

    public boolean isAwsS3() {
        return this.getHost().matches(AWS_S3_PATTERN);
    }


    public String getBucket() {
        return this.bucket;
    }

    @Override
    public int hashCode() {
        return getUri().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof S3ServiceInfo)) return false;
        boolean result = false;
        S3ServiceInfo that = (S3ServiceInfo) o;
        result = that.getHost() != null ? that.getHost().equals(this.getHost()) : that.getHost() == this.getHost();
        result &= that.getPassword() != null ? that.getPassword().equals(this.getPassword()) : that.getPassword() == this.getPassword();
        result &= that.getUserName() != null ? that.getUserName().equals(this.getUserName()) : that.getUserName() == this.getUserName();
        result &= that.getId() != null ? that.getId().equals(this.getId()) : that.getId() == this.getId();
        result &= that.getPort() == this.getPort();
        result &= that.getUri() != null ? that.getUri().equals(this.getUri()) : that.getUri() == this.getUri();
        result &= that.getBucket() != null ? that.getBucket().equals(this.getBucket()) : that.getBucket() == this.getBucket();
        return result;

    }
}