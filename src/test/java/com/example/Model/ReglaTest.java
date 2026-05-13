package com.example.Model;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class ReglaTest {

    // ─────────────────────────────────────────
    // NIVEL 1: x + 3
    // ─────────────────────────────────────────
    @Test
    void nivel1_valorNormal() {
        Regla regla = new Regla(1);
        assertEquals(9.0, regla.aplicarRegla(5));   // 5 + 3 = 8
    }

    @Test
    void nivel1_valorCero() {
        Regla regla = new Regla(1);
        assertEquals(3.0, regla.aplicarRegla(0));   // 0 + 3 = 3
    }

    @Test
    void nivel1_valorNegativo() {
        Regla regla = new Regla(1);
        assertEquals(1.0, regla.aplicarRegla(-2));  // -2 + 3 = 1
    }

    // ─────────────────────────────────────────
    // NIVEL 2: x * 2
    // ─────────────────────────────────────────
    @Test
    void nivel2_valorNormal() {
        Regla regla = new Regla(2);
        assertEquals(10.0, regla.aplicarRegla(5));  // 5 * 2 = 10
    }

    @Test
    void nivel2_valorCero() {
        Regla regla = new Regla(2);
        assertEquals(0.0, regla.aplicarRegla(0));   // 0 * 2 = 0
    }

    // ─────────────────────────────────────────
    // NIVEL 3: 2x + 1
    // ─────────────────────────────────────────
    @Test
    void nivel3_valorNormal() {
        Regla regla = new Regla(3);
        assertEquals(11.0, regla.aplicarRegla(5));  // 2*5 + 1 = 11
    }

    @Test
    void nivel3_valorNegativo() {
        Regla regla = new Regla(3);
        assertEquals(-3.0, regla.aplicarRegla(-2)); // 2*(-2) + 1 = -3
    }

    // ─────────────────────────────────────────
    // NIVEL 4: x²
    // ─────────────────────────────────────────
    @Test
    void nivel4_valorNormal() {
        Regla regla = new Regla(4);
        assertEquals(25.0, regla.aplicarRegla(5));  // 5² = 25
    }

    @Test
    void nivel4_valorNegativo_debeSerPositivo() {
        Regla regla = new Regla(4);
        assertEquals(4.0, regla.aplicarRegla(-2));  // (-2)² = 4 ← caso interesante
    }

    @Test
    void nivel4_valorCero() {
        Regla regla = new Regla(4);
        assertEquals(0.0, regla.aplicarRegla(0));   // 0² = 0
    }

    // ─────────────────────────────────────────
    // NIVEL 5: x² + x
    // ─────────────────────────────────────────
    @Test
    void nivel5_valorNormal() {
        Regla regla = new Regla(5);
        assertEquals(30.0, regla.aplicarRegla(5));  // 25 + 5 = 30
    }

    @Test
    void nivel5_valorCero() {
        Regla regla = new Regla(5);
        assertEquals(0.0, regla.aplicarRegla(0));   // 0 + 0 = 0
    }

    // ─────────────────────────────────────────
    // NIVEL 6: x³ - x
    // ─────────────────────────────────────────
    @Test
    void nivel6_valorNormal() {
        Regla regla = new Regla(6);
        assertEquals(120.0, regla.aplicarRegla(5)); // 125 - 5 = 120
    }

    @Test
    void nivel6_valorNegativo() {
        Regla regla = new Regla(6);
        assertEquals(-6.0, regla.aplicarRegla(-2)); // -8 - (-2) = -6
    }

    @Test
    void nivel6_valorCero() {
        Regla regla = new Regla(6);
        assertEquals(0.0, regla.aplicarRegla(0));   // 0 - 0 = 0
    }

    // ─────────────────────────────────────────
    // FORMATEAR
    // ─────────────────────────────────────────
    @Test
    void formatear_entero_sinDecimal() {
        assertEquals("9", Regla.formatear(9.0));    // 9.0 → "9"
    }

    @Test
    void formatear_decimal_conservaDecimal() {
        assertEquals("9.5", Regla.formatear(9.5));  // 9.5 → "9.5"
    }

    // ─────────────────────────────────────────
    // EVALUAR — integra aplicarRegla + formatear
    // ─────────────────────────────────────────
    @Test
    void evaluar_nivel1_retornaParCorrecto() {
        Regla regla = new Regla(1);
        Regla.ParInOut par = regla.evaluar(5);
        assertEquals("5", par.getEntrada());
        assertEquals("8", par.getSalida());         // 5 + 3 = 8
    }
}