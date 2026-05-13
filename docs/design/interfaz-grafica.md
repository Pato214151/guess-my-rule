# Documentación de Interfaz Gráfica — Guess My Rule

## 1. Estilo general

La interfaz utiliza un estilo **glassmorphism oscuro** inspirado en diseños modernos de aplicaciones educativas. La paleta combina tonos índigo profundos con fondos cálidos y tarjetas de colores suaves, logrando un contraste que guía la atención del usuario hacia el contenido central sin sobrecargar visualmente.

Todas las pantallas comparten el mismo fondo animado de elipses difuminadas, creando coherencia visual y una identidad reconocible a lo largo del flujo de juego.

---

## 2. Paleta de colores

### Fondo (todas las pantallas)

| Rol | Color | Hex |
|-----|-------|-----|
| Base del fondo | Gris cálido | `#d6d2d0` |
| Elipse exterior (orbe) | Azul intenso | `#092483` |
| Elipse media | Azul marino | `#081f71` |
| Elipse interior | Azul oscuro | `#071b61` |

### Tarjetas (cards)

| Rol | Color | Hex |
|-----|-------|-----|
| Fondo de tarjeta | Lavanda muy claro | `#f0f2ff` |
| Borde de tarjeta | Azul-gris suave | `#c5cae9` |

### Texto

| Rol | Color | Hex |
|-----|-------|-----|
| Títulos principales (sobre fondo oscuro) | Blanco | `#ffffff` |
| Títulos de tarjeta | Índigo profundo | `#1a237e` |
| Subtítulos / secciones | Índigo medio | `#3949ab` |
| Texto secundario / descriptivo | Gris oscuro | `#555555` |
| Texto en tabla columna IN | Índigo profundo | `#1a237e` |
| Texto en tabla columna OUT | Índigo medio | `#3f4e85` |

### Botones

| Rol | Color | Hex |
|-----|-------|-----|
| Botón primario (acciones principales) | Índigo | `#3f4e85` |
| Botón de navegación Menú Principal | Índigo profundo | `#1a237e` |
| Botón secundario (volver) | Lavanda | `#c5cae9` |
| Botón de retroceso "←" | Transparente / texto blanco | — |

### Retroalimentación

| Estado | Color texto | Color fondo celda | Hex texto | Hex fondo |
|--------|-------------|-------------------|-----------|-----------|
| Correcto | Verde oscuro | Verde claro | `#2e7d32` | `#a5d6a7` |
| Incorrecto | Rojo | Rojo claro | `#e53935` | `#ef9a9a` |
| Advertencia (login) | Rojo claro | — | `#ef9a9a` | — |
| Listo para jugar (login) | Amarillo-lima | — | `#e2ea0d` | — |

---

## 3. Tipografía

| Uso | Fuente | Tamaño | Peso |
|-----|--------|--------|------|
| Título del juego (PantallaLogin) | TEMBLORES | 74 px | Normal |
| Títulos de pantalla (navbar) | Roboto | 30 px | Bold |
| Títulos de sección (cards) | Roboto | 20 px | Bold |
| Texto de instrucciones y etiquetas | Roboto | 16 px | Normal |
| Texto en tablas | Roboto | 15–16 px | Bold |
| Texto pequeño (feedback, subtextos) | Roboto | 13–14 px | Normal |

La fuente **TEMBLORES** se usa exclusivamente en el título de la pantalla de login para darle un carácter dinámico y lúdico. **Roboto** se usa en el resto de la aplicación por su legibilidad en tamaños pequeños y medianos.

---

## 4. Efecto de fondo — Glassmorphism con elipses

Cada pantalla usa un `StackPane` con dos capas apiladas:

1. **Capa de fondo** (`Pane` con `#d6d2d0`): contiene 9 elipses organizadas en 3 grupos, cada una con un efecto `GaussianBlur`.
2. **Capa de contenido** (`BorderPane` transparente): contiene la interfaz funcional.

### Estructura de los grupos de elipses

Cada grupo tiene 3 elipses concéntricas que crean un "orbe" de luz difusa:

```
Elipse exterior  → radio grande  · #092483 · opacidad 1.0 · blur 500
Elipse media     → radio medio   · #081f71 · opacidad 0.9 · blur 80
Elipse interior  → radio pequeño · #071b61 · opacidad 0.9 · blur 80
```

### Posición de los tres grupos

| Grupo | Posición | Efecto visual |
|-------|----------|---------------|
| Superior izquierdo | centerX=350, centerY=100 | Orbe azul en la esquina superior |
| Derecho | centerX=1200, centerY=350 | Orbe azul en el borde derecho |
| Inferior central | centerX=700, centerY=1000 | Resplandor azul desde abajo |

El resultado es un fondo que simula luces de neón difusas sobre un fondo gris, sin usar imágenes externas.

---

## 5. Componente tarjeta (Card)

Las tarjetas son `VBox` con las siguientes propiedades CSS:

```
-fx-background-color: #f0f2ff
-fx-background-radius: 16
-fx-border-color: #c5cae9
-fx-border-radius: 16
-fx-border-width: 1.5
-fx-padding: 20 a 40px según pantalla
```

Se usan en:
- **MenuSeleccionarNivel**: tarjeta "¿Cómo Jugar?" (320 px) y tarjeta de niveles (320 px)
- **BloqueAprenderRegla**: tarjeta central con input y tabla (420 px máx)
- **BloqueDeclararRegla**: tarjeta con cronómetro y tabla de test (600 px máx)

---

## 6. Tablas

### Tabla In/Out (BloqueAprenderRegla)

