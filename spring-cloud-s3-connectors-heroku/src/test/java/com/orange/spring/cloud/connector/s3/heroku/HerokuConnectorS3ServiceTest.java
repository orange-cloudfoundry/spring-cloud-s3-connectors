package com.orange.spring.cloud.connector.s3.heroku;

import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.cloud.heroku.HerokuConnector;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * @author Arthur Halet
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EnvironmentAccessor.class})
public class HerokuConnectorS3ServiceTest {

    protected static final String hostname = "10.20.30.40";
    protected static final int port = 1234;
    protected static final String username = "myuser";
    protected static final String password = "mypass";
    private final static String bucketName = "mybucket";
    protected HerokuConnector testCloudConnector = new HerokuConnector();

    protected static ServiceInfo getServiceInfo(List<ServiceInfo> serviceInfos, String serviceId) {
        for (ServiceInfo serviceInfo : serviceInfos) {
            if (serviceInfo.getId().equals(serviceId)) {
                return serviceInfo;
            }
        }
        return null;
    }

    @Before
    public void init() {
        // we are oblige to mock System.getenv for EnvironmentAccessor class in order to fake heroku environment
        mockStatic(System.class);
    }

    @Test
    public void s3ServiceCreationFromEnvVar() {
        Map<String, String> env = new HashMap<String, String>();
        String s3Url = getS3ServiceUrl();
        env.put("S3_URL", s3Url);
        when(System.getenv()).thenReturn(env);
        List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
        ServiceInfo serviceInfo = getServiceInfo(serviceInfos, "S3");
        assertNotNull(serviceInfo);
        assertTrue(serviceInfo instanceof S3ServiceInfo);
        assertS3ServiceInfo((S3ServiceInfo) serviceInfo, hostname, port);
    }

    @Test
    public void s3ServiceCreationFromDetectableService() {
        for (S3DetectableService s3DetectableService : S3DetectableService.values()) {
            when(System.getenv()).thenReturn(this.getEnvDetectableService(s3DetectableService));
            List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
            ServiceInfo serviceInfo = getServiceInfo(serviceInfos, bucketName);
            assertNotNull(serviceInfo);
            assertTrue(serviceInfo instanceof S3ServiceInfo);
            assertS3ServiceInfo((S3ServiceInfo) serviceInfo, this.getHost(s3DetectableService, bucketName), -1);
        }

    }

    private String getHost(S3DetectableService s3DetectableService, String bucketName) {
        URI uri = URI.create(s3DetectableService.getBaseUrl().replace("$bucketName", bucketName));
        return uri.getHost();
    }

    private Map<String, String> getEnvDetectableService(S3DetectableService s3DetectableService) {
        Map<String, String> env = new HashMap<String, String>();
        env.put(s3DetectableService.getBucketNameEnvKey(), bucketName);
        env.put(s3DetectableService.getAccessKeyIdEnvKey(), username);
        env.put(s3DetectableService.getSecretAccessKeyEnvKey(), password);
        return env;
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
