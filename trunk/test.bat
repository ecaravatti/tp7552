@echo off

:input
set TEST_DIR=
set /P TEST_DIR=Ingrese el directorio destino del resultado de los tests (path completo): %=%
if "%TEST_DIR%"=="" goto input

..\ant\ant\bin\ant tests junitreport
