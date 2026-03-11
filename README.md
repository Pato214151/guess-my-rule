# Guess My Rule
Guess My Rule es una aplicación educativa interactiva cuyo objetivo principal es fortalecer el pensamiento lógico-matemático en los estudiantes. El jugador introduce números en el sistema, el cual aplica una regla matemática oculta y devuelve un resultado. A partir de la observación de múltiples entradas y sus respectivas salidas, el jugador debe deducir cuál es esa regla. Por ejemplo, si al ingresar 3 el sistema responde 9, y al ingresar 5 responde 25, la regla podría ser elevar al cuadrado.
El proyecto fue desarrollado como trabajo grupal para la asignatura de Practica Apicada Sistemas del Politécnico Grancolombiano, siguiendo la metodología ágil Scrum con entregas por cortes. La arquitectura elegida es el patrón Modelo-Vista-Controlador (MVC), que separa claramente las responsabilidades de la aplicación y facilita el mantenimiento y escalabilidad del código.
La identificación de requerimientos se llevó a cabo mediante observación directa del juego original, revisión de recursos en línea sobre la mecánica del juego y análisis de los objetivos pedagógicos que se desean alcanzar con cada nivel de dificultad.

## Tecnologías
- Java 25
- JavaFX 21
- Maven
- MySQL 8
- GitHub

## Requisitos Previos
- JDK 21 o superior
- Maven 3.9+
- MySQL 8+
- IDE con soporte Maven (Eclipse o VS Code)

## Instalación
### 1. Clonar el repositorio
bash
git clone https://github.com/Pato214151/guess-my-rule.git
cd guess-my-rule


### 2. Configurar base de datos
sql
CREATE DATABASE guessrule;


### 3. Instalar dependencias
bash
mvn clean install


### 4. Ejecutar el proyecto
bash
mvn javafx:run


## Estructura del Proyecto

    # Entidades y DAO
    # Controladores JavaFX
    # Clase principal
    # Archivos FXML
    # Documentación
    # Diagramas y diseños

## Arquitectura
El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)**:

- **Modelo:** Lógica de negocio y acceso a datos
- **Vista:** Interfaces JavaFX 
- **Controlador:** Intermediario entre Vista y Modelo

Ver [diagrama de arquitectura](docs/design/arquitectura.png)

## Gestión del Proyecto
- **Repositorio:** GitHub
- **Gestión:** Jira con Scrum
- **Ramas:** main (estable), develop (desarrollo)
