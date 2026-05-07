package com.example.exception;

/**
 * Excepción no verificada que envuelve errores de navegación entre pantallas.
 * <p>
 * Al extender {@link RuntimeException}, los manejadores {@code @FXML} no necesitan
 * declarar {@code throws} para propagarla, manteniendo el código de navegación limpio.
 * Preserva la causa original para facilitar el diagnóstico.
 * </p>
 */
public class NavigationException extends RuntimeException {
    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
