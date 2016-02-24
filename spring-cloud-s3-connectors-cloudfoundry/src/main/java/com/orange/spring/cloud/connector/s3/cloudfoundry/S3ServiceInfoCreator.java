package com.orange.spring.cloud.connector.s3.cloudfoundry;

import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

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
public class S3ServiceInfoCreator extends CloudFoundryServiceInfoCreator<S3ServiceInfo> {
    public S3ServiceInfoCreator() {
        super(new Tags("riak-cs", "s3", "swift", "google-storage"), "s3");
    }

    public S3ServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        Map<String, Object> credentials = this.getCredentials(serviceData);
        String id = (String) serviceData.get("name");
        String uri = this.getUriFromCredentials(credentials);
        return new S3ServiceInfo(id, uri);
    }
}