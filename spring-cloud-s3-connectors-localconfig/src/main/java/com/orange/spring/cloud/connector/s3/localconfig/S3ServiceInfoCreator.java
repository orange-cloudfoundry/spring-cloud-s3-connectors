package com.orange.spring.cloud.connector.s3.localconfig;

import com.orange.spring.cloud.connector.s3.core.service.S3ServiceInfo;
import org.springframework.cloud.localconfig.LocalConfigServiceInfoCreator;

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
public class S3ServiceInfoCreator extends LocalConfigServiceInfoCreator<S3ServiceInfo> {

    public S3ServiceInfoCreator() {
        super(S3ServiceInfo.S3_SCHEME);
    }

    @Override
    public S3ServiceInfo createServiceInfo(String id, String uri) {
        return new S3ServiceInfo(id, uri);
    }
}