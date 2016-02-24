package com.orange.spring.cloud.connector.s3.core.creator;

import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStore;
import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStoreContext;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.filesystem.reference.FilesystemConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class JcloudsWrapperTest {
    private final static String bucketName = "myfakebucket";
    private final static String fileName = "testFile.txt";
    private final static String fileContent = "test data";

    private SpringCloudBlobStoreContext springCloudBlobStoreContext;
    private File testFile;

    @Before
    public void init() {
        testFile = new File(System.getProperty("java.io.tmpdir") + "/" + bucketName + "/" + fileName);
        Properties properties = new Properties();
        properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, testFile.getParentFile().getParent());
        BlobStoreContext context = ContextBuilder.newBuilder("filesystem")
                .overrides(properties)
                .buildView(BlobStoreContext.class);
        this.springCloudBlobStoreContext = new SpringCloudBlobStoreContext(context, bucketName);
        BlobStore blobStore = this.springCloudBlobStoreContext.getBlobStore();
        blobStore.createContainerInLocation(null, bucketName);
    }

    @Test
    public void putBlob() throws IOException {
        SpringCloudBlobStore blobStore = this.springCloudBlobStoreContext.getSpringCloudBlobStore();
        Blob blob = blobStore.blobBuilder(fileName).build();
        blob.setPayload(fileContent);
        blobStore.putBlob(blob);
        this.assertFromFile();
    }

    @Test
    public void getBlob() throws IOException {
        this.putBlob();
        SpringCloudBlobStore blobStore = this.springCloudBlobStoreContext.getSpringCloudBlobStore();
        Blob blob = blobStore.getBlob(fileName);
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(blob.getPayload().openStream()));
        String content = "";
        while ((line = br.readLine()) != null) {
            content += line;
        }
        assertEquals(fileContent, content);
    }

    public void assertFromFile() throws IOException {
        assertTrue(this.testFile.isFile());
        String content = new String(Files.readAllBytes(Paths.get(testFile.toURI())));
        assertEquals(fileContent, content);
    }

    @After
    public void cleaning() {
        BlobStore blobStore = this.springCloudBlobStoreContext.getBlobStore();
        blobStore.deleteContainer(bucketName);
    }

}
