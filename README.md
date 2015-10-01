# Cloudfoundry-s3-service-connector
[![Build Status](https://travis-ci.org/Orange-OpenSource/cloudfoundry-s3-service-connector.svg)](https://travis-ci.org/Orange-OpenSource/cloudfoundry-s3-service-connector)

A Cloud Foundry s3 service connector for spring which works great with riakcs. 

## Import in your project

### Maven

 **Step 1:** Add the JitPack repository to your build file 

```xml
    <repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
```

 **Step 2:** Add the dependency in the form
 
```xml
	<dependency>
	    <groupId>com.github.Orange-OpenSource</groupId>
	    <artifactId>cloudfoundry-s3-service-connector</artifactId>
	    <version>1.0.0</version>
	</dependency>
```

### Gradle
 **Step 1:** Add it in your build.gradle at the end of repositories
 
```gradle
 repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
```

 **Step 2:** Add the dependency in the form
 
```gradle
	dependencies {
	        compile 'com.github.Orange-OpenSource:cloudfoundry-s3-service-connector:1.0.0'
	}
```


## How to use

### Example app
Have a look to [https://github.com/Orange-OpenSource/cloudfoundry-poc-s3](https://github.com/Orange-OpenSource/cloudfoundry-poc-s3) it will show you how to send and retrieve file with this connector to an S3 storage. 

### Bootstrap
```java
@Configuration
@Profile(value = "cloud")
@ServiceScan
public class CloudConfig extends AbstractCloudConfig {
    @Autowired
    private S3ContextBuilder s3ContextBuilder;

    @Bean
    public String bucketName() {
        return this.s3ContextBuilder.getBucketName();
    }

    /**
     * blobstore context from jcloud see: https://jclouds.apache.org/guides/aws/ to know how to use
     */
    @Bean
    public BlobStoreContext blobStoreContext() {
        return this.s3ContextBuilder.getContextBuilder().buildView(BlobStoreContext.class);
    }
}
```
