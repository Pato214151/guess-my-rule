package com.example.Model;

// Objeto temporal de validación. Su ciclo de vida inicia y termina en PantallaLogin:
// se crea con el alias ingresado, se valida, y si es válido transfiere el alias a GameSession.
// No persiste más allá de ese momento.

public class LoginJugador {

    private final String alias;
    private final boolean isGuest;

    public LoginJugador(String alias, boolean isGuest) {
        this.alias   = alias == null ? "" : alias.trim();
        this.isGuest = isGuest;
    }

    public String  getAlias()   { return alias; }
    public boolean isGuest()    { return isGuest; }

    // El modelo es dueño de las reglas de validación
    public boolean isValid() {
        return !alias.isEmpty() && alias.matches("[a-zA-Z0-9]+");
    }

    // El modelo también conoce el mensaje de error específico
    public String getMensajeError() {
        if (alias.isEmpty())                    return "El alias no puede estar vacío";
        if (!alias.matches("[a-zA-Z0-9]+"))     return "El alias solo puede contener letras y números";
        return null;
    }
}