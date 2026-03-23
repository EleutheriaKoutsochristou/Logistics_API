# How to Run the Logistics API

This guide will walk you through the steps to get the Logistics API up and running on your local machine.

## Prerequisites

Before you begin, ensure you have the following installed on your system:
- **Java Development Kit (JDK) 17**: The project requires Java 17.
- **Docker & Docker Compose**: Used to run the PostgreSQL database.
- **Maven**: The build tool used for this project (or you can use the included Maven wrapper `mvnw`).

## 1. Start the Database

The project includes a `docker-compose.yml` file to easily spin up the required PostgreSQL database and pgAdmin for database management.

1. Open a terminal and navigate to the root directory of the project:
   ```bash
   cd C:/Users/riaek/IdeaProjects/Logistics_API
   ```
2. Start the database containers in the background:
   ```bash
   docker-compose up -d
   ```
   This will start:
   - **PostgreSQL**: Running on `localhost:5432` with user `myappuser`, password `myapppassword`, and database `myappdb`. It will also run any initialization scripts like `init.sql`.
   - **pgAdmin**: Available at `http://localhost:5050` (Login: `admin@local.com` / `admin`).

## 2. Configure the Application (If necessary)

By default, the application is configured to connect to a specific database IP (`172.24.44.59`). If you are running the database locally via Docker as described above, you will need to update the `application.properties` file located at `src/main/resources/application.properties` to point to localhost:

**Change:**
```properties
spring.datasource.url=jdbc:postgresql://172.24.44.59:5432/myappdb
```
**To:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/myappdb
```

## 3. Build and Run the Application

You can use the provided Maven wrapper to build and run the application without needing Maven installed globally.

1. **Clean and install dependencies (optional but recommended):**
   ```bash
   ./mvnw clean install
   ```
   *(On Windows Command Prompt, use `mvnw.cmd clean install`)*

2. **Run the Spring Boot application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   *(On Windows Command Prompt, use `mvnw.cmd spring-boot:run`)*

The application will start on port `8081` (as configured in `application.properties`).

## 4. Test the API

Once the application is running, you can test it using the provided `API_TESTS.http` file (if you use IntelliJ IDEA) or by using tools like `cURL` or Postman.

### Example: Create an Order
```bash
curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "weight": 10.5,
    "destination": "New York",
    "shippingType": "STANDARD"
  }'
```

## Stopping the Application

- To stop the Spring Boot application, press `Ctrl + C` in the terminal where it's running.
- To stop the database containers, run:
  ```bash
  docker-compose down
  ```
