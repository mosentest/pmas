package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class NoteGroup extends BmobObject {
    private String name;
    private String description;
    private BmobRelation notes;
    private BmobPointer user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobRelation getNotes() {
        return notes;
    }

    public void setNotes(BmobRelation notes) {
        this.notes = notes;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
