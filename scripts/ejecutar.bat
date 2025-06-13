@echo off
REM ----------------------------------------------------------
REM Script para ejecutar el proyecto cpzProcessing
REM
REM IMPORTANTE:
REM - Asegúrate de que la ruta del archivo JAR sea correcta.
REM - Por defecto, este script asume que el JAR está en el directorio padre (..)
REM - La carpeta 'data' con los recursos debe estar al mismo nivel que el JAR.
REM ----------------------------------------------------------

java --enable-native-access=ALL-UNNAMED -jar ..\cpzProcessing.jar
pause
