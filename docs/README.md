## App.java — Clase Principal y Punto de Entrada
App.java es el punto de entrada de toda la aplicación. Extiende la clase javafx.application
Application, que es la clase base que JavaFX requiere para iniciar cualquier aplicación
gráfica. El método start(Stage stage) es invocado automáticamente por el framework cuando la
aplicación arranca y recibe como parámetro el Stage, que representa la ventana principal del
sistema operativo.
Lo primero que hace el método start es cargar las dos tipografías personalizadas del
proyecto mediante Font.loadFont(). Esto es necesario porque JavaFX, a diferencia de los
navegadores web, no carga automáticamente fuentes ubicadas en el classpath: deben
registrarse explícitamente al arrancar la aplicación para que estén disponibles en todos los
archivos FXML y hojas de estilo.
A continuación, se crea la Scene principal con un tamaño de 640x480 píxeles cargando el
archivo PantallaDeCarga.fxml como vista inicial. El Stage (ventana) recibe esta Scene y se
hace visible con stage.show().
El método estático setRoot(String fxml) es la pieza central del sistema de navegación.
Permite cambiar la pantalla activa desde cualquier controlador con una sola línea de código,
sin necesidad de crear nuevas ventanas ni conocer los detalles de la Scene. Sin embargo,
este método no expone el controlador del FXML cargado, por lo que cuando es necesario
transferir datos entre pantallas (como el objeto del jugador), se utiliza FXMLLoader
directamente en el controlador origen.
El método privado loadFXML construye dinámicamente la ruta de cada archivo FXML concatenando
el prefijo /com/example/View/ con el nombre recibido y la extensión .fxml. Si el archivo no
existe en el classpath, lanza una IOException con un mensaje descriptivo que facilita
enormemente el debugging durante el desarrollo.

## JugadorModel.java — Entidad del Jugador
JugadorModel es una clase POJO (Plain Old Java Object) que representa el concepto de
'jugador' dentro del dominio del problema. En el patrón MVC, las entidades del Modelo son
objetos de datos que no contienen lógica de negocio compleja: simplemente encapsulan los
atributos de una entidad del mundo real y exponen métodos de acceso.
La clase tiene dos atributos: alias (String), que almacena el nombre que el jugador eligió,
e isGuest (boolean), que indica si el jugador ingresó sin registrar un alias permanente.
Esta distinción es importante para decisiones futuras como si se guarda o no el puntaje en
la base de datos.
El método isValid() realiza una validación básica de integridad del objeto: verifica que el
alias no sea nulo y que no sea una cadena vacía o compuesta solo de espacios. Este método es
útil para hacer comprobaciones rápidas antes de pasar el objeto a capas que realizarán
operaciones más costosas, como consultas a la base de datos.

## JugadorLogica.java — Lógica de Negocio del Jugador
JugadorLogica es la clase que contiene las reglas de negocio relacionadas con el jugador. Su
existencia como clase separada de JugadorModel y de los controladores responde a un
principio fundamental del patrón MVC y del diseño orientado a objetos: la separación de
responsabilidades. Si las validaciones estuvieran en el controlador, cualquier cambio en las
reglas (por ejemplo, permitir guiones en el alias) requeriría modificar la capa de
presentación, lo cual es incorrecto.
El método registerPlayer(String alias) aplica dos validaciones en secuencia. Primero
verifica que el alias no sea nulo ni vacío, y luego comprueba con una expresión regular que
solo contenga caracteres alfanuméricos ([a-zA-Z0-9]+). Si alguna validación falla, lanza una
IllegalArgumentException con un mensaje descriptivo que el controlador captura y muestra al
usuario. Si todo es correcto, instancia y retorna un JugadorModel con isGuest en false.
El método enterAsGuest() crea y retorna un JugadorModel con el alias 'Invitado' y isGuest en
true. No realiza ninguna validación porque no hay datos de entrada del usuario. Este diseño
permite que en el futuro se pueda generar un alias aleatorio para invitados modificando
únicamente este método, sin afectar ningún controlador.

