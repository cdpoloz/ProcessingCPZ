# cpzProcessing

## Elementos interactivos simples para el control de una interfaz gráfica de usuario.

Con el pasar de los años he tratado de sacarle el máximo provecho a [Processing](https://processing.org/) para crear
interfaces gráficas interactivas implementando controles personalizados y animaciones. Si bien el IDE de Processing
muestra mejoras constantes con cada nueva versión, decidí preparar esta plantilla de proyecto Java para que pueda ser
utilizado en otros entornos habiéndolo ya probado en IntelliJ CE y Apache Netbeans. Esto con el fin de aprovechar al
máximo las herramientas de cada uno de estos así como del lenguaje Java mismo. Ya era hora de compartir lo aprendido.

## 📂 Estructura del proyecto

- **src/**: Código fuente Java organizado en paquetes:
    - com.cpz.processing.Bean
    - com.cpz.processing.Ejemplos
    - com.cpz.processing.Input
    - com.cpz.processing.Interfaces
    - com.cpz.processing.Main
    - com.cpz.processing.UI
    - com.cpz.processing.Util


- **data/**: Recursos gráficos usados en los sketchs de ejemplo.

- **scripts/**: Scripts recomendados para ejecutar el proyecto (ver sección [Ejecutar el proyecto](#-ejecutar-el-proyecto)).

- **LICENSE**: Archivo de licencia Apache 2.0 que regula el uso y distribución del proyecto.


- **.gitignore**: Configuración para ignorar archivos generados, binarios, configuraciones de IDE, etc.


- **META-INF/MANIFEST.MF**: Archivo manifest indicando la clase principal para ejecución (`com.cpz.processing.Main.Launcher`).

## ⚙️ Requisitos y dependencias

- Java 17 o superior (compatible con versiones anteriores)
- Librería Processing para Java (versión usada en el proyecto incluida como dependencia)

## 🛠️ Compilar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/cdpoloz/cpzProcessing.git
cd cpzProcessing
```

2. Compila el proyecto (ejemplo con javac):

```bash
javac -d bin -sourcepath src src/com.cpz.processing.Main/Launcher.java
```

3. Ejecuta la aplicación:

```bash
java --enable-native-access=ALL-UNNAMED -cp bin com.cpz.processing.Main.Launcher
```

4. Asegúrate de que la carpeta `data` esté en el directorio raíz del proyecto para que los recursos gráficos puedan
   cargarse correctamente.


## ▶️ Ejecutar el proyecto

✅ Uso recomendado (con scripts incluidos):

Para facilitar la ejecución con todos los argumentos necesarios, puedes utilizar los scripts disponibles en la carpeta
scripts/:

- Linux / macOS:
```bash
chmod +x scripts/ejecutar.sh
./scripts/ejecutar.sh
```
- Windows:
```bash
scripts\ejecutar.bat
```
Estos scripts ya incluyen el argumento necesario para acceso nativo:
```bash
--enable-native-access=ALL-UNNAMED
```

⚠️ Ejecutar manualmente:

Si prefieres ejecutarlo manualmente, asegúrate de incluir este argumento:
```bash
java --enable-native-access=ALL-UNNAMED -jar cpzProcessing.jar
```

## 🎨 Uso

En la clase Launcher se debe elegir el sketch que se desee ejecutar, puedes elegir uno del paquete 'Ejemplos' o crear
uno propio. Por defecto se ejecuta el ejemplo 'SimpleUI', espero poder agregar más ejemplos en un futuro no muy lejano.

## 📜 Licencia

Este proyecto está licenciado bajo la Apache License 2.0. Consulta el archivo LICENSE para más detalles.

## ✉️ Contacto

Para dudas, sugerencias o reportes de error, por favor abre un issue en este repositorio o contacta a:

**[cdpoloz](https://github.com/cdpoloz)** (autor)

## 🚀 Releases

Para obtener el archivo .jar compilado para usar como librería externa, revisa la sección de Releases o contacta al
autor si aún no está disponible.

## 📦 Usar como librería externa

El archivo cpzProcessing.jar también puede importarse en otros proyectos Java para utilizar sus clases y métodos. Puedes agregarlo como dependencia en tu entorno de desarrollo favorito (por ejemplo, IntelliJ IDEA, NetBeans o Eclipse).

Ejemplo (compilación manual):

```bash
javac -cp cpzProcessing.jar MiClase.java
```

Ejemplo (ejecución manual):

```bash
java --enable-native-access=ALL-UNNAMED -cp cpzProcessing.jar:. MiClase
```


¡Gracias por interesarte en este proyecto!
