# Spring Boot - Spring Kafka - Apache Kafka - Apache Avro - Confluent Cloud example

Example project by blog post: https://medium.com/@TimvanBaarsen/maven-cheat-sheet-45942d8c0b86

* [Apache Kafka](https://kafka.apache.org/)
* [Apache Avro](https://avro.apache.org/)
* [Confluent Cloud](https://confluent.cloud/)
* [Confluent Schema Registry](https://docs.confluent.io/current/schema-registry/using.html)
* [Spring Boot 2.2](https://spring.io/projects/spring-boot)
* [Spring Kafka](https://spring.io/projects/spring-kafka)

## Project

### Overview

| Application & modules                         | Port | Avro  | Topic(s)         | Description                                                            |
|-----------------------------------------------|------|-------|------------------|------------------------------------------------------------------------|
| stock-tick-avro-model                         | -    | YES   | -                | Holds the Avro schema and generated Java code based on the Avro schema |
| stock-tick-consumer-avro                      | 8082 | YES   | stock-ticks-avro | Simple consumer of stock ticks using Apache Avro                       |
| stock-tick-producer-avro                      | 8080 | YES   | stock-ticks-avro | Simple producer of stock ticks using Apache Avro                       |

| Application & modules         | Port |
|-------------------------------|------|
| Kafka                         | 9092 |
| Zookeeper                     | 2181 |
| Confluent Schema Registry     | 8081 | 

### Build the project

```
./mvnw clean package
```

### Environment variables

Set these environment variables:

Kafka cluster: 

* CLUSTER_BOOTSTRAP_SERVERS: Your Confluent Cloud Kafka bootstrap server
* CLUSTER_API_KEY: Your Confluent Cloud Kafka Cluster API key
* CLUSTER_API_SECRET: Your Confluent Cloud Kafka Cluster API secret

Schema Registry

* SR_URL: Your Confluent Cloud Schema Registry instance URL
* SR_API_KEY: Your Confluent Cloud Schema Registry API key
* SR_API_SECRET: Your Confluent Cloud Schema Registry API secret

Example:

```
CLUSTER_BOOTSTRAP_SERVERS=your.kafka.bootstrap.server.confluent.cloud:9092
CLUSTER_API_KEY=kafka-cluster-api-key
CLUSTER_API_SECRET=kafka-cluster-api-secret

SR_URL=https://your.schema.registry.instance.confluent.cloud
SR_API_KEY=schema-registry-key
SR_API_SECRET=schema-registry-secret

export CLUSTER_BOOTSTRAP_SERVERS
export CLUSTER_API_KEY
export CLUSTER_API_SECRET

export SR_URL
export SR_API_KEY
export SR_API_SECRET
```

### Spring profile 

spring profile name: `confluent-cloud`

### Running the application

#### Spring Boot Executable jar 

Producer: 

```
java -jar stock-tick-producer-avro/target/stock-tick-producer-avro-0.0.1-SNAPSHOT.jar --spring.profiles.active=confluent-cloud
```  

Consumer:

```
java -jar stock-tick-consumer-avro/target/stock-tick-consumer-avro-0.0.1-SNAPSHOT.jar --spring.profiles.active=confluent-cloud 
```

#### From Maven

Producer: 

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=confluent-cloud -pl :stock-tick-producer-avro
```

Consumer:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=confluent-cloud -pl :stock-tick-consumer-avro
```

### Encrypt your secret properties

Make sure to install [Spring Cloud CLI](https://cloud.spring.io/spring-cloud-cli/reference/html/)
and come up with an encrypt key. 

Encrypt a secret syntax:

```
spring encrypt <secret> --key ENCRYPT_KEY
```

Decrypt a secret syntax:

```
spring decrypt --key ENCRYPT_KEY <secret>
```

Example to encrypt a secret the an encrypt key: 

```
spring encrypt myKafkaClusterApi$ecret --key foo
```

Encrypted properties in environment variables:

```
ENCRYPT_KEY=verySecretEncryptKey;

CLUSTER_BOOTSTRAP_SERVERS=kafka.cluster:9092
CLUSTER_API_KEY={cipher}encrypted-kafka-cluster-api-key
CLUSTER_API_SECRET={cipher}encrypted-kafka-cluster-api-secret

SR_URL=https://your.instance.confluent.cloud
SR_API_KEY={cipher}encrypted-schema-registry-key
SR_API_SECRET={cipher}encrypted-schema-registry-secret
```

## Run the complete project on your local machine (without Confluent Cloud)

To start up a local Kafka broker and Confluent Schema Registry run: 

```
docker-compose up -d
```

Note we run the Spring Boot applications without Spring profile so it will default to local running Kafka cluster (single Kafka broker) and Confluent Schema Registry.

Producer:

```
java -jar stock-tick-producer-avro/target/stock-tick-producer-avro-0.0.1-SNAPSHOT.jar
```  

Consumer:

```
java -jar stock-tick-consumer-avro/target/stock-tick-consumer-avro-0.0.1-SNAPSHOT.jar
```

