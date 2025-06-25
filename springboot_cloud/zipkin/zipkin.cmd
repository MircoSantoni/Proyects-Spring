@echo off
echo === Configurando Zipkin con MySQL ===

REM Configuracion MySQL
set STORAGE_TYPE=mysql
set MYSQL_PORT=3306
set MYSQL_DB=zipkin
set MYSQL_USER=zipkin
set MYSQL_PASS=Zipkin123

echo STORAGE_TYPE=%STORAGE_TYPE%
echo MYSQL_DB=%MYSQL_DB%
echo MYSQL_USER=%MYSQL_USER%
echo MYSQL_USE_SSL=%MYSQL_USE_SSL%
echo MYSQL_MAX_CONNECTIONS=%MYSQL_MAX_CONNECTIONS%
echo ========================================

echo Iniciando Zipkin Server...
java -jar zipkin-server-3.4.1-exec.jar