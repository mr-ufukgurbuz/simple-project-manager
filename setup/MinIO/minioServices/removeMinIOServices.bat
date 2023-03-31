@echo off
:: ==============================================
TITLE "-REMOVE MINIO SERVICES"
REM << RUN THIS SCRIPT AS ADMINISTRATOR >>
REM Removes MINIO Windows Services
:: ==============================================

:: SET ShowAdminInfo=5
net session >nul 2>nul&if errorlevel 1  Batch_Admin "%~0" %*

:: Stops MinIO 
%~dp0nssm.exe stop MinIO


:: Removes MinIO 
%~dp0nssm.exe remove MinIO

echo.

EXIT /B %ERRORLEVEL%