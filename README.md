# 🚀 TaskFlow Backend

> A secure Task Management REST API built with Spring Boot 3, Spring Security JWT, and MySQL.
>
> Supports JWT Authentication, Refresh Token Authentication, Ownership-Based Access Control, Dashboard Analytics, Validation, Exception Handling, and Comprehensive Testing.

---

## 🌐 Live Demo

### Frontend

https://taskflow-frontend-eight-chi.vercel.app/

### Backend API

Railway Deployment

### Swagger Documentation

```text
http://localhost:8080/swagger-ui/index.html
```

---

# ✨ Features

## 🔐 Authentication

- User Registration
- User Login
- JWT Authentication
- Access Token Authentication
- Refresh Token Authentication
- Refresh Token Rotation
- Protected REST APIs

---

## 👤 User Management

- Get Current User Profile
- Get User By ID
- Get All Users (ADMIN)
- Delete User (ADMIN)

---

## ✅ Task Management

- Create Task
- Get Task By ID
- Update Task
- Delete Task
- Search Tasks
- Filter Tasks By Status
- Pagination
- Sorting
- Dashboard Statistics

---

## 🛡 Security

### Role-Based Authorization

Supported Roles:

- USER
- ADMIN

### Ownership-Based Access Control

Users can:

- View their own tasks
- Update their own tasks
- Delete their own tasks

Users cannot access another user's task even when the task ID is known.

---

## ✅ Validation & Exception Handling

### DTO Validation

Implemented using Jakarta Bean Validation:

- `@NotBlank`
- `@NotNull`
- `@Size`
- `@Email`

### Global Exception Handling

Implemented using:

```java
@RestControllerAdvice
```

Standardized API responses for:

- Validation Errors
- Resource Not Found
- Bad Requests
- Internal Server Errors

---

# 📊 Dashboard

Provides statistics for the currently authenticated user:

- Total Tasks
- TODO Tasks
- In Progress Tasks
- Completed Tasks

Dashboard data is isolated through Ownership-Based Access Control.

---

# 🧪 Testing

## Unit Testing

Tools:

- JUnit 5
- Mockito

### Tested Components

- TaskService
- Dashboard Logic
- Ownership Security Logic

---

## Integration Testing

Tools:

- MockMvc
- Spring Security Test

### Tested APIs

- Register API
- Login API
- Refresh Token API
- Task CRUD APIs
- Dashboard APIs

---

## Repository Testing

Tools:

- DataJpaTest
- H2 In-Memory Database

### Tested Queries

- `findByUser()`
- `findByIdAndUser()`
- `countByUser()`
- `countByUserAndStatus()`

---

## ✅ Test Result

```text
Tests run: 30
Failures: 0
Errors: 0

BUILD SUCCESS
```

---

# 🚀 Advanced Features

✅ JWT Authentication

✅ Refresh Token Authentication

✅ Refresh Token Rotation

✅ Role-Based Authorization

✅ Ownership-Based Access Control

✅ Dashboard Statistics

✅ DTO Validation

✅ Global Exception Handling

✅ Pagination

✅ Search By Title

✅ Filter By Status

✅ Sorting

✅ Swagger/OpenAPI Documentation

✅ Unit Testing

✅ Integration Testing

✅ Repository Testing

✅ Audit Fields (`createdAt`, `updatedAt`)

---

# 🛠 Tech Stack

| Layer | Technologies |
|---------|-------------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.6 |
| Security | Spring Security 6 |
| Authentication | JWT + Refresh Token |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Validation | Jakarta Bean Validation |
| Documentation | Swagger / OpenAPI |
| Testing | JUnit 5, Mockito, MockMvc, DataJpaTest |
| Build Tool | Maven |
| Utility | Lombok |

---

# 🏛 Architecture

```text
Client
   ↓
JWT Filter
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL
```

---

# 📁 Project Structure

```text
src
├── main
│   ├── java/com/taskflow
│   │
│   ├── config
│   ├── controller
│   ├── dto
│   │   ├── request
│   │   └── response
│   │
│   ├── entity
│   ├── exception
│   ├── repository
│   ├── security
│   └── service
│
└── test
    ├── controller
    ├── repository
    └── service
```

---

# 🗄 Database Design

```text
USER
├── id
├── username
├── email
├── password
├── role
├── createdAt
└── updatedAt

TASK
├── id
├── title
├── description
├── status
├── user_id
├── createdAt
└── updatedAt

REFRESH_TOKEN
├── id
├── token
├── user_id
└── expiryDate
```

---

# ⚙️ Getting Started

## Prerequisites

- Java 17
- Maven 3.9+
- MySQL 8

---

## Clone Repository

```bash
git clone https://github.com/huenguyenkim/Taskflow.git
```

```bash
cd Taskflow
```

---

## Create Database

```sql
CREATE DATABASE taskflow;
```

---

## Configure Application

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
jwt.expiration=86400000
jwt.refresh-expiration=604800000
```

---

## Run Application

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

## Run Tests

```bash
./mvnw test
```

---

## Build Application

```bash
./mvnw clean package
```

```bash
java -jar target/taskflow-0.0.1-SNAPSHOT.jar
```

---

# 📖 Swagger Documentation

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 🔗 Main Endpoints

## Authentication

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh-token
```

## Users

```http
GET    /api/users/profile
GET    /api/users
GET    /api/users/{id}
DELETE /api/users/{id}
```

## Tasks

```http
POST   /api/tasks
GET    /api/tasks
GET    /api/tasks/{id}
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}

GET    /api/tasks/stats
```

---

# 🔍 Query Examples

### Search

```http
GET /api/tasks?keyword=spring
```

### Filter

```http
GET /api/tasks?status=DONE
```

### Pagination

```http
GET /api/tasks?page=0&size=5
```

### Sort

```http
GET /api/tasks?sortBy=createdAt&direction=desc
```

### Combined Query

```http
GET /api/tasks?page=0&size=5&keyword=spring&status=DONE&sortBy=createdAt&direction=desc
```

---

# ✅ Completed Features

- JWT Authentication
- Refresh Token Authentication
- Refresh Token Rotation
- Role-Based Authorization
- Ownership-Based Access Control
- Dashboard Statistics
- Task CRUD Operations
- Search & Filtering
- Pagination & Sorting
- DTO Validation
- Global Exception Handling
- Swagger Documentation
- Unit Testing
- Integration Testing
- Repository Testing

---

# 🔮 Future Improvements

- Redis Caching
- Email Notification Service
- File Attachments
- GitHub Actions CI/CD
- Kubernetes Deployment
- WebSocket Notifications

---

# 👩‍💻 Author

**Nguyen Thi Kim Hue**

- GitHub: https://github.com/huenguyenkim
- Backend Repository: https://github.com/huenguyenkim/Taskflow
- Frontend Repository: https://github.com/huenguyenkim/taskflow-frontend
- Frontend Demo: https://taskflow-frontend-eight-chi.vercel.app/
- Email: nguyenhue2612200398@gmail.com

---

# 📄 License

MIT License
