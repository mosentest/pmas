package org.mo.pmas.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class Contact implements Serializable {

    private Integer id;//编号

    private String name;//名字

//    private String birthday;//生日

    private String email;//邮箱地址

    private String address;//通讯地址

    private Bitmap contactPhoto;//联系人照片

    private String phoneNumber;//联系电话

    private String sortLetters;  //显示数据拼音的首字母

    private String contactGroup;//联系组名称

    private String objectId;

    private String createdAt;

    private ContactGroup mContactGroup;

    private MyUser myUser;

    private List<Phone> phones;

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

    public ContactGroup getmContactGroup() {
        return mContactGroup;
    }

    public void setmContactGroup(ContactGroup mContactGroup) {
        this.mContactGroup = mContactGroup;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }


    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }

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

    public Bitmap getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(Bitmap contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contactPhoto=" + contactPhoto +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                ", contactGroup='" + contactGroup + '\'' +
                ", objectId='" + objectId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", phones=" + phones +
                '}';
    }
}
