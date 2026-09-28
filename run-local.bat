@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo   SpringJPA - Local Run
echo ========================================
echo.
set /p DB_PASSWORD=Enter MySQL root password: 
if "%DB_PASSWORD%"=="" (
  echo.
  echo ERROR: MySQL password cannot be empty.
  exit /b 1
)
set DB_USERNAME=root

echo.
echo Starting Spring Boot...
call mvnw.cmd clean spring-boot:run
set EXITCODE=%ERRORLEVEL%
set DB_PASSWORD=
set DB_USERNAME=
exit /b %EXITCODE%
