package com.orange.spring.cloud.connector.s3.core.jcloudswrappers;

import com.google.common.reflect.TypeToken;
import org.jclouds.Context;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.rest.Utils;

import java.io.Closeable;

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
public class SpringCloudBlobStoreContext implements BlobStoreContext {

    private BlobStoreContext wrappedBlobStoreContext;
    private SpringCloudBlobStore springCloudBlobStore;

    public SpringCloudBlobStoreContext(BlobStoreContext blobStoreContext, String bucketName) {
        this.wrappedBlobStoreContext = blobStoreContext;
        springCloudBlobStore = new SpringCloudBlobStore(blobStoreContext.getBlobStore(), bucketName, this);
    }


    public BlobRequestSigner getSigner() {
        return wrappedBlobStoreContext.getSigner();
    }

    public BlobStore getBlobStore() {
        return wrappedBlobStoreContext.getBlobStore();
    }

    public ConsistencyModel getConsistencyModel() {
        return wrappedBlobStoreContext.getConsistencyModel();
    }

    public Utils utils() {
        return wrappedBlobStoreContext.utils();
    }

    public void close() {
        wrappedBlobStoreContext.close();
    }

    public SpringCloudBlobStore getSpringCloudBlobStore() {
        return springCloudBlobStore;
    }

    public TypeToken<?> getBackendType() {
        return wrappedBlobStoreContext.getBackendType();
    }

    public <C extends Context> C unwrap(TypeToken<C> typeToken) throws IllegalArgumentException {
        return wrappedBlobStoreContext.unwrap(typeToken);
    }

    public <C extends Context> C unwrap() throws ClassCastException {
        return wrappedBlobStoreContext.unwrap();
    }

    public <A extends Closeable> A unwrapApi(Class<A> aClass) {
        return wrappedBlobStoreContext.unwrapApi(aClass);
    }

    public BlobStoreContext getWrappedBlobStoreContext() {
        return wrappedBlobStoreContext;
    }
}
