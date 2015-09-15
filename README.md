# Cloudfoundry-s3-service-connector
A cloudfoundry s3 service connector for spring which working great with riakcs. 


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