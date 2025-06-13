# Scripts de ejecución

Esta carpeta contiene scripts recomendados para ejecutar el proyecto **cpzProcessing** con los argumentos necesarios para la JVM.

## 📂 Archivos disponibles

- `ejecutar.sh` → Linux/macOS
- `ejecutar.bat` → Windows

## ✅ Requisitos previos

- **Linux/macOS**: Asegúrate de otorgar permisos de ejecución al script:

```bash
chmod +x scripts/ejecutar.sh
```

## ⚙️ Configuración

- Ruta del JAR: Los scripts están configurados para ejecutar un archivo llamado cpzProcessing.jar ubicado en el
directorio padre (..).  Si cambias la ubicación o el nombre del JAR, actualiza el script correspondiente.

- Carpeta 'data': Los recursos gráficos (data/) deben estar al mismo nivel que el JAR para que se carguen correctamente
durante la ejecución.

## 🏃 Ejemplo de uso

```bash
./scripts/ejecutar.sh
```
o en Windows:
```bash
scripts/ejecutar.bat
```

Recuerda que también puedes ejecutar manualmente con:

```bash
java --enable-native-access=ALL-UNNAMED -jar cpzProcessing.jar
```
