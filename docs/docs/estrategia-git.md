# Estrategia de Git y Ramas

## Ramas Principales
- main: Código estable y funcional. Solo se actualiza al finalizar cada sprint.
- develop: Rama de desarrollo activa donde se integran todas las funcionalidades.
## Ramas de Trabajo

### Feature Branches
- Formato: feature/GMR1-XX-descripcion
- Ejemplo: feature/GMR1-3-alias-jugador
- Se crean desde develop y se fusionan de vuelta a develop

### Bugfix Branches
- Formato: bugfix/descripcion-del-bug
- Se crean desde develop para corregir errores

## Flujo de Trabajo
1. Crear rama feature desde develop
2. Trabajar en la funcionalidad
3. Hacer commits frecuentes con mensajes descriptivos
4. Al terminar, crear Pull Request hacia develop
5. Después de revisión, fusionar a develop
6. Al final del sprint, fusionar develop a main
7. 
## Reglas
- No hacer commits directos a main
- Cada historia de usuario tiene su propia rama
- Mensajes de commit claros: "GMR1-XX: Descripción del cambio"
