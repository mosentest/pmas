package org.mo.pmas.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2015/1/13 0013.
 */
public interface BaseRepository<T extends Serializable> {
    public boolean save(T entity);

    public boolean update(T entity);

    public boolean delete(T entity);

    public T findOneById(Serializable id);

    public T findOneByName(String name);

    public List<T> findAll();
}
