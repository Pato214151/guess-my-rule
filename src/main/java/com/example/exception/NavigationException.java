package com.example.exception;

/**
 * Excepción no verificada que se lanza cuando ocurre un error
 * durante la navegación entre pantallas FXML.
 */
public class NavigationException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje descriptivo y la causa original.
     *
     * @param message descripción del error de navegación
     * @param cause   excepción original que originó el fallo
     */
    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
