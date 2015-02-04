package org.mo.pmas.repository;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import org.mo.pmas.ext.entity.Score;
import org.mo.pmas.ext.entity.User;
import org.mo.taskmanager.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by moziqi on 2015/1/9 0009.
 */
public class ScoreRepository implements BaseRepository<Score, Long> {
    private Context mContext;
    private Dao<Score, Long> mNoteLongDao;
    private DBHelper mDbHelper;

    public ScoreRepository(Context mContext) {
        this.mContext = mContext;
        this.mDbHelper = DBHelper.getHelper(mContext);
        try {
            this.mNoteLongDao = mDbHelper.getDao(Score.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Score entity) {
        boolean flag = false;
        try {
            int i = mNoteLongDao.create(entity);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean update(Score entity) {
        boolean flag = false;
        try {
            int i = mNoteLongDao.update(entity);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean delete(Score entity) {
        boolean flag = false;
        try {
            int i = mNoteLongDao.delete(entity);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Score findOneById(Long id) {
        Score note = null;
        try {
            note = mNoteLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }

    @Override
    public Score findOneByName(String name) {
        List<Score> title = null;
        try {
            title = mNoteLongDao.queryForEq("subject", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (title.size() > 0) {
            return title.get(0);
        }
        return null;
    }

    @Override
    public List<Score> findAll() {
        List<Score> notes = null;
        try {
            notes = mNoteLongDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public long countAll() {
        long countOf = 0;
        try {
            countOf = mNoteLongDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countOf;
    }

    @Override
    public List<Score> findAllByLimit(long currentPage, long size) {
        return null;
    }
}
