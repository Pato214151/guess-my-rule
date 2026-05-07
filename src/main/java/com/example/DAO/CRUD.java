package com.example.DAO;

import java.util.List;

/**
 * Interfaz genérica que define el contrato de acceso a datos.
 *
 * @param <T> tipo de la entidad retornada (p. ej. {@code Puntaje})
 * @param <I> tipo del objeto de entrada para creación (p. ej. {@code GameSession})
 */
public interface CRUD<T, I> {

    /**
     * Persiste un nuevo registro a partir del objeto de entrada.
     *
     * @param input objeto con los datos a persistir
     * @return {@code true} si la operación fue exitosa; {@code false} en caso contrario
     */
    boolean create(I input);

    /**
     * Busca y retorna un único registro por su identificador.
     *
     * @param <K> tipo del identificador
     * @param id  valor del identificador
     * @return la entidad encontrada, o {@code null} si no existe
     */
    <K> T readOne(K id);

    /**
     * Retorna todos los registros disponibles.
     *
     * @return lista con todas las entidades; puede estar vacía pero nunca es {@code null}
     */
    List<T> readAll();
}