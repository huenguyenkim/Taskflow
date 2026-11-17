# 🚀 TaskFlow

> Task Management System built with Spring Boot and secured with JWT Authentication.
>
> Supports Role-Based Authorization, Access Token, Refresh Token, Pagination, Search, Filtering, Sorting, Swagger Documentation, and Docker Deployment.

---

## ✨ Features

### 🔐 Authentication

- User Registration
- User Login
- JWT Authentication
- Access Token
- Refresh Token
- Secure API Access

### 👤 User Management

- Get Current User Profile
- Get User By ID
- Get All Users (ADMIN)
- Delete User (ADMIN)

### ✅ Task Management

- Create Task
- Get Task By ID
- Update Task
- Delete Task
- Pagination
- Search by Title
- Filter by Status
- Sorting

### 🛡 Authorization

- Role-Based Access Control
  - USER
  - ADMIN
- Spring Security Integration

### 📖 Documentation & DevOps

- Swagger / OpenAPI Documentation
- Docker Support
- Docker Compose Support
- Maven Build Automation

---

## 🚀 Advanced Features

✅ JWT Authentication

✅ Access Token

✅ Refresh Token

✅ Refresh Token Rotation

✅ Role-Based Authorization

✅ Pagination

✅ Search by Title

✅ Filter by Status

✅ Sorting

✅ Swagger/OpenAPI

✅ Docker & Docker Compose

✅ Audit Fields (`createdAt`, `updatedAt`)

---

## 🛠 Tech Stack

| Layer | Technologies |
|---------|---------|
| Backend | Java 17, Spring Boot 3.5.6 |
| Security | Spring Security 6 |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Authentication | JWT + Refresh Token |
| Documentation | Swagger / OpenAPI 3 |
| DevOps | Docker, Docker Compose |
| Build Tool | Maven |
| Utilities | Lombok, Jakarta Validation API |

---

## 🏛 System Architecture

```mermaid
flowchart LR

    Client["Client<br/>(Postman / Swagger UI / React Frontend)"]

    Client --> API["Spring Boot REST API"]

    API --> Security["Spring Security<br/>+ JWT Filter"]

    Security --> Controller["Controller"]

    Controller --> Service["Service"]

    Service --> Repository["Repository (JPA)"]

    Repository --> DB[("MySQL")]
```

---

## 🔐 Authentication Architecture

```text
auth
├── Login
├── Access Token (24 hours)
├── Refresh Token (7 days)
└── Refresh Endpoint
```

---

## 🔐 JWT Authentication Flow

```mermaid
sequenceDiagram

    actor User

    participant API as Spring Boot API
    participant DB as MySQL

    User->>API: POST /api/auth/login

    API->>DB: Find user by email

    DB-->>API: User record

    API->>API: Verify password

    API->>API: Generate Access Token

    API->>API: Generate Refresh Token

    API-->>User: Return Tokens

    User->>API: Authorization Bearer Token

    API->>API: Validate JWT

    API-->>User: Protected Resource
```

---

## 🔄 Refresh Token Flow

```mermaid
sequenceDiagram

    actor User

    participant API as Spring Boot API
    participant DB as MySQL

    User->>API: POST /api/auth/refresh-token

    API->>DB: Validate Refresh Token

    DB-->>API: Refresh Token Valid

    API->>API: Generate New Access Token

    API->>API: Rotate Refresh Token

    API-->>User: Return New Tokens
```

---

## 🗄 Entity Relationship Diagram

```mermaid
erDiagram

    USER ||--o{ TASK : owns
    USER ||--|| REFRESH_TOKEN : has

    USER {
        Long id PK
        String username
        String email
        String password
        String role
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    TASK {
        Long id PK
        String title
        String description
        String status
        Long user_id FK
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    REFRESH_TOKEN {
        Long id PK
        String token
        Long user_id FK
        Instant expiryDate
    }
```

---

## 📁 Project Structure

