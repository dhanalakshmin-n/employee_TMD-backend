# Employee Task Management Dashboard

A full-stack task management dashboard where managers create and assign tasks to employees, and employees update task statuses and track progress.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17, Spring Boot 3.3, Spring Data JPA |
| Database | PostgreSQL |
| Frontend | React (coming soon) |

---

## Setup Instructions

### Prerequisites

Install the following before running the project:

| Tool | Version | Check |
|------|---------|-------|
| Java JDK | 17 | `java -version` |
| Maven | 3.8+ | `mvn -version` |
| PostgreSQL | 14+ | `psql --version` |

> **Note:** Spring Boot 3.x requires **Java 17 or higher**. Ensure your IDE run configuration and `JAVA_HOME` point to JDK 17.

---

### 1. Clone the repository

```bash
git clone <repository-url>
cd Employee-TMD
```

---

### 2. Set up PostgreSQL

1. Start the PostgreSQL service on your machine.
2. Create the database:

```sql
CREATE DATABASE employee_tmd;
```

3. Confirm you can connect (default user is often `postgres`):

```bash
psql -U postgres -d employee_tmd
```

---

### 3. Configure database credentials

Database settings live in [`backend/src/main/resources/application.properties`](backend/src/main/resources/application.properties):

```properties
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/employee_tmd}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

**Option A — Edit defaults in `application.properties`** (simplest for local dev)

Change `postgres` / password to match your local Postgres setup.

**Option B — Environment variables via IDE run config** (recommended)

In your IDE run configuration for `EmployeeTmdApplication`, add:

| Variable | Example value |
|----------|---------------|
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/employee_tmd` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `your_password` |

**Option C — Terminal (PowerShell)**

```powershell
$env:DATABASE_URL="jdbc:postgresql://localhost:5432/employee_tmd"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

---

### 4. Configure Java 17 in your IDE

**IntelliJ / Cursor (Java)**

1. Install JDK 17 (e.g. [Eclipse Temurin 17](https://adoptium.net/)).
2. Set **Project SDK** and **Run Configuration JRE** to Java 17.
3. VS Code / Cursor: `Ctrl+Shift+P` → **Java: Configure Java Runtime** → add JDK 17 as default.

Verify:

```bash
java -version    # should show 17.x.x
mvn -version     # should show Java version: 17
```

---

### 5. Run the backend

```bash
cd backend
mvn spring-boot:run
```

Or run `EmployeeTmdApplication.java` directly from your IDE.

The API starts at: **http://localhost:8080**

On first run, JPA auto-creates tables (`employees`, `tasks`) from entity classes (`ddl-auto=update`).

---

### 6. Verify the server is running

Check the console for:

```
Started EmployeeTmdApplication in X.XXX seconds
```

If you see a database connection error:

| Error | Fix |
|-------|-----|
| `database "employee_tmd" does not exist` | Run `CREATE DATABASE employee_tmd;` |
| `password authentication failed` | Fix `DB_PASSWORD` in run config or properties |
| `release version 17 not supported` | Switch IDE / Maven to JDK 17 |

---

## Project Structure (Backend)

```
backend/
├── pom.xml
├── src/main/java/com/employeetmd/
│   ├── EmployeeTmdApplication.java   # Entry point
│   ├── enums/                        # Priority, TaskStatus
│   ├── entity/                       # Employee, Task (coming soon)
│   ├── repository/
│   ├── service/
│   ├── controller/
│   └── dto/
└── src/main/resources/
    └── application.properties
```

---

## API Documentation

_Coming soon — endpoints will be documented here as they are implemented._

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List employees |
| POST | `/api/employees` | Create employee |
| GET | `/api/tasks` | List tasks |
| POST | `/api/tasks` | Create task |
| PATCH | `/api/tasks/{id}/status` | Update task status |
| GET | `/api/dashboard/stats` | Dashboard metrics |

---

## Screenshots

_Coming soon._

---

## Mock Login (Frontend)

| Role | Email |
|------|-------|
| Manager | `manager@test.com` |
| Employee | `employee@test.com` |

No password required. Role is stored in browser local storage.