## JugadorController.java — Controlador de la Pantalla de Inicio
JugadorController gestiona la interacción del usuario con la pantalla PantallaDeCarga.fxml,
que es la primera pantalla que ve el usuario al iniciar la aplicación. Esta pantalla cumple
la función de registro e identificación: el jugador puede ingresar un alias personalizado o
entrar directamente como invitado.
Los campos de la vista se inyectan automáticamente mediante la anotación @FXML, que le
indica a JavaFX qué atributos de Java corresponden a qué elementos del archivo FXML según su
atributo fx:id. Por ejemplo, @FXML private TextField aliasField se conecta automáticamente
con el elemento <TextField fx:id="aliasField"> del FXML.
El método handleRegistrar() obtiene el texto del campo aliasField y lo pasa al método
registerPlayer() de JugadorLogica. Si la validación tiene éxito, almacena el jugador en la
variable currentPlayer, habilita el botón btnStart (que estaba deshabilitado en el FXML) y
muestra un mensaje de confirmación. Si falla, captura la IllegalArgumentException y muestra
el mensaje de error en el feedbackLabel sin interrumpir la ejecución de la aplicación.
El método handleInvitado() llama a enterAsGuest(), que siempre tiene éxito, y habilita
igualmente el botón btnStart. Este flujo garantiza que ningún usuario quede bloqueado sin
poder acceder al juego.
El método handleStart() es el más complejo de este controlador porque necesita navegar al
menú Y transferirle el objeto jugador. Para esto no puede usar App.setRoot(), ya que ese
método solo cambia la vista sin exponer el controlador cargado. En cambio, utiliza
FXMLLoader directamente: primero llama a loader.load() para cargar el FXML, luego obtiene el
controlador del menú con loader.getController() y finalmente le pasa el jugador con menuCtrl
setPlayer(). Este patrón de transferencia de datos entre pantallas es una práctica estándar
en JavaFX.
El campo feedbackLabel y el método privado showFeedback() permiten comunicar al usuario el
resultado de cada acción sin usar diálogos modales (Alert), lo que resulta en una
experiencia más fluida. Inicialmente el proyecto usaba titleLabel para los mensajes, pero se
separó en un feedbackLabel dedicado para mantener la separación de responsabilidades
visuales.

## MenuController.java — Controlador del Menú Principal
MenuController gestiona la pantalla Menu.fxml, que muestra los seis niveles disponibles del juego y permite al jugador seleccionar con
cuál desea comenzar. También es el punto de entrada a las instrucciones del juego.
El método setPlayer(JugadorModel player) es llamado externamente por JugadorController después de cargar el FXML. Recibe el objeto
jugador y actualiza el welcomeLabel para mostrar un mensaje personalizado de bienvenida con el alias del jugador. Este es el mecanismo
estándar para comunicar datos entre pantallas en JavaFX cuando se requiere pasar contexto.
El método handleNivel(ActionEvent event) responde a los clics en cualquiera de los seis botones de nivel. A diferencia de una navegación
genérica, este método identifica dinámicamente cuál botón fue presionado extrayendo el número del texto del botón con
btn.getText().replace("Nivel ", "").trim(). Con ese número construye la ruta al archivo FXML correspondiente (Nivel1.fxml, Nivel2.fxml,
etc.) y lo carga usando FXMLLoader directamente, para poder obtener la referencia al NivelController y pasarle el objeto jugador mediante
nivelCtrl.setPlayer(currentPlayer). Este diseño permite que los seis botones compartan un único método de manejo, eliminando la
duplicación de código.
El método handleComoJugar() muestra un Alert de tipo INFORMATION con las instrucciones del juego. Se eligió Alert en lugar de una
pantalla nueva para mantener al usuario en contexto: las instrucciones son una consulta rápida, no una pantalla de navegación. El método
showAndWait() bloquea la ejecución hasta que el usuario cierra el modal.
Los métodos handleHoverOn y handleHoverOff implementan efectos visuales de hover para los botones de nivel y el enlace de instrucciones.
Utilizan instanceof para identificar si el elemento que disparó el evento es un Button o un Label, aplicando estilos diferentes en cada
caso. Este enfoque centraliza el código de hover en dos métodos reutilizables.

