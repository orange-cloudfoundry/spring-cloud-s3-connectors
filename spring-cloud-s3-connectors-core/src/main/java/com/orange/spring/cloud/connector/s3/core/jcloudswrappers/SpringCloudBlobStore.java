package com.orange.spring.cloud.connector.s3.core.jcloudswrappers;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.*;
import org.jclouds.blobstore.options.CopyOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.domain.Location;

import java.util.Set;

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
public class SpringCloudBlobStore {
    private BlobStore wrappedBlobStore;
    private String bucketName;
    private SpringCloudBlobStoreContext springCloudBlobStoreContext;

    public SpringCloudBlobStore(BlobStore wrappedBlobStore, String bucketName, SpringCloudBlobStoreContext springCloudBlobStoreContext) {
        this.wrappedBlobStore = wrappedBlobStore;
        this.bucketName = bucketName;
        this.springCloudBlobStoreContext = springCloudBlobStoreContext;
    }


    public SpringCloudBlobStoreContext getContext() {
        return this.springCloudBlobStoreContext;
    }

    public BlobBuilder blobBuilder(String name) {
        return wrappedBlobStore.blobBuilder(name);
    }

    public Set<? extends Location> listAssignableLocations() {
        return wrappedBlobStore.listAssignableLocations();
    }

    public PageSet<? extends StorageMetadata> list() {
        return wrappedBlobStore.list(this.bucketName);
    }

    public boolean containerExists() {
        return wrappedBlobStore.containerExists(this.bucketName);
    }

    public ContainerAccess getContainerAccess() {
        return wrappedBlobStore.getContainerAccess(this.bucketName);
    }

    public void setContainerAccess(ContainerAccess access) {
        wrappedBlobStore.setContainerAccess(this.bucketName, access);
    }

    public PageSet<? extends StorageMetadata> list(ListContainerOptions options) {
        return wrappedBlobStore.list(this.bucketName, options);
    }

    public void clearContainer() {
        wrappedBlobStore.clearContainer(this.bucketName);
    }

    public void clearContainer(ListContainerOptions options) {
        wrappedBlobStore.clearContainer(this.bucketName, options);
    }

    public void deleteContainer() {
        wrappedBlobStore.deleteContainer(this.bucketName);
    }

    public boolean deleteContainerIfEmpty() {
        return wrappedBlobStore.deleteContainerIfEmpty(this.bucketName);
    }

    public boolean directoryExists(String directory) {
        return wrappedBlobStore.directoryExists(this.bucketName, directory);
    }

    public void createDirectory(String directory) {
        wrappedBlobStore.createDirectory(this.bucketName, directory);
    }

    public void deleteDirectory(String name) {
        wrappedBlobStore.deleteDirectory(this.bucketName, name);
    }

    public boolean blobExists(String name) {
        return wrappedBlobStore.blobExists(this.bucketName, name);
    }

    public String putBlob(Blob blob) {
        return wrappedBlobStore.putBlob(this.bucketName, blob);
    }

    public String putBlob(Blob blob, PutOptions options) {
        return wrappedBlobStore.putBlob(this.bucketName, blob, options);
    }

    public String copyBlob(String fromName, String toName, CopyOptions options) {
        return wrappedBlobStore.copyBlob(this.bucketName, fromName, this.bucketName, toName, options);
    }

    public BlobMetadata blobMetadata(String name) {
        return wrappedBlobStore.blobMetadata(this.bucketName, name);
    }

    public Blob getBlob(String name) {
        return wrappedBlobStore.getBlob(this.bucketName, name);
    }

    public Blob getBlob(String name, GetOptions options) {
        return wrappedBlobStore.getBlob(this.bucketName, name, options);
    }

    public void removeBlob(String name) {
        wrappedBlobStore.removeBlob(this.bucketName, name);
    }

    public void removeBlobs(Iterable<String> names) {
        wrappedBlobStore.removeBlobs(this.bucketName, names);
    }

    public BlobAccess getBlobAccess(String name) {
        return wrappedBlobStore.getBlobAccess(this.bucketName, name);
    }

    public void setBlobAccess(String name, BlobAccess access) {
        wrappedBlobStore.setBlobAccess(this.bucketName, name, access);
    }

    public long countBlobs() {
        return wrappedBlobStore.countBlobs(this.bucketName);
    }

    public long countBlobs(ListContainerOptions options) {
        return wrappedBlobStore.countBlobs(this.bucketName, options);
    }

    public BlobStore getWrappedBlobStore() {
        return wrappedBlobStore;
    }

    public BlobStoreContext getWrappedBlobStoreContext() {
        return wrappedBlobStore.getContext();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
