package org.mo.pmas.bmob.entity.repository;

import cn.bmob.v3.BmobObject;

import java.util.List;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public interface BaseRepository<T extends BmobObject> {

    public static final int STATE_REFRESH = 0;// 下拉刷新

    public static final int STATE_MORE = 1;// 加载更多

    public int limit = 15; // 每页的数据是15条

    public int curPage = 0;// 当前页的编号，从0开始

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public T findOne(String objectId);

    public int countAll();

    public List<T> findAllByLimit(int curPage, int mLimit);


}
