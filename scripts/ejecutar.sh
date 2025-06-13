#!/bin/bash

# ----------------------------------------------------------
# Script para ejecutar el proyecto cpzProcessing
#
# IMPORTANTE:
# - Asegúrate de que la ruta del archivo JAR sea correcta.
# - Por defecto, este script asume que el JAR está en el directorio padre (..)
# - La carpeta 'data' con los recursos debe estar al mismo nivel que el JAR.
# ----------------------------------------------------------

java --enable-native-access=ALL-UNNAMED -jar ../cpzProcessing.jar
