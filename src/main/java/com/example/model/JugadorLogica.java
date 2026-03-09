package com.example.model;

public class JugadorLogica {

    public JugadorModel registerPlayer(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias no puede estar vacío");
        }
        if (!alias.trim().matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("El alias solo puede contener letras y números");
        }
        return new JugadorModel(alias.trim(), false);
    }

    public JugadorModel enterAsGuest() {
        return new JugadorModel("Invitado", true);
    }
}
