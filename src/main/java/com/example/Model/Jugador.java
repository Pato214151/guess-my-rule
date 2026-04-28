package com.example.Model;


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

    public boolean isValid() {
        return alias != null && !alias.trim().isEmpty();
    }
}