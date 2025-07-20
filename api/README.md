# Short.ln - URL Shortener API

A modern URL shortener service built with Spring Boot and MongoDB, providing a RESTful API to create, manage, and track shortened URLs.

## 🚀 Features

- **URL Shortening**: Convert long URLs into short, shareable links
- **URL Management**: Create, read, update, and delete shortened URLs
- **Click Tracking**: Monitor access statistics for each shortened URL
- **Base62 Encoding**: Efficient short code generation using Base62 algorithm
- **MongoDB Integration**: Persistent storage with MongoDB
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Validation**: Comprehensive URL format validation
- **Logging**: Detailed logging with SLF4J

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data MongoDB**
- **MongoDB**
- **Lombok**
- **Swagger/OpenAPI 3**
- **Maven**

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- MongoDB (local or MongoDB Atlas)

## ⚙️ Configuration

### Application Properties

Create `application-dev.properties` in `src/main/resources/`:

```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=shortLn
default_url=https://localhost:8080/
```

### Environment Setup

1. **MongoDB**: Set up a MongoDB instance (local or Atlas)
2. **Environment Variables**: Configure your MongoDB connection string
3. **Profile**: Activate the dev profile by setting `spring.profiles.active=dev`

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Mats057/short.ln-backend.git
cd short.ln-backend
```

### 2. Configure Database

Update the MongoDB connection string in `application-dev.properties`

### 3. Run and Build the Application with your IDE

The API will be available at `http://localhost:8080`

## 📚 API Documentation

### Interactive Documentation

Once the application is running, access the Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html#
```

### API Endpoints

#### Create Shortened URL
```http
POST /shorten
Content-Type: application/json

{
  "url": "https://www.example.com/very/long/url"
}
```

**Response:**
```json
{
  "id": 1,
  "url": "https://www.example.com/very/long/url",
  "shortCode": "b",
  "createdAt": "2025-07-20T16:30:00.000Z",
  "updatedAt": "2025-07-20T16:30:00.000Z"
}
```

#### Get Original URL
```http
GET /shorten/{shortCode}
```

#### Update URL
```http
PUT /shorten/{shortCode}
Content-Type: application/json

{
  "url": "https://www.example.com/updated/url"
}
```

#### Delete URL
```http
DELETE /shorten/{shortCode}
```

#### Get URL Statistics
```http
GET /shorten/{shortCode}/stats
```

**Response:**
```json
{
  "id": 1,
  "url": "https://www.example.com/very/long/url",
  "shortCode": "b",
  "createdAt": "2025-07-20T16:30:00.000Z",
  "updatedAt": "2025-07-20T16:30:00.000Z",
  "accessCount": 42
}
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/mats057/shortLn/
│   │       ├── Application.java                 # Main application class
│   │       ├── business/
│   │       │   ├── models/
│   │       │   │   └── Url.java                # URL entity model
│   │       │   └── services/
│   │       │       ├── ShorteningService.java  # Core business logic
│   │       │       └── SequenceGeneratorService.java # ID generation
│   │       ├── controller/
│   │       │   ├── UrlController.java          # REST endpoints
│   │       │   └── dtos/
│   │       │       ├── UrlCreateDTO.java       # Request DTO
│   │       │       ├── UrlRequestDTO.java      # Response DTO
│   │       │       └── UrlStatsRequestDTO.java # Statistics DTO
│   │       └── infrastructure/
│   │           ├── exceptions/
│   │           │   ├── InvalidUrlException.java
│   │           │   └── URLNotFoundException.java
│   │           └── repositories/
│   │               └── UrlRepo.java            # MongoDB repository
│   └── resources/
│       ├── application.properties              # Main configuration
│       └── application-dev.properties          # Development configuration
└── test/
    └── java/
        └── com/example/demo/
            └── ApplicationTests.java
```

## 🔧 Key Components

### URL Model
- **ID**: Unique identifier (Long)
- **URL**: Original URL string
- **Short Code**: Generated short identifier
- **Created At**: Timestamp of creation
- **Updated At**: Timestamp of last update
- **Access Count**: Number of times accessed

### Base62 Encoding
The application uses Base62 encoding to generate short codes from numeric IDs:
- Character set: `a-z`, `A-Z`, `0-9` (62 characters)
- Generates short, URL-safe identifiers
- Collision-resistant with sequential ID generation

### Validation
- URLs must start with `http://` or `https://`
- Supports complex URLs with query parameters and encoded characters
- Comprehensive error handling with custom exceptions

## 📊 Monitoring & Logging

The application includes comprehensive logging:
- Service-level operation logging
- Error tracking and debugging
- Performance monitoring for encoding operations (coming soon)

## 🧪 Testing (Coming soon - unit and integration tests)

<!-- Run the test suite:

```bash
mvn test
``` -->

## 🚀 Deployment (Coming soon)

<!-- ### Docker Deployment -->

## 👤 Author

**Matheus Q. Zanutin** - [@Mats057](https://github.com/Mats057) - [LinkedIn](https://www.linkedin.com/in/matheus-zanutin/)

<!-- - Thanks roadmap.sh for the [project idea](https://roadmap.sh/projects/url-shortening-service). -->


<!-- ---

## 📈 Future Features Roadmap

- [ ] Rate limiting implementation
- [ ] User authentication and authorization
- [ ] Authentication for management endpoints
- [ ] Analytics dashboard
- [ ] Custom short code support
- [ ] QR code generation
- [ ] Link expiration dates
- [ ] Bulk URL operations
- [ ] API key management
- [ ] Detailed analytics and reporting -->
