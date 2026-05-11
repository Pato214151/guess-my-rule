package com.example.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    @Test
    void puntajeMaximo_sinTiempoNiIntentos() {
        Partida p = new Partida();
        p.setTiempo(0);
        p.setIntentos(0);
        assertEquals(1000, p.calcularPuntaje());    // 1000 - 0 - 0 = 1000
    }

    @Test
    void penalizacionPorTiempo() {
        Partida p = new Partida();
        p.setTiempo(20);
        p.setIntentos(0);
        assertEquals(800, p.calcularPuntaje());     // 1000 - (20*5) = 900
    }

    @Test
    void penalizacionPorIntentos() {
        Partida p = new Partida();
        p.setTiempo(0);
        p.setIntentos(3);
        assertEquals(850, p.calcularPuntaje());     // 1000 - (3*50) = 850
    }

    @Test
    void penalizacionCombinada() {
        Partida p = new Partida();
        p.setTiempo(10);
        p.setIntentos(2);
        assertEquals(850, p.calcularPuntaje());     // 1000 - 50 - 100 = 850
    }

    @Test
    void puntajeNuncaNegativo() {
        Partida p = new Partida();
        p.setTiempo(999);
        p.setIntentos(999);
        assertEquals(0, p.calcularPuntaje());       // Math.max(0, número enorme negativo)
    }

    @Test
    void puntajeExactoEnCero() {
        Partida p = new Partida();
        p.setTiempo(0);
        p.setIntentos(20);
        assertEquals(0, p.calcularPuntaje());       // 1000 - (20*50) = 0 justo
    }
}