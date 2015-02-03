package org.mo.pmas.repository;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import org.mo.pmas.entity.Note;
import org.mo.taskmanager.db.DBHelper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by moziqi on 2015/1/28 0028.
 */
public class NoteRepository implements BaseRepository<Note, Long> {

    private Context mContext;
    private Dao<Note, Long> mNoteLongDao;
    private DBHelper mDbHelper;

    public NoteRepository(Context mContext) {
        this.mContext = mContext;
        this.mDbHelper = DBHelper.getHelper(mContext);
        try {
            this.mNoteLongDao = mDbHelper.getDao(Note.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Note entity) {
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
    public boolean update(Note entity) {
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
    public boolean delete(Note entity) {
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

    public boolean deleteById(Long id) {
        boolean flag = false;
        try {
            int i = mNoteLongDao.deleteById(id);

            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Note findOneById(Long id) {
        Note note = null;
        try {
            note = mNoteLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }

    @Override
    public Note findOneByName(String name) {
        List<Note> title = null;
        try {
            title = mNoteLongDao.queryForEq("title", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (title.size() > 0) {
            return title.get(0);
        }
        return null;
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = null;
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
    public List<Note> findAllByLimit(long currentPage, long size) {
        return null;
    }
}
