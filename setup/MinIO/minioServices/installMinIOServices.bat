@echo off
:: ==============================================
TITLE "-INSTALL MINIO SERVICES"
REM << RUN THIS SCRIPT AS ADMINISTRATOR >>
REM Installs MinIO Services as Windows Services
:: ==============================================

:: SET ShowAdminInfo=5
net session >nul 2>nul&if errorlevel 1  Batch_Admin "%~0" %*

:: Installs MinIO
%~dp0nssm.exe install MinIO C:\Simple-Project-Manager\MinIO\start.bat

%~dp0nssm.exe set MinIO Description "MinIO Service"
echo.

PAUSE

EXIT /B %ERRORLEVEL%