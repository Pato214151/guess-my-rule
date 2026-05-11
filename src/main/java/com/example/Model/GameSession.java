package com.example.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sesión de juego global implementada como Singleton.
 * Mantiene el estado compartido entre pantallas: jugador, regla activa,
 * partida en curso y filas de la tabla In/Out.
 */
public class GameSession {

    private static GameSession instance;

    private Jugador         jugador;
    private Regla                regla;
    private Partida              partida;
    private List<Regla.ParInOut> filasGuardadas = new ArrayList<>();
    private List<FilaTest>       filasTest      = null;

    private GameSession() {}

    /**
     * Devuelve la única instancia de {@code GameSession}, creándola si no existe.
     *
     * @return instancia singleton de {@code GameSession}
     */
    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    }

    /** @param jugador jugador activo en la sesión */
    public void setJugador(Jugador jugador) { this.jugador = jugador; }
    /** @return jugador activo en la sesión */
    public Jugador getJugador()             { return jugador; }
    /** @return alias del jugador activo */
    public String getAlias()               { return jugador.getAlias(); }

    /** @param regla regla seleccionada para el nivel actual */
    public void setRegla(Regla regla) { this.regla = regla; }
    /** @return regla activa */
    public Regla getRegla()           { return regla; }
    /** @return número de nivel de la regla activa */
    public int getNivel()             { return regla.getNivel(); }

    /** @return partida en curso */
    public Partida getPartida()        { return partida; }
    /** @return tiempo transcurrido en segundos */
    public int getTiempo()             { return partida.getTiempo(); }
    /** @return número de intentos realizados */
    public int getIntentos()           { return partida.getIntentos(); }
    /** @return puntaje calculado de la partida actual */
    public int getPuntaje()            { return partida.calcularPuntaje(); }
    /** @param t nuevo valor de tiempo en segundos */
    public void setTiempo(int t)       { partida.setTiempo(t); }
    /** @param i nuevo valor de intentos */
    public void setIntentos(int i)     { partida.setIntentos(i); }

    /** @return lista de pares In/Out guardados entre pantallas */
    public List<Regla.ParInOut> getFilasGuardadas()           { return filasGuardadas; }
    /** @param filas lista de pares In/Out a persistir */
    public void setFilasGuardadas(List<Regla.ParInOut> filas) { this.filasGuardadas = filas; }

    /** @return filas de la tabla de test, o {@code null} si no hay ninguna guardada */
    public List<FilaTest> getFilasTest()                      { return filasTest; }
    /** @param filas filas a persistir para la pantalla de test */
    public void setFilasTest(List<FilaTest> filas)            { this.filasTest = filas; }

    /**
     * Reinicia el nivel: crea una nueva {@link Regla}, una nueva {@link Partida}
     * y limpia las filas guardadas.
     *
     * @param nuevoNivel número del nivel a iniciar (1–6)
     */
    public void resetNivel(int nuevoNivel) {
        this.regla          = new Regla(nuevoNivel);
        this.partida        = new Partida();
        this.filasGuardadas = new ArrayList<>();
        this.filasTest      = null;
    }
}