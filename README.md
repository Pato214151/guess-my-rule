# Guess My Rule
Juego educativo donde el jugador debe descubrir reglas matemáticas ocultas mediante pistas y pruebas.

**Proyecto:** Arquitectura MVC - Politécnico Grancolombiano  
**Equipo:** Julian, Gabriel, Valentina

## Tecnologías
- Java 21
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

Ver [diagrama de arquitectura](design/arquitectura.md)

## Gestión del Proyecto
- **Repositorio:** GitHub
- **Gestión:** Jira con Scrum
- **Ramas:** main (estable), develop (desarrollo)
