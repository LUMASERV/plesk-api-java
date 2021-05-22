# plesk-api-java
A Java Client for Plesk's XML API

## Available Features
- Customers
- Webspaces (Subscriptions)

## Maven
```xml
<repository>
    <id>lumaserv</id>
    <url>https://maven.lumaserv.cloud</url>
</repository>
```
```xml
<dependency>
    <groupId>com.lumaserv</groupId>
    <artifactId>plesk-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage
```java
PleskAPI api = new PleskAPI("example.com", "admin", "changeme");
int id = api.customer().create(new CreateCustomerRequest()
  .setName("John Doe")
  .setEmail("john@doe.com")
  .setLogin("john")
  .setPassword("p4ssw0rd")
);
```
