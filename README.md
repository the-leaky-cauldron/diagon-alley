# Diagon Alley

## Overview

Diagon Alley is a magical marketplace application inspired by the famous shopping district from the wizarding world. This Spring Boot application enables users to browse, sell, and purchase magical items and supplies.

## Features

- **User Authentication**: Secure login using Spring Security
- **Product Catalog**: Browse various magical items by category
- **Shopping Cart**: Add items and proceed to checkout
- **Search Functionality**: Find specific items quickly

## Installation

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6+ or Gradle 7.0+
- PostgreSQL database, Elastic Search, Kafka

### Steps

1. Clone the repository:

   ```sh
   git clone https://github.com/the-leaky-cauldron/diagon-alley
   cd diagon-alley
   ```

2. Install Kafka ─ I have followed this [link](https://hub.docker.com/r/bitnami/kafka) to install kafka in docker

    ```sh
    docker run -d --name kafka-server \
      -p 9092:9092 --network app-tier \
      -e KAFKA_CFG_NODE_ID=0 \
      -e KAFKA_CFG_PROCESS_ROLES=controller,broker \
      -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
      -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
      -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
      -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka-server:9093 \
      -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
      bitnami/kafka:latest
    ```

3. Install Postgres ─ I have ran this docker-compose file to spin up ```Postgres``` and ```PgAdmin```

    ```yml
      services:
      postgresdb:
        container_name: postgrescontainer
        image: postgres:16.1
        restart: always
        environment:
          POSTGRES_USER: ${POSTGRES_USER}
          POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
          POSTGRES_DB: ${POSTGRES_DB}
        expose:
          - 5432
        ports:
          - 5432:5432
        volumes:
          - postgresvolume:/var/lib/postgresql/data
          - ./schema.sql:/docker-entrypoint-initdb.d/schema.sql

      pgadmin:
        container_name: pgadmincontainer
        image: dpage/pgadmin4:latest
        restart: always
        environment:
          PGADMIN_DEFAULT_EMAIL: ${PGADMIN_EMAIL}
          PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_PASSWORD}
          PGADMIN_DEFAULT_ADDRESS: 6000
          PGADMIN_LISTEN_PORT: 6000
        expose:
          - 6000
        ports:
          - 7000:6000
        volumes:
          - pgadminvolume:/var/lib/pgadmin

      volumes:
      pgadminvolume:
      postgresvolume:
    ```

4. Install Elastic search ─ Follow this [guide](https://www.elastic.co/guide/en/elasticsearch/reference/current/run-elasticsearch-locally.html)

5. Configure database connection in `src/main/resources/application.properties` or `application.yml`

6. Configure environmental variables:

   The application requires the following environmental variables:
   - `ES_USER`: Elasticsearch username
   - `ES_PASS`: Elasticsearch password
   - `DB_USER`: PostgreSQL database username
   - `DB_PASS`: PostgreSQL database password

   **For Linux/macOS:**

   ```sh
   export ES_USER=your_elasticsearch_username
   export ES_PASS=your_elasticsearch_password
   export DB_USER=your_database_username
   export DB_PASS=your_database_password
   ```

   **For Windows Command Prompt:**

   ```cmd
   set ES_USER=your_elasticsearch_username
   set ES_PASS=your_elasticsearch_password
   set DB_USER=your_database_username
   set DB_PASS=your_database_password
   ```

   **For Windows PowerShell:**

   ```powershell
   $env:ES_USER="your_elasticsearch_username"
   $env:ES_PASS="your_elasticsearch_password"
   $env:DB_USER="your_database_username"
   $env:DB_PASS="your_database_password"
   ```

7. Build the application:

   ```sh
   # If using Maven
   mvn clean install
   
   # If using Gradle
   ./gradlew build
   ```

8. Run the application:

   ```sh
   # If using Maven
   mvn spring-boot:run
   
   # If using Gradle
   ./gradlew bootRun
   ```

## Technologies Used

- **Backend**: Java, Spring Boot, Spring MVC, Spring Data JPA
- **Database**: PostgreSQL, ElasticSearch
- **Authentication**: Spring Security
- **Testing**: JUnit, Mockito, Spring Test
- **Build Tool**: Maven/Gradle
- **Deployment**: Docker
- **Other Tools**: Kafka

## API Documentation

API endpoints are documented using Swagger/OpenAPI. After starting the server, visit `/swagger-ui.html` to view the documentation.

## Acknowledgments

- Inspired by J.K. Rowling's Harry Potter series
- Special thanks to all contributors
- Scaler Academy for project guidance
- Spring Boot and related open-source communities

## Contact

- Project Maintainer: [Vijaysurya Mandala](mailto:mandala.vijay.surya@gamil.com)
- Project Repository: [GitHub](https://github.com/the-leaky-cauldron/diagon-alley)
- Issue Tracker: [GitHub Issues](https://github.com/the-leaky-cauldron/diagon-alley/issues)
