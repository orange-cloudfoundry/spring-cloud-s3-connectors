# Spring-cloud-s3-connectors
[![Build Status](https://travis-ci.org/Orange-OpenSource/spring-cloud-s3-connectors.svg)](https://travis-ci.org/Orange-OpenSource/spring-cloud-s3-connectors)
[![Apache Version 2 Licence](http://img.shields.io/:license-Apache%20v2-blue.svg)](LICENSE)
[ ![Download](https://api.bintray.com/packages/elpaaso/maven/spring-cloud-s3-connectors/images/download.svg) ](https://bintray.com/elpaaso/maven/spring-cloud-s3-connectors/_latestVersion)


[![Join the chat at https://gitter.im/Orange-OpenSource/elpaaso](https://img.shields.io/badge/gitter-join%20chat%20→-brightgreen.svg)](https://gitter.im/Orange-OpenSource/elpaaso?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Spring cloud s3 service connectors to use s3 in CloudFoundry, Heroku or Local Config.

**You can find an example app here: https://github.com/Orange-OpenSource/s3-connectors-poc**

## Getting started

### Import repositories to your project

**Maven**:

```xml
<repositories>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-elpaaso-maven</id>
        <name>bintray</name>
        <url>http://dl.bintray.com/elpaaso/maven</url>
    </repository>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>central</id>
        <name>bintray</name>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```

**Gradle**:

```gradle
repositories {
    // ...
    jcenter()
    maven { url "http://dl.bintray.com/elpaaso/maven" }
}
```

### Add a connector to your project

First, make a version propertie:

**Maven**:

```xml
<properties>
    <s3.connectors.version>2.0.15</s3.connectors.version>
</properties>
```

**Gradle**:

```gradle
ext {
	s3ConnectorsVersion = "2.0.15"
}
```

#### Cloud Foundry

**Maven**:

```xml
<dependency>
    <groupId>com.orange.spring.cloud.connectors</groupId>
    <artifactId>spring-cloud-s3-connectors-cloudfoundry</artifactId>
    <version>${s3.connectors.version}</version>
</dependency>
```

**Gradle**:

```gradle
dependencies {
  compile("com.orange.spring.cloud.connectors:spring-cloud-s3-connectors-cloudfoundry:$s3ConnectorsVersion")
}
```

#### Local Config

**Maven**:

```xml
<dependency>
    <groupId>com.orange.spring.cloud.connectors</groupId>
    <artifactId>spring-cloud-s3-connectors-localconfig</artifactId>
    <version>${s3.connectors.version}</version>
</dependency>
```

**Gradle**:

```gradle
dependencies {
  compile("com.orange.spring.cloud.connectors:spring-cloud-s3-connectors-localconfig:$s3ConnectorsVersion")
}
```

#### Heroku

**Maven**:

```xml
<dependency>
    <groupId>com.orange.spring.cloud.connectors</groupId>
    <artifactId>spring-cloud-s3-connectors-heroku</artifactId>
    <version>${s3.connectors.version}</version>
</dependency>
```

**Gradle**:

```gradle
dependencies {
  compile("com.orange.spring.cloud.connectors:spring-cloud-s3-connectors-heroku:$s3ConnectorsVersion")
}
```

### Use it !

Here the bootstrap for your spring boot app to get a `SpringCloudBlobStore` which will help you to manipulate your s3 bucket:

```java
@Configuration
@ServiceScan
public class S3Config extends AbstractCloudConfig {

    @Autowired
    private SpringCloudBlobStoreContext springCloudBlobStoreContext;

    @Bean
    public SpringCloudBlobStore blobStore() {
        return this.springCloudBlobStoreContext.getSpringCloudBlobStore();
    }
}
```

Usage example:

```java
@Component
public class ExampleS3 {

    @Autowired
    @Qualifier(value = "blobStore")
    protected SpringCloudBlobStore blobStore;

    private final static String myfile = "mysuperfile.txt";

    public void pushData(){
        Blob blob = this.blobStore.blobBuilder(myfile).build();
        blob.setPayload("test data");
        blobStore.putBlob(blob);
        // this will push 'test data' with the filename *mysuperfile.txt* to your connected s3
    }

    public void retrieveData(){
        Blob blob = blobStore.getBlob(fileName);
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(blob.getPayload().openStream()));
        String content = "";
        while ((line = br.readLine()) != null) {
            content += line;
        } // <- this will retrieve the content of the file *mysuperfile.txt* from your connected s3 (the content is 'test data' )
    }
}
```

### Deploy and run

#### Cloud Foundry

1. Create a s3 service from the marketplace (e.g with p-riakcs: `cf cs p-riakcs developper nameofmyservice`)
2. Push your app with `cf push`
3. After the app has been pushed bind your new created service to your app (e.g: `cf bs nameofmyapp nameofmyservice`)
4. Restage your app: `cf restage nameofmyapp`

#### Heroku

1. Create and push your app on heroku
2. To use s3 connectors you have three ways
 - Use the [Bucketeer addon](https://elements.heroku.com/addons/bucketeer)
 - Use the native support of S3 on heroku: https://devcenter.heroku.com/articles/s3
 - Set an env var called `S3_URL` with the address of your s3 bucket (e.g: `S3_URL=s3://myaccesskey:mysecretaccesskey@s3.host.com/mybucketname`)
3. Restart your app: `heroku restart`

#### Locally

1. Follow the tutorial made by spring to create the property file: http://cloud.spring.io/spring-cloud-connectors/spring-cloud-connectors.html#_local_configuration_connector
2. Add to your property file or system properties a key/value pair with this form: `spring.cloud.myServiceS3Name=s3://myaccesskey:mysecretaccesskey@s3.host.com/mybucketname` (`myServiceS3Name` can be replace to what you want only the scheme `s3` is required in value)


## Examples

You can find an example app here: https://github.com/Orange-OpenSource/s3-connectors-poc

## Use HTTP Proxy

In order to use HTTP Proxy you can set these JVM properties:

- **http.proxyHost**: host of the proxy
- **http.proxyPort**: port of the proxy
- **http.proxyUsername** (Optional): User name to connect to the proxy
- **http.proxyPassword** (Optional): Password to connect to the proxy
- **skip.ssl.verification** (Optional): If set to true it will trust all certificates

## Contributing

Report any issues or pull request on this repo.

Feedbacks for heroku are really welcome.

