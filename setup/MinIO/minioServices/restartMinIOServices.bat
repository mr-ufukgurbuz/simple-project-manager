@echo off
:: -----------------------------------------------
TITLE "-RESTART MINIO SERVICES"
REM << RUN THIS SCRIPT AS ADMINISTRATOR >>
REM Restarts MINIO Windows Services
:: -----------------------------------------------

:: SET ShowAdminInfo=5
net session >nul 2>nul&if errorlevel 1  Batch_Admin "%~0" %*

:: Stops MinIO
%~dp0nssm.exe stop MinIO

PAUSE

EXIT /B %ERRORLEVEL%