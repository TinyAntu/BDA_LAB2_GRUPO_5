@echo off
REM Navigate to the folder where the .bat file is located
cd /d "%~dp0"

REM Execute the dbCreate.sql file
psql -U postgres -h localhost -p 5432 -f dbCreate.sql
if %errorlevel% neq 0 (
    echo Error occurred while executing dbCreate.sql
    pause
    exit /b %errorlevel%
)

REM Execute the dump.sql file
psql -U postgres -h localhost -d lab2bda -f dump.sql
if %errorlevel% neq 0 (
    echo Error occurred while executing dump.sql
    pause
    exit /b %errorlevel%
)

echo Commands executed successfully!
pause
