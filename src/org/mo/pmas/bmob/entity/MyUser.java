package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class MyUser extends BmobUser {
    private BmobRelation contacts;
    private BmobRelation contactGroups;
    private BmobRelation neighborhoods;
    private BmobRelation notes;
    private BmobRelation noteGroups;
    private BmobRelation phones;
    private BmobRelation schedules;
    private BmobRelation scheduleGroups;
    private BmobRelation weathers;

    public BmobRelation getContacts() {
        return contacts;
    }

    public void setContacts(BmobRelation contacts) {
        this.contacts = contacts;
    }

    public BmobRelation getContactGroups() {
        return contactGroups;
    }

    public void setContactGroups(BmobRelation contactGroups) {
        this.contactGroups = contactGroups;
    }

    public BmobRelation getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(BmobRelation neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public BmobRelation getNotes() {
        return notes;
    }

    public void setNotes(BmobRelation notes) {
        this.notes = notes;
    }

    public BmobRelation getNoteGroups() {
        return noteGroups;
    }

    public void setNoteGroups(BmobRelation noteGroups) {
        this.noteGroups = noteGroups;
    }

    public BmobRelation getPhones() {
        return phones;
    }

    public void setPhones(BmobRelation phones) {
        this.phones = phones;
    }

    public BmobRelation getSchedules() {
        return schedules;
    }

    public void setSchedules(BmobRelation schedules) {
        this.schedules = schedules;
    }

    public BmobRelation getScheduleGroups() {
        return scheduleGroups;
    }

    public void setScheduleGroups(BmobRelation scheduleGroups) {
        this.scheduleGroups = scheduleGroups;
    }

    public BmobRelation getWeathers() {
        return weathers;
    }

    public void setWeathers(BmobRelation weathers) {
        this.weathers = weathers;
    }
}