| Propiedad | Valor |
|-----------|-------|
| Fondo | Transparente |
| Borde | `#c5cae9`, 2 px, radio 10 |
| Fila par (índice 0, 2, 4…) | `#e8eaf6` |
| Fila impar (índice 1, 3, 5…) | `#f0f2ff` |
| Columna IN — texto | `#1a237e`, 16 px, bold |
| Columna OUT — texto | `#3f4e85`, 16 px, bold |

### Tabla de test (BloqueDeclararRegla)

Misma configuración de filas, más el sistema de colores de retroalimentación en la columna OUT:

| Estado tras verificar | Color fondo celda | Color borde celda |
|-----------------------|-------------------|-------------------|
| Respuesta correcta | `#a5d6a7` (verde claro) | `#81c784` |
| Respuesta incorrecta | `#ef9a9a` (rojo claro) | `#e57373` |
| Sin verificar | Transparente | `#9fa8da` |

La columna OUT utiliza un `TextField` embebido en cada celda, activable con un solo clic, para que el usuario no tenga que hacer doble clic.

### Tabla de ranking (MenuSeleccionarNivel)

| Propiedad | Valor |
|-----------|-------|
| Fondo | Azul oscuro `#1c2875` |
| Borde | `#d6d2d0`, 2 px, radio 12 |
| Texto placeholder | `#d6d2d0`, 16 px, cursiva |

---

## 7. Botones

Todos los botones tienen `cursor: hand` para indicar que son interactivos.

| Tipo | Estilo |
|------|--------|
| **Primario** | Fondo `#3f4e85`, texto blanco, radio 8–10, padding 10 24 |
| **Menú principal** | Fondo `#1a237e`, texto blanco, radio 6 |
| **Secundario** | Fondo `#c5cae9`, texto `#1a237e`, radio 8 |
| **Navegación "←"** | Fondo transparente, texto blanco, 30 px |
| **Start (login)** | Fondo `#3f4e85`, texto blanco, 20 px, `disable=true` por defecto |

---

## 8. Animaciones

### Título flotante (PantallaLogin)
El label `"Guess My Rule"` tiene un `TranslateTransition` aplicado en el controlador:

| Propiedad | Valor |
|-----------|-------|
| Duración | 2 000 ms |
| Movimiento Y | de 0 a −10 px |
| AutoReverse | true |
| Ciclos | INDEFINITE |

El efecto es un suave balanceo vertical continuo que da vida al título sin distraer al usuario.

### Validación en tiempo real (login)
El campo de alias valida carácter a carácter mientras el usuario escribe:
- Campo vacío → botón Start deshabilitado, sin mensaje
- Caracteres inválidos → mensaje rojo `⚠️ Solo letras y números, sin espacios`
- Alias válido → botón Start habilitado, mensaje `¡Dale al botón Start para jugar!` en `#e2ea0d`

---

## 9. Estructura de layout por pantalla

### PantallaLogin
```
StackPane
├── Pane (fondo con 9 elipses)
└── VBox (centrado)
    ├── Label "Guess My Rule" (TEMBLORES 74px, animado)
    └── StackPane (tarjeta semitransparente)
        └── HBox
            ├── VBox (alias + feedback + botón Start)
            ├── Label "o"
            └── VBox (botón Invitado)
```

### MenuSeleccionarNivel
```
StackPane
├── Pane (fondo con 9 elipses)
└── BorderPane
    ├── top: HBox (← + bienvenida)
    └── center: HBox
        ├── VBox card "¿Cómo Jugar?" (320 px)
        ├── VBox card "Selecciona un Nivel" (320 px)
        └── VBox "Ranking Global" (flexible)
```

### BloqueAprenderRegla
```
StackPane
├── Pane (fondo con 9 elipses)
└── BorderPane
    ├── top: HBox (← + título nivel)
    └── center: VBox
        └── VBox card (420 px)
            ├── HBox (TextField + botón Go!)
            ├── TableView (In | Out)
            ├── Label feedback
            └── Button "I think I know the rule!"
```

### BloqueDeclararRegla
```
StackPane
├── Pane (fondo con 9 elipses)
└── BorderPane
    ├── top: HBox (título nivel)
    └── center: VBox
        └── VBox card (600 px)
            ├── HBox (tiempo + intentos)
            ├── Label instrucción
            ├── TableView (In | Out editable)
            ├── Label feedback
            └── HBox (Check + Go Back)
```

### ResumenPuntaje
```
BorderPane
├── Pane (fondo con 9 elipses)
└── center: VBox (centrado)
    ├── Label "🏆" (64px)
    ├── Label mensaje
    ├── Label puntaje
    ├── Label tiempo
    ├── Label estado
    └── Button "🏠 Menú Principal"
```

---

## 10. Decisiones de diseño

| Decisión | Justificación |
|----------|---------------|
| Fondo glassmorphism con elipses | Crea profundidad visual sin usar imágenes; es liviano en recursos |
| Tarjetas `#f0f2ff` sobre fondo oscuro | El contraste entre la tarjeta clara y el fondo oscuro dirige la atención al contenido |
| Paleta monocromática índigo | Transmite seriedad y concentración, adecuado para un juego matemático |
| Fuente TEMBLORES solo en el título | Diferencia el título del juego del resto de la UI sin romper la coherencia |
| TextField visible siempre en la tabla | Elimina la fricción del doble clic; el usuario puede responder inmediatamente |
| Retroalimentación verde/roja por celda | El jugador identifica exactamente qué respuestas corregir sin leer texto |
| Botón Start deshabilitado por defecto | Previene el envío de un alias vacío antes de que el usuario haya escrito |