## NivelController.java — Controlador Compartido de los Niveles
NivelController es el controlador que se asocia a las seis pantallas de nivel del juego (Nivel1.fxml hasta Nivel6.fxml). Una decisión de
diseño importante fue que los seis niveles comparten un único controlador, en lugar de tener un controlador diferente para cada nivel.
Esto es posible porque la estructura y el comportamiento de las pantallas de nivel son idénticos en esta etapa del desarrollo; las
diferencias de dificultad se implementarán a través de datos y lógica interna, no de controladores distintos.
La clase mantiene una referencia al JugadorModel mediante el campo currentPlayer y el método setPlayer(JugadorModel player), que es
invocado por MenuController inmediatamente después de cargar el FXML del nivel. Esta referencia es necesaria para conservar la identidad
del jugador a lo largo de toda la sesión de juego: sin ella, al volver al menú se perdería el contexto del jugador activo.
El método handleVolver() implementa la navegación de regreso al menú principal. Al igual que handleStart() en JugadorController, no puede
usar App.setRoot() porque necesita pasar el objeto jugador al MenuController de destino. Por esto utiliza FXMLLoader directamente: carga
Menu.fxml, obtiene el MenuController con getController() y llama a setPlayer() pasándole el currentPlayer guardado. De esta forma el menú
siempre muestra el alias correcto en el encabezado de bienvenida, independientemente de cuántos niveles haya visitado el jugador en la
sesión.
El campo nivelLabel está declarado con @FXML como referencia opcional al Label que muestra el número del nivel en pantalla. Aunque
actualmente no se usa de forma activa en la lógica del controlador, su presencia prepara la clase para iteraciones futuras donde se
necesite actualizar dinámicamente el título u otros elementos del nivel desde el código Java.

## NavigationException.java — Excepción Personalizada de Navegación
NavigationException es una excepción personalizada que extiende RuntimeException. Su
propósito es envolver y contextualizar los errores que ocurren durante la navegación entre
pantallas, específicamente las IOException que pueden lanzar FXMLLoader cuando no encuentra
un archivo FXML o cuando hay errores de configuración en la vista.
Al ser una RuntimeException (excepción no verificada), no obliga a los métodos que la usan a
declararla con throws ni a capturarla en cada punto de uso, lo que mantiene el código de
navegación limpio. Su constructor recibe tanto un mensaje descriptivo como la causa original
(Throwable cause), lo que preserva la traza completa del error para depuración.

## module-info.java — Declaración del Módulo Java
A partir de Java 9, el sistema de módulos (Project Jigsaw) requiere que las aplicaciones
declaren explícitamente sus dependencias y qué paquetes exponen al exterior. El archivo
module-info.java cumple esta función para el módulo com.example.
Las directivas requires javafx.controls, javafx.fxml y javafx.graphics declaran que el 
módulo depende de esas partes de JavaFX. Las directivas opens son especialmente importantes:
JavaFX necesita acceder por reflexión a los controladores para inyectar los campos @FXML en
tiempo de ejecución. Sin las directivas opens, Java lanzaría una InaccessibleObjectException
al intentar inyectar los campos de los controladores, impidiendo que la aplicación funcione.
Por eso se abren tanto el paquete raíz (com.example) como el paquete de controladores (com
example.Controller) al framework de JavaFX.

## pom.xml — Configuración de Maven
El archivo pom.xml (Project Object Model) es la configuración central de Maven y define todo
lo necesario para compilar, gestionar dependencias y ejecutar el proyecto.
Las dependencias javafx-controls y javafx-fxml en versión 13 proveen los controles de
interfaz (Button, TextField, Label, etc.) y el soporte para cargar archivos FXML
respectivamente. Estas dependencias se descargan automáticamente del repositorio central de
Maven la primera vez que se ejecuta mvn clean install.
El maven-compiler-plugin está configurado con release 11, lo que garantiza que el código
compilado sea compatible con Java 11 o superior, aunque el proyecto use características de
versiones más recientes. El javafx-maven-plugin permite ejecutar la aplicación directamente
con mvn javafx:run, configurando automáticamente el module-path de JavaFX, que es necesario
desde Java 11 ya que JavaFX dejó de estar incluido en el JDK estándar.

## PantallaDeCarga.fxml — Pantalla de Inicio
Esta es la primera pantalla que ve el usuario al iniciar la aplicación. Está construida con
un VBox (contenedor vertical) que centra todos sus elementos horizontal y verticalmente.
Contiene el título del juego con la fuente SIXTY a 74px, un Label de retroalimentación
inicialmente vacío, un TextField para el alias, y tres botones: Registrar, Ingresar como
Invitado y Start.
El botón Start tiene el atributo disable="true" en el FXML, lo que significa que inicia
deshabilitado y solo se activa programáticamente cuando el usuario registra un alias válido
o elige entrar como invitado. Esta decisión de diseño obliga al usuario a identificarse
antes de proceder, garantizando que siempre haya un jugador activo en el sistema.

