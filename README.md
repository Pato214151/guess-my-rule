# Guess My Rule

Guess My Rule es una aplicación educativa interactiva cuyo objetivo principal es fortalecer el pensamiento lógico-matemático en los estudiantes. El jugador introduce números en el sistema, el cual aplica una regla matemática oculta y devuelve un resultado. A partir de la observación de múltiples entradas y sus respectivas salidas, el jugador debe deducir cuál es esa regla. Por ejemplo, si al ingresar 3 el sistema responde 9, y al ingresar 5 responde 25, la regla podría ser elevar al cuadrado.

El proyecto fue desarrollado como trabajo grupal para la asignatura de Práctica Aplicada Sistemas del Politécnico Grancolombiano, siguiendo la metodología ágil Scrum con entregas por cortes. La arquitectura elegida es el patrón Modelo-Vista-Controlador (MVC).

---

## Tecnologías y Herramientas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21+ | Lenguaje principal |
| JavaFX | 13+ | Interfaces gráficas de escritorio (FXML) |
| Maven | 3.9+ | Gestión de dependencias y build |
| MySQL | 8 | Persistencia de jugadores, puntajes y partidas |
| GitHub | — | Control de versiones y colaboración |
| Jira | — | Gestión Scrum: historias de usuario y sprints |

---

## Arquitectura (MVC)

El patrón Modelo-Vista-Controlador divide la aplicación en tres capas:

- **Modelo:** Lógica de negocio y entidades del dominio (`Regla`, `Jugador`, `Partida`, `GameSession`).
- **Vista (FXML):** Archivos XML declarativos que definen la interfaz sin lógica de negocio.
- **Controlador:** Clases Java anotadas con `@FXML` que coordinan entre la Vista y el Modelo.

Ver [diagrama de arquitectura](docs/design/arquitectura.png)

---

## Estructura del Proyecto

```
src/main/java/com/example/
├── App.java                        # Punto de entrada JavaFX
├── Model/
│   ├── Regla.java                  # Regla matemática oculta por nivel
│   ├── Regla.ParInOut.java         # Par entrada-salida (inner class)
│   ├── FilaTest.java               # Fila editable de la tabla de evaluación
│   ├── GameSession.java            # Singleton de sesión compartida
│   ├── Jugador.java                # Entidad del jugador
│   ├── Partida.java                # Métricas: tiempo e intentos
│   └── Puntaje.java                # Entidad del ranking
├── Controller/
│   ├── PantallaLogin.java
│   ├── MenuSeleccionarNivel.java
│   ├── BloqueAprenderRegla.java
│   ├── BloqueDeclararRegla.java
│   └── ResumenPuntaje.java
└── util/
    ├── DataBaseConnection.java
    ├── DAOPuntaje.java
    ├── CRUD.java
    └── NavigationException.java

src/main/resources/com/example/View/
├── PantallaLogin.fxml
├── MenuSeleccionarNivel.fxml
├── BloqueAprenderRegla.fxml
├── BloqueDeclararRegla.fxml
└── ResumenPuntaje.fxml
```

---

## Flujo de Navegación

```
PantallaLogin → MenuSeleccionarNivel → BloqueAprenderRegla → BloqueDeclararRegla → ResumenPuntaje
                                                ↑                      |
                                                └──────── Volver ──────┘
```

---

## Instalación y Ejecución

### Prerrequisitos

Verificar que los siguientes programas estén instalados antes de continuar:

```bash
java -version   # Requiere JDK 21 o superior
mvn -version    # Requiere Maven 3.9 o superior
```

También se necesita **MySQL 8** con el servidor en ejecución.

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/Pato214151/guess-my-rule.git
cd guess-my-rule
```

**2. Crear la base de datos en MySQL**
```sql
CREATE DATABASE guessrule;
```

**3. Instalar dependencias y compilar**
```bash
mvn clean install
```

**4. Ejecutar la aplicación**
```bash
mvn javafx:run
```

> **Nota:** Si aparece el error `Communications link failure`, el servicio MySQL está detenido. Iniciarlo antes de ejecutar la aplicación.

---

## Gestión del Proyecto

- **Repositorio:** [github.com/Pato214151/guess-my-rule](https://github.com/Pato214151/guess-my-rule)
- **Metodología:** Scrum con 6 sprints
- **Ramas:** `main` (estable) · `develop` (integración)
- **Gestión de tareas:** Jira — proyecto GMR1
- **Equipo:** Val, Julian, Gabriel, Valentina
