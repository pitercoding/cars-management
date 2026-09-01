# Cars Management

**Cars Management** is a full-stack application for managing car sales, brands, accessories, and owners. Users can perform CRUD operations on cars, brands, owners, and accessories, with JWT-based authentication and role-based permissions for admins.

The application includes **DTO-based request/response contracts**, **database migrations with Flyway**, **centralized error handling**, **robust validation**, and a **CI pipeline** that builds and tests both the backend and frontend on every push.

## How to Access the Project

The production stack is hosted across:

- **Backend:** Render ([https://cars-management-co0d.onrender.com](https://cars-management-co0d.onrender.com)) (hibernates on free tier)
- **Frontend:** Vercel ([https://cars-management-drab.vercel.app](https://cars-management-drab.vercel.app))
- **Database:** Aiven MySQL (free tier)
- **Deployment practice:** Initially deployed on AWS for learning, but the final production setup uses Render + Vercel + Aiven to avoid costs.

### Steps to run locally

1. Clone the repository:

```bash
git clone https://github.com/pitercoding/cars-management.git
cd cars-management
```

1. Backend (requires a running MySQL instance; schema is created automatically by Flyway on startup):

```cmd
cd backend
mvnw spring-boot:run
```

```powershell
cd backend
.\mvnw spring-boot:run
```

```bash
cd backend
./mvnw spring-boot:run
```

1. Frontend (copy `src/environments/environment.example.ts` to `environment.development.ts` and point `SERVER` at your backend):

```bash
cd frontend
npm install
ng serve
```

## Motivation

As a Computer Science student, this project was created to **practice full-stack development** by building a realistic management system.

It allowed me to apply concepts in **Spring Boot, Angular, REST APIs, authentication, database modeling, frontend UI/UX, and cloud deployment**.

## Learning Points

During development, I strengthened skills in:

- **Frontend:** Angular (standalone components, the esbuild-based application builder), TypeScript, SCSS, MDB Angular UI Kit, routing, functional HTTP interceptors and route guards.
- **Backend:** Spring Boot, Spring Security, JWT authentication, DTO-based API contracts, Flyway migrations, centralized exception handling, constructor-based dependency injection.
- **Database:** MySQL, versioned schema migrations, JPA relationships.
- **Deployment & Cloud:** Experience deploying to AWS, then using Render (backend, via Docker), Vercel (frontend), and Aiven (MySQL) for the final free-tier setup.
- **Testing, Validation & CI:** Unit tests with JUnit/Mockito, code coverage with JaCoCo, frontend Jasmine/Karma specs, GitHub Actions pipeline running both suites on every push/PR.

---

## Application Structure

| Layer      | Technology                         | Main Function                                                               |
| ---------- | ---------------------------------- | --------------------------------------------------------------------------- |
| Frontend   | Angular 22 + TypeScript            | UI for managing cars, brands, owners, accessories with forms and lists      |
| Backend    | Spring Boot 4                      | REST API with DTOs, logging, authentication, validation, exception handling |
| Database   | MySQL + Flyway                     | Stores cars, owners, brands, accessories; schema managed by migrations      |
| Auth       | JWT + Spring Security              | Secure login, admin role management, password change                        |
| CI/CD      | GitHub Actions                     | Automated build + test for backend and frontend on push/PR                  |
| Deployment | Render (Docker) / Vercel / Aiven   | Cloud deployment and hosting                                                |

---

## Technologies & Tools

### Frontend (Angular)

- Angular 22 (standalone components, esbuild/Vite-based `application` builder)
- MDB Angular UI Kit + Font Awesome icons
- SweetAlert2 for alerts, confirmations, and modals
- Chart.js for data visualization
- SCSS / CSS3, mobile-first responsive layout
- Functional HTTP interceptor (JWT attachment, 401/403 handling)
- Route guards (`authGuard`, `adminGuard`, `loginGuard`)
- Components for Cars, Owners, Brands, Accessories, Users
- Karma + Jasmine unit tests

### Backend (Spring Boot)

- Spring Boot 4.0.8 on Java 21
- Spring Security + JWT (jjwt 0.13.0)
- REST APIs (Cars, Brands, Owners, Accessories, Users) built around dedicated request/response DTOs
- Flyway-managed schema migrations (`db/migration`)
- Centralized exception handling (`GlobalExceptionHandler`) with standardized `ErrorResponse` payloads and a custom `CarDeletionException`
- Bean Validation and constructor-injected service/repository layers
- Public `/health` endpoint for uptime checks
- JUnit 5 / Mockito unit tests with JaCoCo coverage reporting

### Database

- MySQL, schema versioned via Flyway migrations
- Entity relationships: Many-to-Many (Cars ↔ Accessories), One-to-Many (Brand → Cars), One-to-One (Owner ↔ Car)

### CI/CD & Deployment

- GitHub Actions pipeline: backend build+test against a throwaway MySQL service container; frontend `npm ci` + build + headless Karma tests
- Backend containerized with a multi-stage Dockerfile, deployed on Render
- Frontend deployed on Vercel
- Database hosted on Aiven MySQL (free tier)
- Initial deployment practice on AWS (later replaced to avoid costs)

---

## Screenshots & Visuals

### Login

![Login Page](frontend/src/assets/screenshots/login.png)

### Brands Management

![Brands Management](frontend/src/assets/screenshots/brands-list.png)

### Accessories Management

![Accessories Management](frontend/src/assets/screenshots/accessories-list.png)

### Cars List

![Cars List](frontend/src/assets/screenshots/cars-list.png)

### Car Details Modal

![Car Details](frontend/src/assets/screenshots/cars-details.png)

### User Management

![User Management](frontend/src/assets/screenshots/users-list.png)

---

## Application Flow

```text
User → Frontend (Angular, standalone components + guards)
↓
REST API (Spring Boot, JWT filter, DTO validation, centralized error handling, logs)
↓
Database (MySQL, schema versioned by Flyway)
↑
(Backend maps entities to DTOs and returns results)
```

## Main API Endpoints

All endpoints below (except `/api/login` and `/health`) require a valid JWT in the `Authorization: Bearer <token>` header. User management endpoints additionally require the `ADMIN` role.

| Resource   | Base path          | Endpoints                                                                                                                                   |
| ---------- | ------------------ | ---------------------------------------------------------------------------------------------                                               |
| Cars       | `/api/cars`        | `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`, `GET /findByName`, `GET /findByBrand`, `GET /findByManufactureYearGreaterThan` |
| Brands     | `/api/brands`      | `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`                                                                                 |
| Owners     | `/api/owners`      | `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`, `GET /available`                                                               |
| Accessories| `/api/accessories` | `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`                                                                                 |
| Users      | `/api/users`       | `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}` (admin only), `PUT /me/password` (any authenticated user)                       |
| Auth       | `/api/login`       | `POST /` (public, returns a JWT)                                                                                                            |
| Health     | `/health`          | `GET /` (public)                                                                                                                            |

## Current Status

| Area           | Status         | Description                                                                                         |
| -------------- | -------------- | ------------------------------------------------------------------------------------------------    |
| Backend        | ✅ Completed   | CRUD via DTOs, Flyway migrations, validation, JWT auth, centralized exception handling              |
| Frontend       | ✅ Completed   | Full management UI for cars, brands, owners, accessories, users (Angular 22, standalone components) |
| Integration    | ✅ Tested      | Frontend ↔ Backend communication via HTTP + JWT interceptor                                         |
| Database       | ✅ Operational | Connected and schema-versioned via Flyway                                                           |
| Authentication | ✅ Implemented | JWT + role-based UI/route guards + self-service password change                                     |
| CI/CD          | ✅ Implemented | GitHub Actions builds and tests backend + frontend on every push/PR                                 |
| Deployment     | ✅ Done        | Backend → Render (Docker), Frontend → Vercel, Database → Aiven, AWS deployment experience           |

## Folder Structure

```bash
cars-management/
├─ backend/
│  ├─ src/main/java/com/cars/backend/
│  │  ├─ auth/                  # Authentication module (login, users, DTOs)
│  │  ├─ config/                # Security, CORS, JWT filter/generator
│  │  ├─ controller/            # REST controllers (cars, brands, owners, accessories, health)
│  │  ├─ dto/                   # Request/response DTOs + ErrorResponse
│  │  ├─ entity/                # JPA entities (Car, Brand, Owner, Accessory)
│  │  ├─ exception/             # GlobalExceptionHandler and custom exceptions
│  │  ├─ repository/            # Spring Data JPA repositories
│  │  ├─ service/                # Business logic services
│  │  └─ BackendApplication.java
│  ├─ src/main/resources/db/migration/  # Flyway schema migrations
│  └─ Dockerfile
├─ frontend/
│  ├─ src/app/
│  │  ├─ auth/                  # Login/user models, guards, interceptor, auth services
│  │  ├─ components/            # CRUD components (cars, brands, owners, accessories, layout/menu)
│  │  ├─ models/                # TypeScript models
│  │  ├─ services/               # HTTP services
│  │  ├─ app.routes.ts          # Routing configuration
│  │  └─ app.ts/html/scss       # Main app files
│  ├─ src/assets/                # Logo, screenshots and other static files
│  └─ src/environments/          # Environment configs (dev/prod, gitignored; see environment.example.ts)
├─ .github/workflows/ci.yml     # CI pipeline (backend + frontend build/test)
├─ .gitignore
└─ README.md
```

## License

This project is licensed under the **MIT License**.

## Author

**Piter Gomes** — Computer Science Student & Full-Stack Developer

[Email](mailto:piterg.bio@gmail.com) | [LinkedIn](https://www.linkedin.com/in/piter-gomes-4a39281a1/) | [GitHub](https://github.com/pitercoding) | [Portfolio](https://portfolio-pitergomes.vercel.app/)
