# Logistics API - Complete Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Architecture](#project-architecture)
4. [Directory Structure](#directory-structure)
5. [Core Components](#core-components)
6. [API Endpoints](#api-endpoints)
7. [Database Schema](#database-schema)
8. [Configuration](#configuration)
9. [Design Patterns](#design-patterns)

---

## Project Overview

**Logistics_API** is a Spring Boot REST API application designed to manage logistics operations including order processing, vehicle management, and shipping cost calculations. The application handles multiple shipping types (Standard, Express, Free) with different pricing strategies and supports various vehicle types (Truck, Van, Drone) for delivery.

### Key Features
- Create and process customer orders
- Calculate shipping costs based on weight and shipping type
- Bind vehicles to orders for delivery
- Track order status (PENDING, DELAYED, READY, FAILED)
- Analytics for orders including revenue, delayed orders, and expensive orders
- Asynchronous order processing
- Factory pattern for vehicle creation
- Strategy pattern for pricing calculations

---

## Technology Stack

- **Java Version**: 17
- **Framework**: Spring Boot 3.2.1
- **Build Tool**: Maven
- **ORM**: Hibernate JPA
- **Database**: PostgreSQL
- **Database Driver**: PostgreSQL JDBC Driver
- **Utility**: Lombok (for reducing boilerplate code)
- **Additional**: Spring Boot Web, Spring Boot Data JPA, Async Processing

### Key Dependencies (from pom.xml)
```xml
- spring-boot-starter-web (3.2.1)
- spring-boot-starter-data-jpa (3.2.1)
- spring-boot-starter-parent (3.2.1)
- postgresql driver (runtime)
- lombok (optional)
```

---

## Project Architecture

### Layered Architecture Pattern
The project follows a traditional 3-tier architecture:

```
┌─────────────────────────────┐
│      Controllers Layer       │
│  (Request/Response Handler)  │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│      Services Layer         │
│   (Business Logic)          │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│    Repository Layer         │
│  (Database Operations)      │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│    Entity/Domain Models     │
│   (Database Tables)         │
└─────────────────────────────┘
```

---

## Directory Structure

```
Logistics_API/
├── src/main/java/com/example/logistics_api/
│   ├── LogisticsApiApplication.java         (Main Application Entry Point)
│   ├── Config/                              (Configuration Classes)
│   │   ├── AsyncConfig.java                 (Async Processing Configuration)
│   │   └── SecurityConfig.java              (Security Configuration - Empty)
│   │
│   ├── Controller/                          (REST Controllers)
│   │   ├── OrderController.java             (Order API Endpoints)
│   │   ├── VehicleController.java           (Vehicle API Endpoints)
│   │   └── AnalyticsController.java         (Analytics API Endpoints)
│   │
│   ├── Service/                             (Business Logic)
│   │   ├── OrderService.java                (Order Processing Logic)
│   │   ├── VehicleService.java              (Vehicle Management Logic)
│   │   ├── ShippingCostService.java         (Shipping Cost Calculation)
│   │   └── AnalyticsService.java            (Analytics Queries)
│   │
│   ├── Repository/                          (Database Access)
│   │   ├── OrderRepository.java             (Order Data Access)
│   │   ├── VehicleRepository.java           (Vehicle Data Access)
│   │   └── AnalyticsRepository.java         (Analytics Data Access - Empty)
│   │
│   ├── Entity/                              (JPA Entities/Domain Models)
│   │   ├── Order.java                       (Order Entity)
│   │   └── Vehicle.java                     (Vehicle Entity)
│   │
│   ├── DTO/                                 (Data Transfer Objects)
│   │   ├── OrderDTO.java                    (Order DTO - Record)
│   │   ├── OrderResponseDTO.java            (Order Response DTO)
│   │   ├── VehicleDTO.java                  (Vehicle DTO - Record)
│   │   └── VehicleRequestDTO.java           (Vehicle Request DTO)
│   │
│   ├── Enumeration/                         (Enum Classes)
│   │   ├── OrderStatus.java                 (Order Status Values)
│   │   ├── ShippingType.java                (Shipping Type Values)
│   │   └── VehicleType.java                 (Vehicle Type Values)
│   │
│   ├── FactoryPattern/                      (Factory Pattern Implementation)
│   │   ├── VehicleFactory.java              (Abstract Factory Base)
│   │   ├── TruckFactory.java                (Truck Factory Implementation)
│   │   ├── VanFactory.java                  (Van Factory Implementation)
│   │   ├── DroneFactory.java                (Drone Factory Implementation)
│   │   └── DeliveryService.java             (Delivery Service - Factory Manager)
│   │
│   └── PricingService/                      (Strategy Pattern for Pricing)
│       ├── ShippingPricing.java             (Pricing Strategy Interface)
│       ├── StandardPricing.java             (Standard Pricing Strategy)
│       ├── ExpressPricing.java              (Express Pricing Strategy)
│       └── FreePricing.java                 (Free Pricing Strategy)
│
└── src/main/resources/
    └── application.properties               (Application Configuration)
```

---

## Core Components

### 1. Entities

#### Order.java
Represents a customer order in the system.

**Attributes:**
- `id` (Integer): Primary key, auto-incremented
- `customerName` (String): Name of the customer (max 128 chars)
- `weight` (double): Weight of the order in kg
- `destination` (String): Delivery destination (max 128 chars)
- `shippingType` (ShippingType enum): Type of shipping (STANDARD, EXPRESS, FREE)
- `status` (OrderStatus enum): Current order status (PENDING, DELAYED, READY, FAILED)
- `cost` (double): Calculated shipping cost

**Key Methods:**
- `DtoToEntity(OrderDTO)`: Converts DTO to Entity

**Database Mapping:**
- Table Name: `orders`
- Auto-increment ID strategy
- Default status: PENDING

---

#### Vehicle.java
Represents a delivery vehicle in the system.

**Attributes:**
- `id` (Integer): Primary key, auto-incremented
- `type` (VehicleType enum): Type of vehicle (TRUCK, VAN, DRONE)
- `licensePlate` (String): Vehicle license plate (unique)
- `capacity` (Double): Vehicle cargo capacity in kg
- `maxSpeed` (Double): Maximum speed of vehicle
- `status` (String): Current status (TRUE/FALSE or "in_service")

**Key Methods:**
- `DtoToVehicle(VehicleDTO)`: Converts DTO to Entity

**Database Mapping:**
- Table Name: `vehicles`
- License plate is unique constraint

---

### 2. DTOs (Data Transfer Objects)

#### OrderDTO (Record)
A record-based DTO for order operations.

```java
record OrderDTO(
    Integer id,
    String customerName,
    Double cost,
    Double weight,
    ShippingType shippingType,
    String destination
)
```

**Methods:**
- `OrdertoDTO(Order)`: Converts Order entity to OrderDTO

---

#### OrderResponseDTO
A class-based DTO for order responses.

**Attributes:**
- `id` (Integer)
- `customerName` (String)
- `cost` (Double)
- `weight` (Double)
- `shippingType` (ShippingType)
- `destination` (String)

**Methods:**
- `fromEntity(Order)`: Converts Order entity to OrderResponseDTO

---

#### VehicleDTO (Record)
A record-based DTO for vehicle operations.

```java
record VehicleDTO(
    Integer id,
    VehicleType type,
    String licensePlate,
    Double capacity,
    Double maxSpeed
)
```

**Methods:**
- `VehicletoDTO(Vehicle)`: Converts Vehicle entity to VehicleDTO
- `GetTypeVehicleDTO()`: Returns a VehicleDTO with only the type field

---

#### VehicleRequestDTO
DTO for vehicle binding requests.

**Attributes:**
- `type` (VehicleType): Type of vehicle requested
- `orderId` (Integer): ID of the order to bind vehicle to

---

### 3. Enumerations

#### OrderStatus.java
Represents the possible states of an order.

**Values:**
- `PENDING`: Order is pending processing
- `DELAYED`: Order processing is delayed
- `READY`: Order is ready for delivery
- `FAILED`: Order processing failed

---

#### ShippingType.java
Represents the types of shipping services available.

**Values:**
- `STANDARD`: Standard shipping (2.5 per kg)
- `EXPRESS`: Express shipping (2.5 × 1.5 per kg = 3.75 per kg)
- `FREE`: Free shipping (limited to 5 kg max)

---

#### VehicleType.java
Represents the types of vehicles available for delivery.

**Values:**
- `TRUCK`: Large truck for bulk deliveries
- `VAN`: Medium van for standard deliveries
- `DRONE`: Drone for fast deliveries

---

### 4. Controllers

#### OrderController.java
Handles order-related HTTP requests.

**Endpoints:**

##### POST /orders
Creates and processes a new order.

**Request Body:**
```json
{
    "customerName": "John Doe",
    "weight": 10.5,
    "destination": "New York",
    "shippingType": "STANDARD"
}
```

**Response:**
```json
HTTP 202 ACCEPTED
Body: 1  (Order ID)
```

**Description:**
- Creates a new order with PENDING status
- Asynchronously executes order processing
- Calculates shipping cost based on weight and shipping type
- Returns the created order ID

---

#### VehicleController.java
Handles vehicle-related HTTP requests.

**Endpoints:**

##### POST /vehicles/bindVehicleToOrder
Binds a vehicle to an existing order.

**Request Body:**
```json
{
    "type": "TRUCK",
    "orderId": 1
}
```

**Response:**
```json
HTTP 200 OK
{
    "id": 1,
    "type": "TRUCK",
    "licensePlate": "ABC-123",
    "capacity": 5000.0,
    "maxSpeed": 120.0
}
```

**Description:**
- Binds an available vehicle of specified type to an order
- Updates order status based on vehicle availability
- Returns vehicle details in response

---

#### AnalyticsController.java
Handles analytics and reporting requests.

**Endpoints:**

##### GET /analytics
Retrieves comprehensive analytics data.

**Response:**
```json
{
    "totalRevenue": 1500.50,
    "mostExpensiveOrder": {
        "id": 5,
        "customerName": "Jane Smith",
        "cost": 500.0,
        "weight": 100.0,
        "shippingType": "EXPRESS",
        "destination": "Los Angeles"
    },
    "delayedOrders": [2, 4, 7],
    "getOrders": {
        "New York": [...],
        "Los Angeles": [...],
        ...
    }
}
```

**Description:**
- Returns total revenue from all orders
- Shows the most expensive order processed
- Lists all delayed orders
- Groups orders by destination

---

### 5. Services

#### OrderService.java
Handles business logic for order operations.

**Key Methods:**

##### `createOrder(OrderDTO dto): int`
- Converts DTO to entity
- Sets initial status to PENDING
- Saves order to database
- Returns order ID

##### `executeOrder(int orderId): void` (Async)
- Retrieves order from database
- Simulates processing delay (3 seconds)
- Calculates shipping cost using ShippingCostService
- Updates order cost
- Marks as FAILED if exception occurs

**Features:**
- Transactional
- Asynchronous execution
- Error handling with status updates

---

#### VehicleService.java
Handles vehicle management and order binding logic.

**Key Methods:**

##### `createNewVehicle(VehicleType type, int orderId): VehicleDTO`
- Retrieves order from database
- Uses DeliveryService factory to create appropriate vehicle
- Performs inventory check
- Returns vehicle details as DTO

##### `performInventoryCheck(Vehicle vehicle, Order order): void`
- Checks vehicle status
- Updates order status based on vehicle availability:
  - If vehicle status is "in_service" → DELAYED
  - Otherwise → READY
- Saves updated order

---

#### ShippingCostService.java
Calculates shipping costs based on strategy pattern.

**Key Methods:**

##### `calculate(ShippingType shippingType, double weight): double`
- Looks up appropriate pricing strategy
- Validates weight constraints:
  - FREE shipping has 5 kg maximum
  - Throws exception if exceeded
- Returns calculated cost

**Strategy Mapping:**
- STANDARD → StandardPricing (weight × 2.5)
- EXPRESS → ExpressPricing (weight × 2.5 × 1.5)
- FREE → FreePricing (0.0)

---

#### AnalyticsService.java
Provides analytics and reporting functionality.

**Key Methods:**

##### `totalRevenue(): double`
- Sums all order costs
- Returns total revenue

##### `mostExpensiveOrder(): Optional<Object>`
- Finds order with highest cost
- Returns as OrderResponseDTO wrapped in Optional

##### `getOrders(): Map<String, List<Order>>`
- Groups all orders by destination
- Returns map structure

##### `getDelayedOrders(): List<Integer>`
- Filters orders with DELAYED status
- Returns list of delayed order IDs

---

### 6. Repositories

#### OrderRepository.java
Data access layer for Order entities.

**Methods:**
- `findByStatus(OrderStatus status): List<Order>` - Find orders by status
- Inherits standard CRUD operations from JpaRepository

---

#### VehicleRepository.java
Data access layer for Vehicle entities.

**Methods:**
- `status(String status): List<Vehicle>` - Find vehicles by status
- `findFirstByTypeAndStatus(VehicleType type, String status): Optional<Vehicle>` - Find available vehicle by type and status
- `getVehiclesByType(VehicleType type): Vehicle` - Get vehicle by type
- `findFirstByType(VehicleType type): Vehicle` - Find first vehicle of type
- Inherits standard CRUD operations from JpaRepository

---

#### AnalyticsRepository.java
Currently empty repository class (placeholder).

---

### 7. Factory Pattern Implementation

#### VehicleFactory.java (Abstract Base)
Base abstract class for vehicle factory pattern.

```java
abstract class VehicleFactory {
    protected abstract Vehicle createVehicleInstance();
    public abstract VehicleType getType();
    public Vehicle prepareVehicle();
}
```

**Purpose:** Define common interface for vehicle creation

---

#### TruckFactory.java
Concrete factory for creating Truck vehicles.

**Implementation:**
- `createVehicleInstance()`: Retrieves first TRUCK type vehicle from repository
- `getType()`: Returns VehicleType.TRUCK

---

#### VanFactory.java
Concrete factory for creating Van vehicles.

**Implementation:**
- `createVehicleInstance()`: Retrieves first VAN type vehicle from repository
- `getType()`: Returns VehicleType.VAN

---

#### DroneFactory.java
Concrete factory for creating Drone vehicles.

**Implementation:**
- `createVehicleInstance()`: Retrieves first DRONE type vehicle from repository
- `getType()`: Returns VehicleType.DRONE

---

#### DeliveryService.java
Service that manages the factory map and delegates vehicle creation.

**Key Components:**
- Maintains map of `VehicleType` to `VehicleFactory` instances
- Auto-wired with list of all VehicleFactory implementations
- Maps factories by their type on initialization

**Key Methods:**

##### `startDelivery(VehicleType type): Vehicle`
- Looks up factory for specified vehicle type
- Throws RuntimeException if type not found
- Returns prepared vehicle from factory

---

### 8. Strategy Pattern for Pricing

#### ShippingPricing.java (Strategy Interface)
Defines contract for pricing strategies.

```java
interface ShippingPricing {
    double calculateCost(double weight);
}
```

---

#### StandardPricing.java
Standard shipping pricing implementation.

**Formula:** `weight × 2.5`

**Bean Name:** "STANDARD"

---

#### ExpressPricing.java
Express shipping pricing implementation (premium).

**Formula:** `weight × 2.5 × 1.5 = weight × 3.75`

**Bean Name:** "EXPRESS"

---

#### FreePricing.java
Free shipping pricing implementation (cost-free).

**Formula:** `0.0`

**Constraint:** Maximum 5 kg weight (enforced in ShippingCostService)

**Bean Name:** "FREE"

---

### 9. Configuration

#### AsyncConfig.java
Configuration for asynchronous processing.

**Features:**
- `@EnableAsync` annotation enables async method execution
- Allows methods annotated with `@Async` to run in separate thread pool

---

#### SecurityConfig.java
Security configuration class (currently empty/placeholder).

---

### 10. Main Application

#### LogisticsApiApplication.java
Main entry point for Spring Boot application.

**Annotations:**
- `@SpringBootApplication`: Enables auto-configuration and component scanning
- `@EnableAsync`: Enables asynchronous processing

**Startup:**
```bash
mvn spring-boot:run
```

---

## API Endpoints

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders` | Create and process a new order |

### Vehicle Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/vehicles/bindVehicleToOrder` | Bind a vehicle to an order |

### Analytics Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/analytics` | Get comprehensive analytics data |

---

## Database Schema

### Orders Table

```sql
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(128) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    destination VARCHAR(128) NOT NULL,
    shipping_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    cost DOUBLE PRECISION NOT NULL
);
```

**Columns:**
- `id`: Auto-incremented primary key
- `customer_name`: Customer's name (max 128 characters)
- `weight`: Order weight in kilograms
- `destination`: Delivery destination address
- `shipping_type`: One of STANDARD, EXPRESS, or FREE
- `status`: One of PENDING, DELAYED, READY, or FAILED
- `cost`: Calculated shipping cost

---

### Vehicles Table

```sql
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    license_plate VARCHAR(255) NOT NULL UNIQUE,
    capacity DOUBLE PRECISION NOT NULL,
    max_speed DOUBLE PRECISION NOT NULL,
    status VARCHAR(255) NOT NULL
);
```

**Columns:**
- `id`: Auto-incremented primary key
- `type`: One of TRUCK, VAN, or DRONE
- `license_plate`: Vehicle's license plate (unique)
- `capacity`: Maximum cargo capacity in kg
- `max_speed`: Maximum speed the vehicle can travel
- `status`: Vehicle status (TRUE/FALSE or "in_service")

---

## Configuration

### Application Properties (application.properties)

```properties
spring.application.name=Logistics_API
server.port=8081
spring.datasource.url=jdbc:postgresql://172.24.44.59:5432/myappdb
spring.datasource.username=myappuser
spring.datasource.password=myapppassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

**Configuration Details:**
- **Application Name**: Logistics_API
- **Server Port**: 8081
- **Database**: PostgreSQL on 172.24.44.59:5432
- **Database Name**: myappdb
- **DDL Auto**: update (creates/updates tables automatically)
- **Show SQL**: Enabled (logs SQL queries)
- **Open in View**: Disabled (better performance)

---

## Design Patterns

### 1. Factory Pattern
**Location:** `FactoryPattern` package

**Purpose:** Create vehicle instances without specifying concrete classes directly

**Implementation:**
- Abstract `VehicleFactory` base class
- Concrete implementations: `TruckFactory`, `VanFactory`, `DroneFactory`
- `DeliveryService` manages factory instances

**Benefits:**
- Encapsulates object creation
- Easy to add new vehicle types
- Centralized vehicle creation logic

---

### 2. Strategy Pattern
**Location:** `PricingService` package

**Purpose:** Encapsulate different pricing algorithms

**Implementation:**
- `ShippingPricing` interface defines common contract
- Concrete strategies: `StandardPricing`, `ExpressPricing`, `FreePricing`
- `ShippingCostService` selects and executes appropriate strategy

**Benefits:**
- Easy to add new pricing strategies
- Runtime algorithm selection
- Decouples pricing logic from order processing

---

### 3. Repository Pattern
**Location:** `Repository` package

**Purpose:** Abstract data access logic

**Implementation:**
- `OrderRepository`, `VehicleRepository` interfaces extending `JpaRepository`
- Spring Data JPA provides implementations

**Benefits:**
- Centralized data access
- Easy to switch database implementations
- CRUD operations abstraction

---

### 4. Service/Business Logic Layer Pattern
**Location:** `Service` package

**Purpose:** Encapsulate business logic separate from HTTP handling

**Implementation:**
- `OrderService`, `VehicleService`, `ShippingCostService`, `AnalyticsService`
- Controllers delegate to services

**Benefits:**
- Reusable business logic
- Testable components
- Clear separation of concerns

---

### 5. DTO (Data Transfer Object) Pattern
**Location:** `DTO` package

**Purpose:** Transfer data between layers without exposing entities

**Implementation:**
- `OrderDTO`, `VehicleDTO` (records)
- `OrderResponseDTO`, `VehicleRequestDTO` (classes)

**Benefits:**
- Hides internal entity structure
- Reduces bandwidth (only needed fields)
- Enables input validation

---

### 6. Async Processing Pattern
**Location:** `Service` layer & `Config` package

**Purpose:** Non-blocking order processing

**Implementation:**
- `@Async` annotation on `OrderService.executeOrder()`
- `AsyncConfig` enables async processing

**Benefits:**
- Non-blocking API responses
- Better resource utilization
- Improved user experience

---

## Workflow Examples

### Order Creation and Processing Flow

```
1. Client POSTs to /orders with OrderDTO
   ↓
2. OrderController receives request
   ↓
3. OrderService.createOrder() called
   ↓
4. Order entity created and saved to database with PENDING status
   ↓
5. OrderID returned to client (202 ACCEPTED)
   ↓
6. OrderService.executeOrder() called asynchronously
   ↓
7. Thread sleep for 3 seconds (simulated processing)
   ↓
8. ShippingCostService calculates cost
   ↓
9. Order cost updated in database
   ↓
(Processing complete)
```

### Vehicle Binding Flow

```
1. Client POSTs to /vehicles/bindVehicleToOrder with VehicleRequestDTO
   ↓
2. VehicleController receives request with type and orderId
   ↓
3. VehicleService.createNewVehicle() called
   ↓
4. Order retrieved from database by orderId
   ↓
5. DeliveryService.startDelivery() called with vehicle type
   ↓
6. Appropriate factory creates vehicle instance from repository
   ↓
7. VehicleService.performInventoryCheck() called
   ↓
8. Order status updated based on vehicle availability
   ↓
9. VehicleDTO returned to client (200 OK)
```

### Analytics Flow

```
1. Client GETs /analytics
   ↓
2. AnalyticsController receives request
   ↓
3. AnalyticsService methods called:
   - totalRevenue(): Sums all order costs
   - mostExpensiveOrder(): Finds max cost order
   - getDelayedOrders(): Filters DELAYED orders
   - getOrders(): Groups orders by destination
   ↓
4. Map of analytics data returned (200 OK)
```

---

## Error Handling

### Handled Exceptions

1. **OrderService:**
   - `IllegalArgumentException`: Order not found
   - General exceptions trigger FAILED order status

2. **VehicleService:**
   - `IllegalArgumentException`: Order not found

3. **ShippingCostService:**
   - `IllegalArgumentException`: 
     - Unsupported shipping type
     - Weight exceeds FREE shipping limit (5 kg)

4. **DeliveryService:**
   - `RuntimeException`: Unsupported vehicle type

---

## Performance Considerations

1. **Asynchronous Processing**: Order processing doesn't block API response
2. **Database Indexing**: IDs and unique fields (license plate) are indexed
3. **Lazy Loading Disabled**: `spring.jpa.open-in-view=false` improves performance
4. **SQL Logging**: Enabled for debugging, should be disabled in production

---

## Future Enhancements

1. **Authentication & Authorization**: Implement SecurityConfig
2. **AnalyticsRepository**: Fully implement with custom queries
3. **Validation**: Add bean validation (jakarta.validation)
4. **Error Responses**: Implement global exception handler
5. **API Documentation**: Add Swagger/OpenAPI documentation
6. **Caching**: Implement caching for frequently accessed data
7. **Pagination**: Add pagination for order/vehicle listings
8. **Filtering**: Add advanced filtering options
9. **Rate Limiting**: Implement rate limiting for API endpoints
10. **Monitoring**: Add metrics and health checks

---

## Testing Endpoints

### Example cURL Commands

#### Create Order
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

#### Bind Vehicle to Order
```bash
curl -X POST http://localhost:8081/vehicles/bindVehicleToOrder \
  -H "Content-Type: application/json" \
  -d '{
    "type": "TRUCK",
    "orderId": 1
  }'
```

#### Get Analytics
```bash
curl -X GET http://localhost:8081/analytics \
  -H "Content-Type: application/json"
```

---

## Running the Application

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 12+ running on 172.24.44.59:5432

### Steps

1. **Clone/Navigate to project:**
   ```bash
   cd C:\Users\riaek\IdeaProjects\Logistics_API
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API:**
   - Base URL: `http://localhost:8081`
   - Health Check: `http://localhost:8081/actuator/health` (if enabled)

---

## Project Structure Summary

| Component | Purpose | Pattern Used |
|-----------|---------|--------------|
| Entity | Database models | Domain Model |
| Repository | Data access | Repository |
| Service | Business logic | Business Logic |
| Controller | HTTP handling | MVC Controller |
| DTO | Data transfer | Data Transfer Object |
| Enumeration | Constants | Enum |
| FactoryPattern | Vehicle creation | Factory |
| PricingService | Pricing algorithms | Strategy |
| Config | Application setup | Configuration |

---

## Conclusion

The Logistics API is a well-structured Spring Boot application that demonstrates key architectural patterns and best practices:

- **Clean Architecture**: Separation of concerns across layers
- **Design Patterns**: Factory and Strategy patterns for extensibility
- **Async Processing**: Non-blocking order processing
- **Database Integration**: JPA/Hibernate with PostgreSQL
- **RESTful API**: Clean HTTP endpoints for business operations

The application is ready for deployment and can be easily extended with additional features such as authentication, advanced analytics, and payment processing.

---

**Documentation Generated**: March 18, 2026
**Application Version**: 0.0.1-SNAPSHOT
**Last Updated**: Current
