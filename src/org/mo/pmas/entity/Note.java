package org.mo.pmas.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class Note implements Serializable {

    private Integer id;

    private String title;

    private String createDate;

    private String content;

    private NoteGroup noteGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createDate='" + createDate + '\'' +
                ", content='" + content + '\'' +
                ", noteGroup=" + noteGroup.toString() +
                '}';
    }
}
