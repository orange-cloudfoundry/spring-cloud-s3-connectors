package com.orange.spring.cloud.connector.s3.localconfig;

import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.cloud.localconfig.LocalConfigConnector;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({EnvironmentAccessor.class})
public class LocalConfigConnectorS3ServiceTest {
    protected static final String hostname = "10.20.30.40";
    protected static final int port = 1234;
    protected static final String username = "myuser";
    protected static final String password = "mypass";
    private final static String bucketName = "mybucket";
    protected LocalConfigConnector testCloudConnector = new LocalConfigConnector();

    protected static ServiceInfo getServiceInfo(List<ServiceInfo> serviceInfos, String serviceId) {
        for (ServiceInfo serviceInfo : serviceInfos) {
            if (serviceInfo.getId().equals(serviceId)) {
                return serviceInfo;
            }
        }
        return null;
    }

    @Before
    public void setUp() throws Exception {
        // we are oblige to mock System.getProperties for EnvironmentAccessor class in order to fake heroku environment
        mockStatic(System.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void serviceCreation() {
        Properties properties = new Properties();
        properties.put("spring.cloud.mys3", getS3ServiceUrl());
        properties.put("spring.cloud.appId", "mysuperapp");
        when(System.getProperties()).thenReturn(properties);
        when(System.getProperty("spring.cloud.appId", null)).thenReturn("mysuperapp");
        assertTrue(this.testCloudConnector.isInMatchingCloud());
        List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
        ServiceInfo serviceInfo = getServiceInfo(serviceInfos, "mys3");
        assertNotNull(serviceInfo);
        assertTrue(serviceInfo instanceof S3ServiceInfo);
        assertS3ServiceInfo((S3ServiceInfo) serviceInfo, hostname, port);
    }

    private String getS3ServiceUrl() {
        String template = "s3://$username:$password@$hostname:$port/$bucket";

        return template.replace("$username", username).
                replace("$password", password).
                replace("$hostname", hostname).
                replace("$port", Integer.toString(port)).
                replace("$bucket", bucketName);
    }

    protected void assertS3ServiceInfo(S3ServiceInfo serviceInfo, String host, int port) {
        assertEquals(host, serviceInfo.getHost());
        assertEquals(port, serviceInfo.getPort());
        assertEquals(username, serviceInfo.getUserName());
        assertEquals(password, serviceInfo.getPassword());
        assertEquals(bucketName, serviceInfo.getBucket());
    }
}