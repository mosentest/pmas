package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;

/**w
 * Created by moziqi on 2014/12/31 0031.
 */
public class Schedule extends BmobObject {
    private String title;
    private BmobDate remindDate;
    private String content;
    private BmobPointer scheduleGroup;
    private BmobPointer user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobDate getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(BmobDate remindDate) {
        this.remindDate = remindDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobPointer getScheduleGroup() {
        return scheduleGroup;
    }

    public void setScheduleGroup(BmobPointer scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
