package org.mo.pmas.entity;

import java.io.Serializable;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class Schedule implements Serializable {

    private int id;

    private String title;

    private String remindDate;

    private String content;

    private ScheduleGroup scheduleGroup;

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

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    public void setScheduleGroup(ScheduleGroup scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", remindDate='" + remindDate + '\'' +
                ", content='" + content + '\'' +
                ", scheduleGroup=" + scheduleGroup.toString() +
                '}';
    }
}
