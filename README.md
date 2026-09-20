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
