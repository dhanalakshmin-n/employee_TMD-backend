# Employee Task Management Dashboard

A full-stack task management dashboard where managers create and assign tasks to employees, and employees update task statuses and track progress.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17, Spring Boot 3.3, Spring Data JPA |
| Database | PostgreSQL |
| Frontend | React (coming soon) |

**Base URL:** `http://localhost:8080`

All API responses use a common wrapper:

```json
{
  "success": true,
  "message": "Operation message",
  "data": { }
}
```

---

## Setup Instructions

### Prerequisites

| Tool | Version |
|------|---------|
| Java JDK | 17 |
| Maven | 3.8+ |
| PostgreSQL | 14+ |

### 1. Create database

```sql
CREATE DATABASE employee_tmd;
```

### 2. Configure database

Edit [`backend/src/main/resources/application.properties`](backend/src/main/resources/application.properties) or set env vars in your IDE run config:

| Variable | Example |
|----------|---------|
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/employee_tmd` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `your_password` |

### 3. Run backend

```bash
cd backend
mvn spring-boot:run
```

Tables are auto-created on startup. Sample employees and tasks are seeded from `data.sql`.

---

## Mock Login

| Role | Email |
|------|-------|
| Manager | `manager@test.com` (hardcoded) |
| Employee | Any email in the `employees` table (e.g. `employee@test.com`) |

```http
POST /api/auth/login
Content-Type: application/json

{ "email": "manager@test.com" }
```

No password required.

---

## API Documentation

### Auth

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login with email, returns role + employee info |

**Response `data`:**
```json
{
  "email": "employee@test.com",
  "role": "EMPLOYEE",
  "employeeId": 1,
  "name": "John Doe"
}
```

---

### Employees

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List employees (search, pagination, sort) |
| GET | `/api/employees/options` | Dropdown list (`id`, `name`) for task assignee |
| GET | `/api/employees/{id}` | Get employee by id |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee (blocked if tasks assigned) |

**Query params (list):** `search`, `page`, `size`, `sort` (e.g. `sort=name,asc`)

**Create / update body:**
```json
{
  "name": "John Doe",
  "email": "john@company.com",
  "department": "Engineering"
}
```

---

### Tasks

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | List tasks (filters, search, pagination, sort) |
| GET | `/api/tasks/{id}` | Get task details |
| POST | `/api/tasks` | Create task |
| PUT | `/api/tasks/{id}` | Update / reassign task |
| DELETE | `/api/tasks/{id}` | Delete task |
| PATCH | `/api/tasks/{id}/status` | Update task status (employee) |

**Query params (list):** `assignedEmployeeId`, `status`, `priority`, `search`, `page`, `size`, `sort`

**Create / update body:**
```json
{
  "title": "Fix login bug",
  "description": "Resolve mobile auth issue",
  "priority": "HIGH",
  "dueDate": "2026-12-01",
  "assignedEmployeeId": 1
}
```

**Status update body:**
```json
{ "status": "IN_PROGRESS" }
```

Status values: `PENDING`, `IN_PROGRESS`, `COMPLETED`  
Priority values: `LOW`, `MEDIUM`, `HIGH`

---

### Dashboard

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard/stats` | Manager dashboard metrics + chart data |

**Response `data`:**
```json
{
  "totalEmployees": 3,
  "totalTasks": 4,
  "pendingTasks": 2,
  "completedTasks": 1,
  "statusDistribution": {
    "PENDING": 2,
    "IN_PROGRESS": 1,
    "COMPLETED": 1
  }
}
```

---

## Quick Test (curl)

```bash
curl http://localhost:8080/api/dashboard/stats
curl http://localhost:8080/api/employees
curl http://localhost:8080/api/tasks?assignedEmployeeId=1
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"manager@test.com\"}"
```

---

## Project Structure

```
Employee-TMD/
├── backend/          # Spring Boot API
│   └── src/main/java/com/employeetmd/
│       ├── controller/   # Auth, Employee, Task, Dashboard
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── enums/
└── README.md
```

---

## Screenshots

_Coming soon — frontend UI screenshots will be added here._
