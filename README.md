## 1. Guess My Rule

Guess My Rule es una aplicación educativa interactiva cuyo objetivo principal es fortalecer el pensamiento lógico-matemático en los
estudiantes. El jugador introduce números en el sistema, el cual aplica una regla matemática oculta y devuelve un resultado. A partir de
la observación de múltiples entradas y sus respectivas salidas, el jugador debe deducir cuál es esa regla. Por ejemplo, si al ingresar 3
el sistema responde 9, y al ingresar 5 responde 25, la regla podría ser elevar al cuadrado.
El proyecto fue desarrollado como trabajo grupal para la asignatura de Practica Apicada Sistemas del Politécnico Grancolombiano,
siguiendo la metodología ágil Scrum con entregas por cortes. La arquitectura elegida es el patrón Modelo-Vista-Controlador (MVC), que
separa claramente las responsabilidades de la aplicación y facilita el mantenimiento y escalabilidad del código.
La identificación de requerimientos se llevó a cabo mediante observación directa del juego original, revisión de recursos en línea sobre
la mecánica del juego y análisis de los objetivos pedagógicos que se desean alcanzar con cada nivel de dificultad.


## 2. Tecnologías y Herramientas Utilizadas

**- Java 25 :** Lenguaje principal de programación. Provee el tipado estático, la orientación a objetos y el ecosistema de librerías
necesario.

**- JavaFX 13 + :** Framework para la construcción de interfaces gráficas de escritorio en Java. Permite separar la interfaz (FXML) del
controlador (Java).

**- Maven :** Herramienta de gestión de dependencias y automatización del build. Facilita la instalación del proyecto con un solo comando.

**- MySQL 8 :** Sistema de gestión de bases de datos relacional para almacenar jugadores, puntajes y partidas de forma persistente.

**- GitHub :** Plataforma de control de versiones y colaboración. Almacena el código fuente y todos los artefactos del proyecto.

**- Jira :** Herramienta de gestión del proyecto con metodología Scrum: historias de usuario, sprints y tablero Kanban.


## 3. Arquitectura
El patrón Modelo-Vista-Controlador divide la aplicación en tres capas con responsabilidades bien definidas, lo cual permite que cada
integrante del equipo trabaje en una capa sin afectar directamente el trabajo de los demás.

**- Modelo (Model):** El Modelo contiene toda la lógica de negocio de la aplicación. Está compuesto por dos tipos de clases: las
entidades (como JugadorModel), que representan los objetos del dominio del problema, y las clases de lógica (como JugadorLogica), que
contienen las reglas de validación y procesamiento de datos. En fases posteriores del proyecto, el Modelo también incluirá clases DAO
(Data Access Object) para la comunicación con la base de datos MySQL.

**- Vista (FXML):** La capa de Vista está compuesta por archivos FXML, un formato basado en XML propio de JavaFX que describe la
estructura y apariencia de cada pantalla de manera declarativa. Esta capa no contiene lógica de negocio: únicamente define qué elementos
visuales existen, su disposición en pantalla y qué método del controlador debe ser invocado ante cada evento de usuario (como un clic en
un botón). Esto garantiza que los diseñadores puedan modificar la interfaz sin tocar código Java.

**- Controlador (Controller):** Los controladores son clases Java anotadas con @FXML que actúan como intermediarios entre la Vista y el
Modelo. Reciben los eventos generados por el usuario (clics, escritura en campos de texto, movimientos del ratón), invocan la lógica de
negocio correspondiente en el Modelo y actualizan la Vista con los resultados. Los controladores no contienen reglas de negocio propias:
su rol es coordinar, no procesar.

Ver [diagrama de arquitectura](docs/design/arquitectura.png)

## 4. Instalación y Ejecucción
Siga estos pasos en orden para configurar y ejecutar el proyecto en un entorno nuevo:
•	 Tener instalado JDK 21 o superior. Verificar con: java -versionRequisito 1:
•	 Tener instalado Maven 3.9 o superior. Verificar con: mvn -versionRequisito 2:
•	 Tener instalado MySQL 8 y el servidor en ejecución.Requisito 3:

**Paso 1 — Clonar el repositorio:**
git clone https://github.com/Pato214151/guess-my-rule.git
cd guess-my-rule

**Paso 2 — Crear la base de datos en MySQL:**
CREATE DATABASE guessrule;

**Paso 3 — Instalar dependencias y compilar:**
mvn clean install

**Paso 4 — Ejecutar la aplicación:**
mvn javafx:run


## Estructura del Proyecto

    # Entidades y DAO
    # Controladores JavaFX
    # Clase principal
    # Archivos FXML
    # Documentación
    # Diagramas y diseños

## Gestión del Proyecto
- **Repositorio:** GitHub
- **Gestión:** Jira con Scrum
- **Ramas:** main (estable), develop (desarrollo)
