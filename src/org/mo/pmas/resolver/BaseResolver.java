package org.mo.pmas.resolver;


import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2015/1/9 0009.
 */
public interface BaseResolver<T extends Serializable> {
    public boolean save(T entity);

    public boolean update(T entity);

    public boolean delete(T entity);

    public List<T> findAll();
}
