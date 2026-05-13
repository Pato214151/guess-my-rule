package com.example.Model;


/**
 * Representa a un jugador con un alias y su condición de invitado.
 */
public class Jugador {
    private String alias;
    private boolean isGuest;

    /**
     * Crea un nuevo jugador.
     *
     * @param alias   nombre único del jugador
     * @param isGuest {@code true} si el jugador ingresó como invitado
     */
    public Jugador(String alias, boolean isGuest) {
        this.alias = alias;
        this.isGuest = isGuest;
    }

    /** @return alias del jugador */
    public String getAlias() { return alias; }
    /** @param alias nuevo alias del jugador */
    public void setAlias(String alias) { this.alias = alias; }
    /** @return {@code true} si el jugador es invitado */
    public boolean isGuest() { return isGuest; }
    /** @param guest {@code true} para marcar al jugador como invitado */
    public void setGuest(boolean guest) { isGuest = guest; }

    /**
     * Verifica que el alias no sea nulo ni esté vacío.
     *
     * @return {@code true} si el alias es válido
     */
    public boolean isValid() {
        return alias != null && !alias.trim().isEmpty();
    }
}