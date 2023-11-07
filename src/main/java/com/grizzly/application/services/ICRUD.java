package com.grizzly.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Function;

public interface ICRUD<T, K> {
    final Logger logger = LogManager.getLogger(ICRUD.class);

    public void insert(T record) throws Exception;

    public T read(K id) throws Exception;


    public T read(String query, List<?> paramters) throws Exception;

    public List<T> readALL(String query, List<?> paramters) throws Exception;


    void update(T record) throws Exception;

    public void update(String query, List<?> paramters) throws Exception;

    public void delete(K id) throws Exception;

    public void delete(String query, List<?> paramters) throws Exception;

}
