@echo off
:: ==============================================
TITLE "-START MINIO SERVICES"
REM << RUN THIS SCRIPT AS ADMINISTRATOR >>
REM Starts MINIO Windows Services
:: ==============================================

:: SET ShowAdminInfo=5
net session >nul 2>nul&if errorlevel 1  Batch_Admin "%~0" %*

:: Starts MinIO
%~dp0nssm.exe start MinIO

echo.

PAUSE

EXIT /B %ERRORLEVEL%