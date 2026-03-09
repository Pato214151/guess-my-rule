// com/example/model/PlayerService.java
package com.example;

public class JugadorLogica {

    public JugadorModel registerPlayer(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias no puede estar vacío");
        }
        return new JugadorModel(alias.trim(), false);
    }

    public JugadorModel enterAsGuest() {
        return new JugadorModel("Invitado", true);
    }
}