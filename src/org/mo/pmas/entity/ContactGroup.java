package org.mo.pmas.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class ContactGroup implements Serializable {

    private Integer id;

    private String objectId;

    private String createdAt;

    private String name;

    private String description;

    private MyUser myUser;

    private List<Contact> contactLists;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public List<Contact> getContactLists() {
        return contactLists;
    }

    public void setContactLists(List<Contact> contactLists) {
        this.contactLists = contactLists;
    }

    @Override
    public String toString() {
        return "ContactGroup{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
