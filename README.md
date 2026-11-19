Nếu README này dành riêng cho Backend Repository của TaskFlow thì nên viết theo hướng backend-focused, phản ánh đúng những gì bạn đã hoàn thành.

🚀 TaskFlow Backend

A secure Task Management REST API built with Java 17, Spring Boot 3, Spring Security JWT, and MySQL.

The application supports JWT Authentication, Refresh Token Authentication, Ownership-Based Access Control, Dashboard Analytics, Validation, Exception Handling, and comprehensive testing.

✨ Features
🔐 Authentication
User Registration
User Login
JWT Authentication
Access Token Authentication
Refresh Token Authentication
Refresh Token Rotation
Protected REST APIs
👤 User Management
Get Current User Profile
Get User By ID
Get All Users (ADMIN)
Delete User (ADMIN)
✅ Task Management
Create Task
Get Task By ID
Update Task
Delete Task
Search Tasks
Filter Tasks by Status
Pagination
Sorting
Dashboard Statistics
🛡 Security
Role-Based Authorization

Supported roles:

USER
ADMIN
Ownership-Based Access Control

Users can only:

View their own tasks
Update their own tasks
Delete their own tasks

Even when a valid task ID is known, a user cannot access another user's data.

🚀 Advanced Features

✅ JWT Authentication

✅ Refresh Token Authentication

✅ Refresh Token Rotation

✅ Role-Based Authorization

✅ Ownership-Based Access Control

✅ Dashboard Statistics

✅ DTO Validation

✅ Global Exception Handling

✅ Pagination

✅ Search by Title

✅ Filter by Status

✅ Sorting

✅ Swagger/OpenAPI Documentation

✅ Unit Testing

✅ Integration Testing

✅ Repository Testing

✅ Audit Fields (createdAt, updatedAt)

🧪 Testing
Unit Testing
JUnit 5
Mockito
Tested Components
TaskService
Dashboard Logic
Ownership Security Logic
Integration Testing
MockMvc
Spring Security Test
Tested APIs
Register API
Login API
Refresh Token API
Task CRUD API
Dashboard API
Repository Testing
DataJpaTest
H2 In-Memory Database
Tested Queries
findByUser()
findByIdAndUser()
countByUser()
countByUserAndStatus()
Test Result
Tests run: 30
Failures: 0
Errors: 0
BUILD SUCCESS

🛠 Tech Stack
Layer	TechnologiesLanguage	Java 17
Framework	Spring Boot 3.5.6
Security	Spring Security 6
Authentication	JWT + Refresh Token
Database	MySQL 8
ORM	Spring Data JPA, Hibernate
Validation	Jakarta Bean Validation
Documentation	Swagger / OpenAPI 3
Testing	JUnit 5, Mockito, MockMvc, DataJpaTest
Build Tool	Maven
Utility	Lombok
🏛 Architecture
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

📁 Project Structure
src
├── main
│   ├── java/com/taskflow
│   │   ├── config
│   │   ├── controller
│   │   ├── dto
│   │   │   ├── request
│   │   │   └── response
│   │   ├── entity
│   │   ├── exception
│   │   ├── repository
│   │   ├── security
│   │   └── service
│   │
│   └── resources
│
└── test
    ├── controller
    ├── repository
    └── service

🗄 Database Design
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

⚙️ Getting Started
Prerequisites
Java 17
Maven 3.9+
MySQL 8
Clone Repository
git clone https://github.com/huenguyenkim/Taskflow.git

cd Taskflow

Create Database
CREATE DATABASE taskflow;

Configure Application
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
jwt.expiration=86400000
jwt.refresh-expiration=604800000

Run Application

Windows

.\mvnw.cmd spring-boot:run


Linux / Mac

./mvnw spring-boot:run

Run Tests
./mvnw test

Build Application
./mvnw clean package

java -jar target/taskflow-0.0.1-SNAPSHOT.jar

📖 Swagger Documentation
http://localhost:8080/swagger-ui/index.html

🔗 Main Endpoints
Authentication
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh-token

Users
GET    /api/users/profile
GET    /api/users
GET    /api/users/{id}
DELETE /api/users/{id}

Tasks
POST   /api/tasks
GET    /api/tasks
GET    /api/tasks/{id}
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}

GET    /api/tasks/stats

🔍 Query Examples
Search
GET /api/tasks?keyword=spring

Filter
GET /api/tasks?status=DONE

Pagination
GET /api/tasks?page=0&size=5

Sort
GET /api/tasks?sortBy=createdAt&direction=desc

✅ Completed Features
JWT Authentication
Refresh Token Authentication
Refresh Token Rotation
Role-Based Authorization
Ownership-Based Access Control
Dashboard Statistics
Task CRUD Operations
Search & Filtering
Pagination & Sorting
DTO Validation
Global Exception Handling
Swagger Documentation
Unit Testing
Integration Testing
Repository Testing
🔮 Future Improvements
Redis Caching
Email Notification Service
File Attachments
GitHub Actions CI/CD
Kubernetes Deployment
WebSocket Notifications
👩‍💻 Author

Nguyen Thi Kim Hue

GitHub: https://github.com/huenguyenkim
Repository: https://github.com/huenguyenkim/Taskflow
Email: nguyenhue2612200398@gmail.com
📄 License

MIT License

Đây là phiên bản README backend phù hợp hơn với dự án hiện tại vì phản ánh đúng các phần bạn đã hoàn thành: Ownership Security, Dashboard, Validation, Exception Handling và 30 test case PASS.
