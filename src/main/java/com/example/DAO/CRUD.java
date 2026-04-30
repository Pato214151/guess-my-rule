package com.example.DAO;

import java.util.List;

public interface CRUD<T, I> {
    boolean create(I input);
    <K> T readOne(K id);
    List<T> readAll();
}