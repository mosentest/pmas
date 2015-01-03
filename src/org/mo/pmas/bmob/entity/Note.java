package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class Note extends BmobObject {
    private String title;
    private String content;
    private BmobPointer noteGroup;
    private BmobPointer user;

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

    public BmobPointer getNoteGroup() {
        return noteGroup;
    }

    public void setNoteGroup(BmobPointer noteGroup) {
        this.noteGroup = noteGroup;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
