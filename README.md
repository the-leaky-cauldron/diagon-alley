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
   git clone https://github.com/yourusername/diagon-alley.git
   cd diagon-alley
   ```

2. Install Kafka ─ I have followed this [link](https://hub.docker.com/r/bitnami/kafka) to install kafka in docker

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

## Project Structure

```

diagon-alley/
├── src/
│ ├── main/
│ │ ├── java/ # Java source files
│ │ │ ├── cartservice/ # Cart service of DiagonAlley
│ │ │ │ ├── controllers/ # Controllers of Cart service
│ │ │ │ ├── dto/ # DTO(s) of Cart service
│ │ │ │ ├── entities/ # Entities of Cart service
│ │ │ │ ├── repositories/ # Repositories of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ ├── commons/ # Common files of DiagonAlley
│ │ │ │ ├── annotations/ # Annotations of DiagonAlley
│ │ │ │ ├── basemodels/ # Basemodels of DiagonAlley
│ │ │ │ ├── configuration/ # Bean configuration of DiagonAlley
│ │ │ │ ├── generators/ # Generation Types of DiagonAlley
│ │ │ │ ├── utils/ # Utility classes for DiagonAlley
│ │ │ ├── configurations/ # Configuration files of DiagonAlley
│ │ │ ├── dtos/ # DTO(s) of DiagonAlley
│ │ │ ├── orderservice/ # Order service of DiagonAlley
│ │ │ │ ├── dtos/ # Service layer of Order service
│ │ │ │ ├── entities/ # Service layer of Order service
│ │ │ │ ├── repositories/ # Service layer of Order service
│ │ │ │ ├── services/ # Service layer of Order service
│ │ │ │ ├── utils/ # Service layer of Order service
│ │ │ ├── outboxservice/ # Outbox service of DiagonAlley
│ │ │ │ ├── controlleradvices/ # Controller Advices of Outbox service
│ │ │ │ ├── dtos/ # DTO(s) of Outbox service
│ │ │ │ ├── entities/ #  Entities of Outbox service
│ │ │ │ ├── exceptions/ # Exceptions of Outbox service
│ │ │ │ ├── listeners/ # Listeners of Outbox service
│ │ │ │ ├── repositories/ # Repository layer of Outbox service
│ │ │ │ ├── services/ # Service layer of Outbox service
│ │ │ ├── paymentservice/ # Payment service of DiagonAlley
│ │ │ │ ├── controllers/ # Controller layer of Payment service
│ │ │ │ ├── dtos/ # DTO(s) of Payment service
│ │ │ │ ├── enums/ # Enums of Payment service
│ │ │ │ ├── services/ # Service layer of Payment service
│ │ │ ├── productservice/ # Product service of DiagonAlley
│ │ │ │ ├── configurations/ #  Configurations of Cart service
│ │ │ │ ├── controllers/ # Controller layers of Cart service
│ │ │ │ ├── dtos/ # DTO(s) of Cart service
│ │ │ │ ├── entities/ # Entities of Cart service
│ │ │ │ ├── exceptions/ # Exceptions of Cart service
│ │ │ │ ├── repositories/ # Repository of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ │ ├── validation/ # Validations of Cart service
│ │ │ ├── security/ # Security files of DiagonAlley
│ │ │ │ ├── filters/ # Filters of DiagonAlley
│ │ │ └── DiagonAlleyApplication.java # Main class
│ │ └── resources/ # Configuration files and static resources
│ │ ├── static/
│ │ ├── templates/
│ │ └── application.properties # Application configuration
│ └── test/ # Test classes
├── pom.xml # Maven configuration
└── README.md # This file
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

 Contact
- Project Maintainer: [Vijaysurya Mandala](mailto:mandala.vijay.surya@gamil.com)
- Project Repository: [GitHub](https://github.com/the-leaky-cauldron/diagon-alley)
- Issue Tracker: [GitHub Issues](https://github.com/the-leaky-cauldron/diagon-alley/issues)