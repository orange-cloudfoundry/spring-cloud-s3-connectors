package com.orange.cloudfoundry.connector.s3.factory;

import org.jclouds.ContextBuilder;

/**
 * Copyright (C) 2015 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 04/06/2015
 */
public class S3ContextBuilder {
    private String bucketName;
    private ContextBuilder contextBuilder;

    public ContextBuilder getContextBuilder() {
        if (this.contextBuilder == null) {
            this.contextBuilder = ContextBuilder.newBuilder("s3");
        }
        return contextBuilder;
    }


    public String getBucketName() {
        return bucketName;
    }

    public S3ContextBuilder setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }
}
