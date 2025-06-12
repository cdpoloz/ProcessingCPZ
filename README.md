# cpzProcessingUI
## Elementos interactivos simples para el control de una interfaz gráfica de usuario.
##

Este proyecto es una aplicación desarrollada en Java que utiliza la librería [Processing](https://processing.org/) para crear una interfaz gráfica interactiva con controles personalizados y animaciones basadas en ruido Perlin.

## Estructura del proyecto

- **src/**: Código fuente Java organizado en paquetes:
    - com.cpz.processingUI.Bean
    - com.cpz.processingUI.Input
    - com.cpz.processingUI.Interfaces
    - com.cpz.processingUI.Main
    - com.cpz.processingUI.UI
    - com.cpz.processingUI.Util

- **data/**: Recursos gráficos usados en la aplicación, incluyendo las imágenes SVG (`circulo.svg`, `cuadrado.svg`) usadas en el sketch de ejemplo.

- **LICENSE**: Archivo de licencia Apache 2.0 que regula el uso y distribución del proyecto.

- **.gitignore**: Configuración para ignorar archivos generados, binarios, configuraciones de IDE, etc.

- **META-INF/MANIFEST.MF**: Archivo manifest indicando la clase principal para ejecución (`Main.com.cpz.Launcher`).

## Requisitos y dependencias

- Java 17 o superior (compatible con versiones anteriores)
- Librería Processing para Java (versión usada en el proyecto incluida como dependencia)

## Instrucciones para compilar y ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/cdpoloz/cpzProcessingUI.git
   cd cpzProcessingUI
   ```

2. Compila el proyecto (ejemplo con javac):

   ```bash
   javac -d bin -sourcepath src src/com.cpz.processingUI.Main/Launcher.java
   ```

3. Ejecuta la aplicación:

   ```bash
   java -cp bin Main.com.cpz.Launcher
   ```

4. Asegúrate de que la carpeta `data` esté en el directorio raíz del proyecto para que los recursos gráficos puedan cargarse correctamente.


## Uso

La aplicación muestra una interfaz gráfica con varios controles personalizados (barras, switches, labels) y animaciones basadas en ruido Perlin para generar movimientos naturales.

## Licencia

Este proyecto está licenciado bajo la Apache License 2.0. Consulta el archivo LICENSE para más detalles.

## Contacto

Para dudas, sugerencias o reportes de error, por favor abre un issue en este repositorio o contacta a:

**cdpoloz** (autor)

## Notas adicionales

Para obtener el archivo .jar compilado para usar como librería externa, revisa la sección de Releases (próximamente) o contacta al autor.

¡Gracias por interesarte en este proyecto!
