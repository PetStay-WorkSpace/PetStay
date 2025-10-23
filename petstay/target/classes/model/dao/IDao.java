package model.dao;

import java.util.List;

/**
 * @author lohra
 */
public interface IDao<T> {
    void save(T obj);
    boolean delete(T obj);
    T find(T obj);
    List<T> findAll();
}
