package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class Contact extends BmobObject {
    private String name;
    private String birthday;
    private String email;
    private String address;
    private BmobFile contactPhoto;
    private BmobPointer contactGroup;
    private BmobRelation phones;
    private BmobPointer user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BmobFile getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(BmobFile contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public BmobPointer getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(BmobPointer contactGroup) {
        this.contactGroup = contactGroup;
    }

    public BmobRelation getPhones() {
        return phones;
    }

    public void setPhones(BmobRelation phones) {
        this.phones = phones;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
