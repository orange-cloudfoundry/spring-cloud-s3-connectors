package com.orange.cloudfoundry.connector.service.info;

import org.springframework.cloud.service.UriBasedServiceInfo;

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
public class S3ServiceInfo extends UriBasedServiceInfo {
    public S3ServiceInfo(String id, String scheme, String host, int port, String username, String password, String path) {
        super(id, scheme, host, port, username, password, path);
    }

    public S3ServiceInfo(String id, String uriString) {
        super(id, uriString);
    }


    public String getBucket() {
        return this.getPath();
    }
}
