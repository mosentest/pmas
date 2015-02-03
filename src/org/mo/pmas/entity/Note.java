package org.mo.pmas.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
@DatabaseTable(tableName = "tb_note")
public class Note extends BaseColumn {

    @DatabaseField(index = true, columnName = "title", unique = true, canBeNull = false)
    private String title;

    @DatabaseField(columnName = "content")
    private String content;

    @DatabaseField(columnName = "note_type")
    private int noteType;

    private NoteGroup noteGroup;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteGroup getNoteGroup() {
        return noteGroup;
    }

    public void setNoteGroup(NoteGroup noteGroup) {
        this.noteGroup = noteGroup;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", noteType='" + noteType + '\'' +
                '}';
    }
}
