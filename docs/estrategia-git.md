# Estrategia de Git y Ramas

## Ramas Principales

- **main**: Código estable y funcional. Solo se actualiza al finalizar cada sprint.
- **develop**: Rama de integración. NO se trabaja directamente aquí, solo se fusionan las ramas feature completadas.

## Ramas de Trabajo

### Feature Branches (Ramas de Funcionalidad)
- Formato: `feature/GMR1-XX-descripcion`
- Ejemplo: `feature/GMR1-3-alias-jugador`
- **Importante:** Cada integrante trabaja en SU PROPIA rama feature
- Se crean desde `develop` y se fusionan de vuelta a `develop`

### Bugfix Branches (Ramas de Corrección)
- Formato: `bugfix/descripcion-del-bug`
- Se crean desde `develop` para corregir errores

## Flujo de Trabajo

### 1. Asignación de Tarea
- Cada integrante toma una historia de usuario en Jira (ej: GMR1-3)

### 2. Crear Rama Feature
```bash
git checkout develop
git pull origin develop
git checkout -b feature/GMR1-3-alias-jugador
```

### 3. Trabajar en Rama de cada quien
- Hacer commits frecuentes EN TU RAMA feature
- **NO trabajar directo en develop**
- Puedes trabajar varios días en tu rama sin afectar a nadie

### 4. Mantener Actualizada Tu Rama
```bash
git checkout develop
git pull origin develop
git checkout feature/GMR1-3-alias-jugador
git merge develop
```

### 5. Finalizar y Fusionar
- Subir tu rama feature a GitHub
- Crear Pull Request de tu feature → develop
- Otro integrante revisa el código
- Si está bien, se fusiona a develop
- Borrar la rama feature después de fusionar

### 6. Final del Sprint
- Al terminar el sprint, fusionar develop → main
