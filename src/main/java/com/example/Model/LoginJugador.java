package com.example.Model;

//Primeramente entender que esta clase es muy local y temporal, es decir, su ciclo de vida muere cuando
// en la pantalla inicial del juego se da en iniciar, ese dato de "alias" se guarda en la clase GameSession, 
// y esta clase JugadorModel es solo un contenedor temporal para validar el alias ingresado por el usuario, 
// y luego se transfiere a GameSession para 
// su uso durante toda la sesión de juego. Es una clase simple que encapsula la información del jugador, 
// principalmente su alias y si es un invitado o no




public class LoginJugador {
    private String alias;
    private boolean isGuest;

    public LoginJugador(String alias, boolean isGuest) {
        this.alias = alias;
        this.isGuest = isGuest;
    }

    // Getters y setters
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public boolean isGuest() { return isGuest; }
    public void setGuest(boolean guest) { isGuest = guest; }

    public boolean isValid() {
        return alias != null && !alias.trim().isEmpty();
    }
}