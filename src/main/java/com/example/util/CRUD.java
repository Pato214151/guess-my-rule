package com.example.util;

import java.util.List;

public interface CRUD<T> {
    String create(T t);
    <K> T readOne(K id);
    List<T> readAll();
}