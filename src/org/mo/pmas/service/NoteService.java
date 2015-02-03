package org.mo.pmas.service;

import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.support.DatabaseConnection;

import org.mo.pmas.entity.Note;
import org.mo.pmas.repository.NoteRepository;
import org.mo.taskmanager.db.DBHelper;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * Created by moziqi on 2015/1/28 0028.
 */
public class NoteService implements INoteService {
    // 添加事物
    public DatabaseConnection connection;
    private NoteRepository mNoteRepository;
    private Context mContext;

    public NoteService(Context mContext) {
        this.mContext = mContext;
        mNoteRepository = new NoteRepository(mContext);
        connection = new AndroidDatabaseConnection(
                DBHelper.getHelper(mContext).getWritableDatabase(), true);
    }

    @Override
    public boolean save(Note note) {
        boolean result = false;
        Savepoint savepoint = null;
        try {
            savepoint =  connection.setSavePoint("note");
            result = mNoteRepository.save(note);
            connection.commit(savepoint);
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean update(Note note) {
        boolean result = mNoteRepository.update(note);
        return result;
    }

    @Override
    public boolean delete(Note note) {
        boolean result = mNoteRepository.delete(note);
        return result;
    }

    public boolean deleteById(long id) {
        boolean result = mNoteRepository.deleteById(id);
        return result;
    }

    @Override
    public Note getOneById(long id) {
        return mNoteRepository.findOneById(id);
    }

    @Override
    public List<Note> getAll() {
        List<Note> notes = mNoteRepository.findAll();
        return notes;
    }


}
