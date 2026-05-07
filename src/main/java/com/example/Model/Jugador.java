package com.example.Model;


/**
 * Entidad que representa al jugador activo en la sesión.
 * <p>
 * Almacena el alias elegido y si el jugador ingresó como invitado.
 * La distinción {@code isGuest} determina si el puntaje se persiste en la base de datos.
 * </p>
 */
public class Jugador {
    private String alias;
    private boolean isGuest;

    public Jugador(String alias, boolean isGuest) {
        this.alias = alias;
        this.isGuest = isGuest;
    }


    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public boolean isGuest() { return isGuest; }
    public void setGuest(boolean guest) { isGuest = guest; }

    /**
     * Verifica que el alias no sea nulo ni esté compuesto solo de espacios.
     *
     * @return {@code true} si el alias es válido; {@code false} en caso contrario
     */
    public boolean isValid() {
        return alias != null && !alias.trim().isEmpty();
    }
}