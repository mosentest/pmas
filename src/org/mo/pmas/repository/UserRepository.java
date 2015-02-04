package org.mo.pmas.repository;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import org.mo.pmas.entity.Note;
import org.mo.pmas.ext.entity.User;
import org.mo.pmas.ext.entity.User;
import org.mo.taskmanager.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by moziqi on 2015/1/9 0009.
 */
public class UserRepository implements BaseRepository<User, Long> {
    private Context mContext;
    private Dao<User, Long> mNoteLongDao;
    private DBHelper mDbHelper;

    public UserRepository(Context mContext) {
        this.mContext = mContext;
        this.mDbHelper = DBHelper.getHelper(mContext);
        try {
            this.mNoteLongDao = mDbHelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(User entity) {
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
    public boolean update(User entity) {
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
    public boolean delete(User entity) {
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
    public User findOneById(Long id) {
        User note = null;
        try {
            note = mNoteLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }

    @Override
    public User findOneByName(String name) {
        List<User> title = null;
        try {
            title = mNoteLongDao.queryForEq("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (title.size() > 0) {
            return title.get(0);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> notes = null;
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
    public List<User> findAllByLimit(long currentPage, long size) {
        return null;
    }
}
