@echo off
:: ==============================================
TITLE "-STATUS of MINIO SERVICES"
REM << RUN THIS SCRIPT AS ADMINISTRATOR >>
REM Shows Status of MINIO Windows Services
:: ==============================================

:: SET ShowAdminInfo=5
net session >nul 2>nul&if errorlevel 1  Batch_Admin "%~0" %*

:: Gets status of MinIO
echo "MinIO Status: "
%~dp0nssm.exe status MinIO
echo.

PAUSE

EXIT /B %ERRORLEVEL%