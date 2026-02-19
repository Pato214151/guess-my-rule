# Arquitectura MVC - Guess My Rule
## Diagrama
(arquitectura.png)

## Descripción de Capas
### VISTA (JavaFX)
- Archivos FXML que definen la interfaz gráfica
- Pantallas del juego
- NO contiene lógica de negocio

### CONTROLADOR
- Intermediario entre Vista y Modelo
- Maneja eventos de la interfaz
- Actualiza la vista con datos del modelo

### MODELO (Entidades + DAO)
- Lógica del juego
- Clases de entidades (Jugador, Partida, Regla)
- Clases DAO para acceso a base de datos

### BASE DE DATOS (MySQL)
- Almacenamiento persistente
- Tablas: jugadores, partidas, puntajes