```text
taskflow/
├── screenshots/
├── src/
│   ├── main/
│   │   ├── java/com/taskflow/
│   │   │
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17
- Maven 3.9+
- MySQL 8

---

### Clone Repository

```bash
git clone https://github.com/huenguyenkim/Taskflow.git
```

```bash
cd Taskflow
```

---

### Create Database

```sql
CREATE DATABASE taskflow;
```

---

### Configure Application

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
jwt.expiration=86400000
```

---

### Run Application

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

---

### Build JAR

```bash
./mvnw clean package
```

```bash
java -jar target/taskflow-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Docker

### Build Image

```bash
docker build -t taskflow .
```

### Run Container

```bash
docker run -p 8080:8080 taskflow
```

---

## 🐳 Docker Compose

### Start

```bash
docker compose up -d
```

### Stop

```bash
docker compose down
```

---

## 📖 Swagger API Documentation

After starting application:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🔗 Main Endpoints

### Authentication

| Method | Endpoint |
|----------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |
| POST | /api/auth/refresh-token |

### Users

| Method | Endpoint |
|----------|----------|
| GET | /api/users/profile |
| GET | /api/users |
| GET | /api/users/{id} |
| DELETE | /api/users/{id} |

### Tasks

| Method | Endpoint |
|----------|----------|
| POST | /api/tasks |
| GET | /api/tasks |
| GET | /api/tasks/{id} |
| PUT | /api/tasks/{id} |
| DELETE | /api/tasks/{id} |

---

## 🔍 Task Query Examples

### Pagination

```http
GET /api/tasks?page=0&size=5
```

### Search

```http
GET /api/tasks?keyword=swagger
```

### Filter

```http
GET /api/tasks?status=DONE
```

### Sort

```http
GET /api/tasks?sortBy=createdAt&direction=desc
```

### Combined Query

```http
GET /api/tasks?page=0&size=5&status=DONE&keyword=swagger&sortBy=createdAt&direction=desc
```

---

## 🔐 Login Example

### Request

```json
{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

### Response

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "8f9fc15d-d615-411d-862f-937e876623cf",
  "tokenType": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@gmail.com",
  "role": "ADMIN"
}
```

---

## 🔄 Refresh Token Example

### Request

```json
{
  "refreshToken": "8f9fc15d-d615-411d-862f-937e876623cf"
}
```

### Response

```json
{
  "accessToken": "new-access-token",
  "refreshToken": "new-refresh-token",
  "tokenType": "Bearer"
}
```

---

## 🖼 Screenshots

### Swagger UI

```text
screenshots/swagger-ui.png
```

### JWT Authentication

```text
screenshots/login-success.png
```

### Task Management API

```text
screenshots/task-list.png
```

### Pagination Search Filter Sort

```text
screenshots/pagination-search-filter-sort.png
```

### Refresh Token Authentication

```text
screenshots/refresh-token.png
```

---

## ✅ Completed Features

- JWT Authentication
- Access Token
- Refresh Token
- Refresh Token Rotation
- Role-Based Authorization
- User Management
- Task CRUD
- Pagination
- Search
- Filter
- Sorting
- Swagger Documentation
- Docker Support
- Docker Compose
- MySQL Integration
- Audit Fields

---

## 🔮 Future Improvements

- [ ] React Frontend (Vite + Tailwind CSS)
- [ ] Email Notifications
- [ ] File Attachments
- [ ] Redis Caching
- [ ] CI/CD Pipeline (GitHub Actions)
- [ ] Kubernetes Deployment
- [ ] Task Dashboard & Analytics

---

## 👩‍💻 Author

**Nguyen Thi Kim Hue**

Backend Developer (Java / Spring Boot)

- GitHub: https://github.com/huenguyenkim
- Repository: https://github.com/huenguyenkim/Taskflow
- Email: nguyenhue2612200398@gmail.com

---

## 📄 License

This project is licensed under the MIT License.v
