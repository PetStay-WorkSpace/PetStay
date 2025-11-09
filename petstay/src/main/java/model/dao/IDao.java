package model.dao;

import java.util.List;

/**
 * @author lohra
 */
public interface IDao<T> {
    void save(T obj);
    void delete(T obj);
    T find(int id);
    List<T> findAll();
}
