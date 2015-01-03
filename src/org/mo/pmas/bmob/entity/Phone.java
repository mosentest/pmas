package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class Phone extends BmobObject {
    private String phoneNumber;
    private BmobPointer contact;
    private BmobPointer user;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BmobPointer getContact() {
        return contact;
    }

    public void setContact(BmobPointer contact) {
        this.contact = contact;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
