package com.orange.spring.cloud.connector.s3.core.creator;

import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStore;
import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStoreContext;
import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright (C) 2016 Arthur Halet
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 24/02/2016
 */
public class S3FactoryCreatorTest {
    private static final String TEST_HOST = "10.20.30.40";
    private static final int TEST_PORT = 1234;
    private static final String TEST_USERNAME = "myuser";
    private static final String TEST_PASSWORD = "mypass";
    private static final String TEST_PATH = "mybucket";
    private static final String TEST_SCHEME = "s3";

    private S3FactoryCreator testCreator = new S3FactoryCreator();

    @Test
    public void create() throws Exception {
        S3ServiceInfo serviceInfo = createServiceInfo();

        SpringCloudBlobStoreContext contextBuilder = testCreator.create(serviceInfo, null);
        assertConnectorProperties(serviceInfo, contextBuilder);
    }

    public S3ServiceInfo createServiceInfo() {
        return new S3ServiceInfo("id", TEST_SCHEME, TEST_HOST, TEST_PORT, TEST_USERNAME, TEST_PASSWORD, TEST_PATH);
    }

    private void assertConnectorProperties(S3ServiceInfo serviceInfo, SpringCloudBlobStoreContext contextBuilder) {
        SpringCloudBlobStore springCloudBlobStore = contextBuilder.getSpringCloudBlobStore();
        assertNotNull(contextBuilder);
        assertNotNull(springCloudBlobStore);
        assertNotNull(springCloudBlobStore.getWrappedBlobStore());
        assertNotNull(serviceInfo.getBucket());
        assertEquals(serviceInfo.getBucket(), springCloudBlobStore.getBucketName());
    }
}