package org.mo.pmas.service;

import org.mo.pmas.entity.Note;

import java.util.List;

/**
 * Created by moziqi on 2015/1/28 0028.
 */
public interface INoteService {
    boolean save(Note note);

    boolean update(Note note);

    boolean delete(Note note);

    Note getOneById(long id);

    List<Note> getAll();
}
