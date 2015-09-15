# Cloudfoundry-s3-service-connector
A cloudfoundry s3 service connector for spring which working great with riakcs. 

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
	    <version>0.1.0</version>
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
	        compile 'com.github.Orange-OpenSource:cloudfoundry-s3-service-connector:0.1.0'
	}
```


## Exemple for spring configuration

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