# Diagrama de Clases – Guess My Rule

## Descripción general

El sistema se divide en tres capas principales siguiendo el patrón MVC:

- **Capa de Aplicación**: `App` y `GameSession` (Singleton de estado global)
- **Capa de Controladores (MVC – Controller)**: `JugadorController`, `MenuController`, `NivelController`
- **Capa de Modelos (MVC – Model)**: `JugadorModel`, `ReglaModel`, `ParInOut` (inner class)
- **Capa de Utilidades**: `DataBaseConnection`, `NavigationException`

## Diagrama

```mermaid
classDiagram

  %% ── Capa de Aplicación ──────────────────────────────────────────
  class App {
    -Scene scene$
    +start(Stage) void
    +setRoot(String) void$
    +loadFXML(String) Parent$
    +getScene() Scene$
    +main(String[]) void$
  }

  class GameSession {
    -GameSession instance$
    -String alias
    -int nivel
    -GameSession()
    +getInstance() GameSession$
    +getAlias() String
    +setAlias(String) void
    +getNivel() int
    +setNivel(int) void
  }

  %% ── Capa de Controladores ───────────────────────────────────────
  class JugadorController {
    -Label titleLabel
    -TextField aliasField
    -Button btnRegistrar
    -Button btnInvitado
    -Button btnStart
    -Label feedbackLabel
    -JugadorModel currentPlayer
    +handleRegistrar() void
    +handleInvitado() void
    +handleStart() void
    -showFeedback(String) void
  }

  class MenuController {
    -Label welcomeLabel
    +initialize() void
    +handleVolver() void
    +handleNivel(ActionEvent) void
    +handleComoJugar() void
    +handleHoverOn(MouseEvent) void
    +handleHoverOff(MouseEvent) void
  }

  class NivelController {
    -Label labelTitulo
    -TextField inputNumero
    -TableView tablaInOut
    -TableColumn colIn
    -TableColumn colOut
    -Label labelFeedback
    -ReglaModel regla
    -ObservableList~ParInOut~ filas
    -String[] TITULOS$
    +initialize() void
    +handleGo() void
    +handleDeclararRegla() void
    +handleVolver() void
  }

  %% ── Capa de Modelos ─────────────────────────────────────────────
  class JugadorModel {
    -String alias
    -boolean isGuest
    +JugadorModel(String, boolean)
    +getAlias() String
    +setAlias(String) void
    +isGuest() boolean
    +setGuest(boolean) void
    +isValid() boolean
  }

  class ReglaModel {
    -int nivel
    +ReglaModel(int)
    +aplicarRegla(double) double
    +formatear(double) String$
    +evaluar(double) ParInOut
  }

  class ParInOut {
    -String entrada
    -String salida
    +ParInOut(String, String)
    +getEntrada() String
    +getSalida() String
  }

  %% ── Capa de Utilidades ──────────────────────────────────────────
  class DataBaseConnection {
    -String URL$
    -String USUARIO$
    -String PASSWORD$
    -DataBaseConnection()
    +obtenerConexion() Connection$
  }

  class NavigationException {
    +NavigationException(String, Throwable)
  }

  %% ── Relaciones ──────────────────────────────────────────────────
  App              ..>  GameSession        : usa (Singleton)
  App              ..>  DataBaseConnection : prueba conexión
  JugadorController ..> JugadorModel       : crea
  JugadorController ..> GameSession        : setAlias()
  JugadorController ..> App                : setRoot()
  MenuController   ..>  GameSession        : getAlias() / setNivel()
  MenuController   ..>  App                : setRoot()
  NivelController  ..>  GameSession        : getNivel()
  NivelController  ..>  ReglaModel         : evaluar()
  NivelController  ..>  App                : setRoot()
  ReglaModel       "1" *-- "0..*" ParInOut : crea
```

## Descripción de clases

### App
Punto de entrada de la aplicación JavaFX. Carga las fuentes, prueba la conexión a BD y gestiona la navegación entre vistas FXML mediante `setRoot()`.

### GameSession (Singleton)
Almacena el estado global de la sesión actual: alias del jugador y nivel seleccionado. Implementa el patrón Singleton para garantizar una única instancia.

### JugadorController
Controla la pantalla de registro del jugador. Valida el alias, crea el `JugadorModel` y guarda el alias en `GameSession` antes de navegar al menú.

### MenuController
Controla la pantalla de selección de nivel. Lee el alias de `GameSession` para el mensaje de bienvenida y navega al nivel seleccionado.

### NivelController
Controla la pantalla de juego. Delega al `ReglaModel` la lógica de evaluación y muestra los pares entrada/salida en la tabla.

### JugadorModel
Modelo de datos del jugador: alias y si es invitado. Incluye validación básica del alias.

### ReglaModel
Contiene la lógica de las 6 reglas matemáticas según el nivel. Evalúa entradas y retorna `ParInOut`.

### ParInOut (inner class de ReglaModel)
Representa un par entrada→salida generado por la regla. Usado por `NivelController` para poblar la tabla.

### DataBaseConnection
Utilidad estática para obtener la conexión JDBC a MySQL/MariaDB.

### NavigationException
Excepción de runtime para errores de navegación entre vistas FXML.
