<<<<<<< HEAD
# UAM Hub

UAM Hub is a Spring Boot operations dashboard for managing a modern urban air mobility network. It brings together drone fleet administration, landing pad monitoring, air corridor tracking, docking workflows, and financial reporting in a single command center.

## Tech stack

- Java 21
- Spring Boot 3.3.4
- Spring Web MVC
- Spring Data JPA and Hibernate
- MySQL
- Thymeleaf templates
- Maven
- HTML, CSS, and vanilla JavaScript

## Features

- Drone and operator management
- Landing pad status tracking
- Airspace corridor monitoring
- Docking and check-in/check-out workflow
- Product, invoicing, vendor, and payment records
- Budget and financial ledger tracking
- Executive reporting dashboards

## Project structure

- src/main/java/com/uam/hub - application code
- src/main/resources/templates - Thymeleaf pages
- src/main/resources/static - frontend assets
- src/main/resources/application.properties - runtime configuration

## Prerequisites

- Java 21
- Maven
- MySQL running locally

## Local setup

1. Create a MySQL database named uam_hub.
2. Copy .env.example to a local environment file and set your values.
3. Start the app:

```powershell
mvn spring-boot:run
```

4. Open the app in a browser:

- http://localhost:8081

## Environment variables

The app reads values from environment variables with safe defaults. Do not commit secrets to source control.

```bash
DB_URL=jdbc:mysql://localhost:3306/uam_hub?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_secure_password
PORT=8081
```

## Screenshots

Add screenshots of the following views:

- Dashboard overview
- Airspace corridor radar
- Drone fleet management
- Docking activity
- Finance and reports pages

## Notes

This project is designed as an internal operations dashboard with a realistic UAM workflow around drone logistics, airspace control, and financial operations.
=======
# SpringJPA - Foodie Express

Spring Boot + Spring Data JPA + MySQL + Thymeleaf food ordering application.

## Requirements
- Java 25
- MySQL Server 8.0 running as `MySQL80`

## Run on Windows (recommended)
1. Make sure MySQL Server is running.
2. Make sure the database user is `root` and know its password.
3. Double-click `run-local.bat`, or from PowerShell run:

```powershell
.\run-local.bat
```

The script asks for the MySQL root password and does **not** save it in the project.

The JDBC URL uses `createDatabaseIfNotExist=true`, so MySQL can create `fooddb` automatically when the root account has permission to create databases.

The application starts on:

`http://localhost:8080`

## Manual PowerShell run

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
.\mvnw.cmd clean spring-boot:run
```

Do not commit the password or put it in `application.properties`.

## API endpoints
- `GET /api/food`
- `GET /api/food/{id}`
- `POST /api/food`
- `PUT /api/food/{id}`
- `DELETE /api/food/{id}`

## Web pages
- `/`
- `/menu`
- `/cart`
- `/orders`
>>>>>>> 53f02a9ff30790ab5ffb4701bd68ec5caefb4573
