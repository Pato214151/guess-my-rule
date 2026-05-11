package com.example.DAO;

import java.util.List;

/**
 * Interfaz genérica de operaciones CRUD básicas sobre un repositorio.
 *
 * @param <T> tipo de entidad que devuelven las operaciones de lectura
 * @param <I> tipo de entrada que recibe la operación de creación
 */
public interface CRUD<T, I> {

    /**
     * Persiste una nueva entidad a partir del objeto de entrada.
     *
     * @param input datos necesarios para crear el registro
     * @return {@code true} si la operación fue exitosa
     */
    boolean create(I input);

    /**
     * Recupera una entidad por su identificador.
     *
     * @param <K> tipo del identificador
     * @param id  valor del identificador
     * @return entidad encontrada, o {@code null} si no existe
     */
    <K> T readOne(K id);

    /**
     * Recupera todas las entidades del repositorio.
     *
     * @return lista con todos los registros disponibles
     */
    List<T> readAll();
}