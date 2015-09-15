package com.orange.cloudfoundry.connector.service.info;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

/**
 * Copyright (C) 2015 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 03/06/2015
 */
public class S3ServiceInfoCreator extends CloudFoundryServiceInfoCreator<S3ServiceInfo> {
    public S3ServiceInfoCreator() {
        super(new Tags("riak-cs", "s3"));
    }

    public S3ServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        Map credentials = this.getCredentials(serviceData);
        String id = (String) serviceData.get("name");
        String uri = this.getUriFromCredentials(credentials);
        return new S3ServiceInfo(id, uri);
    }
}