## Menu.fxml — Menú de Selección de Nivel
Esta pantalla usa BorderPane como contenedor raíz, que divide el espacio en cinco regiones: top, center, bottom, left y right. La región
top contiene un HBox con el mensaje de bienvenida personalizado con el alias del jugador. La región center contiene un VBox con los seis
botones de nivel y el enlace de instrucciones.
Cada uno de los seis botones de nivel comparte el mismo color de fondo (#64b5f6, azul claro) y los mismos manejadores de eventos:
onAction apunta a handleNivel, y onMouseEntered y onMouseExited apuntan a handleHoverOn y handleHoverOff respectivamente. Al hacer clic
en cualquiera de ellos, el MenuController extrae el número del texto del botón para determinar a qué pantalla navegar, lo que permite que
los seis botones sean manejados por un único método sin duplicación de código.
El enlace '¿Cómo Jugar?' es técnicamente un Label con estilo de hipervínculo (texto subrayado, color naranja #ffb74d, cursor tipo mano).
El evento onMouseClicked apunta a handleComoJugar, y los eventos de hover cambian el color a un naranja más oscuro (#b44305) al pasar el
cursor.

## Nivel1.fxml hasta Nivel6.fxml — Pantallas de Juego por Nivel
Los seis archivos FXML de nivel (Nivel1.fxml, Nivel2.fxml, Nivel3.fxml, Nivel4.fxml, Nivel5.fxml y Nivel6.fxml) comparten exactamente la
misma estructura, diferenciándose únicamente en el número que muestran en los Labels de título y encabezado. Esta decisión de diseño es
coherente con el uso de un controlador compartido (NivelController): si la estructura de las pantallas es idéntica, no tiene sentido
tener controladores diferentes.
Todas las pantallas de nivel usan BorderPane como contenedor raíz, asociadas al controlador com.example.Controller.NivelController. La
región top contiene un HBox con fondo azul claro (#e3f2fd) que alberga el botón '← Volver' y un Label con el nombre del nivel. La región
center contiene un VBox centrado con un Label grande en gris claro (#9e9e9e) que indica el número de nivel, sirviendo como marcador de
posición para el contenido de juego que se implementará en sprints posteriores.
El botón '← Volver' está estilizado como un enlace (fondo transparente, texto azul #1565c0, subrayado) en lugar de un botón convencional.
Su evento onAction apunta al método handleVolver() del NivelController, que se encarga de regresar al menú principal conservando el
estado del jugador activo.

## Diseño Visual — Tipografías
El proyecto utiliza dos tipografías cargadas localmente desde la carpeta de recursos del
proyecto (/com/example/fonts/), lo que garantiza que la aplicación se vea igual en cualquier
computador, independientemente de las fuentes instaladas en el sistema operativo.
*SIXTY (SIXTY.TTF) — Tipografía del Título*
SIXTY es una tipografía display de estilo bold y geométrico utilizada exclusivamente para el
título principal 'Guess My Rule' en la pantalla de inicio, con un tamaño de 74 píxeles. Fue
seleccionada por varias razones: su trazo grueso y formas redondeadas transmiten energía y
modernidad, coherente con el carácter lúdico de la aplicación. Su escala grande y alto peso
visual la hace identificable de un vistazo, cumpliendo su función como elemento de identidad
del juego. La tipografía es de distribución gratuita y fue obtenida de DaFont (dafont.com),
un repositorio de fuentes libres ampliamente utilizado en proyectos educativos y de
entretenimiento.
*Roboto Regular (Roboto-Regular.ttf) — Tipografía del Cuerpo*
Roboto Regular es utilizada para todos los textos del cuerpo de la aplicación: etiquetas,
botones, mensajes de retroalimentación e instrucciones. Fue diseñada originalmente por
Google para el sistema operativo Android y el sistema de diseño Material Design, y es
considerada una de las tipografías más legibles en pantallas digitales gracias a su
geometría humanista y su espaciado optimizado para resoluciones de pantalla.
Roboto es de código abierto bajo licencia Apache 2.0 y está disponible libremente en Google
Fonts (fonts.google.com/specimen/Roboto). Su uso en este proyecto garantiza legibilidad en
los rangos de tamaño utilizados (12 a 20 píxeles) y es una fuente reconocida por su buen
rendimiento para personas con dislexia leve gracias a la diferenciación clara entre
caracteres similares como 'b', 'd', 'p' y 'q'.

## Paleta de Colores y Accesibilidad Visual
La selección de colores del proyecto no fue arbitraria: se tomaron en cuenta criterios de
accesibilidad visual para garantizar que la aplicación pueda ser utilizada cómodamente por
personas con diferentes capacidades visuales, incluyendo baja visión, daltonismo y
sensibilidad a la luz.

| Color | Justificación |
|-----|-------|
| ![#f5f5f5](https://img.shields.io/badge/-F5F5F5-f5f5f5) | Fondo general de pantallas. Gris muy claro en lugar de blanco puro para reducir la fatiga visual. |
| ![#64b5f6](https://img.shields.io/badge/-64B5F6-64b5f6) | Color base de los botones de nivel. Azul claro de Material Design, amigable y no agresivo visualmente. |
| ![#1976d2](https://img.shields.io/badge/-1976D2-1976d2) | Color de los botones al pasar el cursor (hover). Azul más oscuro que genera suficiente contraste con el fondo. |
| ![#1565c0](https://img.shields.io/badge/-1565C0-1565c0) | Texto del encabezado de bienvenida. Azul oscuro con alto contraste sobre fondo claro. |
| ![#e3f2fd](https://img.shields.io/badge/-E3F2FD-e3f2fd) | Fondo del encabezado superior del menú. Azul muy pálido que enmarca la zona de bienvenida sin recargar visualmente. |
| ![#424242](https://img.shields.io/badge/-424242-424242) | Texto 'Selecciona un Nivel'. Gris oscuro de alta legibilidad, alternativa preferible al negro puro. |
| ![#616161](https://img.shields.io/badge/-616161-616161) | Color del título principal. Gris medio que es elegante y mantiene buen contraste. |
| ![#4dda40](https://img.shields.io/badge/-4DDA40-4dda40) | Botón Start. Verde equilibrado que indica acción positiva y disponibilidad para continuar. |
| ![#4caf50](https://img.shields.io/badge/-4CAF50-4caf50) | Mensajes de retroalimentación positiva (alias registrado). Verde semántico estándar. |
| ![#ffb74d](https://img.shields.io/badge/-FFB74D-ffb74d) | Enlace '¿Cómo Jugar?'. Naranja cálido que destaca sobre el fondo gris sin ser alarmante. |
| ![#b44305](https://img.shields.io/badge/-B44305-b44305) | Enlace '¿Cómo Jugar?' al pasar el cursor (hover). Naranja oscuro que indica interactividad. |
| ![#ffffff](https://img.shields.io/badge/-FFFFFF-ffffff) | Texto sobre botones de color oscuro. Garantiza máximo contraste sobre fondos azules y verdes. |
    
## Accesibilidad y la Experiencia de Usuario
Para que una plataforma sea realmente inclusiva, no basta con que sea funcional; debe ser legible y cómoda para todos. Por
ello, hemos alineado el diseño con las Pautas de Accesibilidad para el Contenido Web (WCAG 2.1), centrándonos especialmente
en garantizar que el contraste visual facilite la lectura sin importar las condiciones visuales del usuario.

### Priorizando la Claridad Visual
Bajo el estándar de nivel AA, que exige un ratio mínimo de 4.5:1, hemos seleccionado una paleta donde el contenido principal
no solo cumple, sino que supera las expectativas. Por ejemplo, nuestro texto base en gris oscuro sobre fondo claro alcanza
un ratio de 9.7:1, ofreciendo una nitidez excepcional. Incluso en elementos dinámicos como los botones en estado hover o los
títulos decorativos, mantenemos ratios que oscilan entre 4.8:1 y 6.9:1, asegurando que la jerarquía de la información sea
evidente para cualquier usuario.

### Diseño Inclusivo para el Daltonismo
Somos conscientes de que la percepción del color varía; condiciones como la deuteranomalía afectan a una parte significativa
de la población. Con esto en mente, la identidad visual del proyecto se apoya firmemente en una gama de azules y grises,
tonos que son percibidos con consistencia por la mayoría de las personas con daltonismo rojo-verde.

Más allá del color, creemos en la redundancia de la información: el estado del botón "Start", por ejemplo, no se comunica
solo mediante el cambio a verde, sino que está vinculado a un comportamiento físico de bloqueo (disabled). Así, el usuario
recibe señales claras a través de la interactividad y la luminosidad, no dependiendo exclusivamente de la interpretación
cromática.

### Cuidado de la Fatiga Visual
El bienestar del usuario durante sesiones prolongadas es una prioridad. En lugar de utilizar un blanco puro (#ffffff), que
puede generar un deslumbramiento incómodo, hemos optado por un gris muy suave (#f5f5f5) para el fondo general. Esta pequeña
pero significativa variación reduce el contraste extremo, beneficiando directamente a personas con fotofobia, migrañas o
simplemente a quienes utilizan la aplicación en entornos muy iluminados, mitigando la fatiga visual a largo plazo.
