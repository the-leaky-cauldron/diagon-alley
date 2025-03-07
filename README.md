# Diagon Alley

## Overview
Diagon Alley is a magical marketplace application inspired by the famous shopping district from the wizarding world. This Spring Boot application enables users to browse, sell, and purchase magical items and supplies.

## Features
- **User Authentication**: Secure login and registration system using Spring Security
- **Product Catalog**: Browse various magical items by category
- **Shopping Cart**: Add items and proceed to checkout
- **User Profiles**: Manage account details and view order history
- **Search Functionality**: Find specific items quickly
- **Admin Dashboard**: Manage products, users, and orders

## Installation

### Prerequisites
- Java JDK 11 or higher
- Maven 3.6+ or Gradle 7.0+
- MySQL/PostgreSQL database

### Steps
1. Clone the repository:
   ```
   git clone https://github.com/yourusername/diagon-alley.git
   cd diagon-alley
   ```

2. Configure database connection in `src/main/resources/application.properties` or `application.yml`

3. Build the application:
   ```
   # If using Maven
   mvn clean install
   
   # If using Gradle
   ./gradlew build
   ```

4. Run the application:
   ```
   # If using Maven
   mvn spring-boot:run
   
   # If using Gradle
   ./gradlew bootRun
   ```

## Project Structure

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
│ │ │ │ ├── dtos/ # Service layer of Cart service
│ │ │ │ ├── entities/ # Service layer of Cart service
│ │ │ │ ├── repositories/ # Service layer of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ │ ├── utils/ # Service layer of Cart service
│ │ │ ├── outboxservice/ # Outbox service of DiagonAlley
│ │ │ │ ├── controlleradvices/ # Service layer of Cart service
│ │ │ │ ├── dtos/ # Service layer of Cart service
│ │ │ │ ├── entities/ # Service layer of Cart service
│ │ │ │ ├── exceptions/ # Service layer of Cart service
│ │ │ │ ├── listeners/ # Service layer of Cart service
│ │ │ │ ├── repositories/ # Service layer of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ ├── paymentservice/ # Payment service of DiagonAlley
│ │ │ │ ├── controllers/ # Service layer of Cart service
│ │ │ │ ├── dtos/ # Service layer of Cart service
│ │ │ │ ├── enums/ # Service layer of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ ├── productservice/ # Product service of DiagonAlley
│ │ │ │ ├── configurations/ # Service layer of Cart service
│ │ │ │ ├── controllers/ # Service layer of Cart service
│ │ │ │ ├── dtos/ # Service layer of Cart service
│ │ │ │ ├── entities/ # Service layer of Cart service
│ │ │ │ ├── exceptions/ # Service layer of Cart service
│ │ │ │ ├── repositories/ # Service layer of Cart service
│ │ │ │ ├── services/ # Service layer of Cart service
│ │ │ │ ├── validation/ # Service layer of Cart service
│ │ │ ├── security/ # Security related file of DiagonAlley
│ │ │ │ ├── filters/ # Service layer of Cart service
│ │ │ └── DiagonAlleyApplication.java # Main class
│ │ └── resources/ # Configuration files and static resources
│ │ ├── static/
│ │ ├── templates/
│ │ └── application.properties # Application configuration
│ └── test/ # Test classes
├── pom.xml # Maven configuration
└── README.md # This file

## Technologies Used

- **Backend**: Java, Spring Boot, Spring MVC, Spring Data JPA
- **Database**: PostgreSQL, ElasticSearch
- **Authentication**: Spring Security
- **Testing**: JUnit, Mockito, Spring Test
- **Build Tool**: Maven/Gradle
- **Deployment**: Docker

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