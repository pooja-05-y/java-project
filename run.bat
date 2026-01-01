@echo off
echo 🐾 Starting Digital Pet...
echo ==========================

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven first.
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java first.
    pause
    exit /b 1
)

echo ✅ Dependencies found!
echo 🚀 Launching Digital Pet Console App...
echo.

REM Run the console application
mvn clean compile exec:java -Dexec.mainClass="digitalpet.ConsoleApp" -q

echo.
echo 👋 Thanks for playing with your Digital Pet!
pause