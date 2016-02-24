package com.orange.spring.cloud.connector.s3.localconfig;

import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
public class LocalConfigConnectorS3ServiceTest {
    private S3ServiceInfoCreator creator;

    @Before
    public void setUp() throws Exception {
        creator = new S3ServiceInfoCreator();
    }

    @After
    public void tearDown() throws Exception {
        creator = null;
    }

    @Test
    public void serviceCreation() {
        S3ServiceInfo info = creator.createServiceInfo("riak", "s3://myuser:mypass@10.20.30.40:1234/bucketname");
        assertEquals(new S3ServiceInfo("riak", "s3", "10.20.30.40", 1234, "myuser", "mypass", "bucketname"), info);
    }

}