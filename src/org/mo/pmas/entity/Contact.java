package org.mo.pmas.entity;

import java.io.Serializable;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class Contact implements Serializable, Comparable<Contact> {

    private int id;

    private String name;

    private String birthday;

    private String phoneOne;

    private String phoneTwo;

    private String phoneThree;

    private String email;

    private String address;

    private ContactGroup contactGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneThree() {
        return phoneThree;
    }

    public void setPhoneThree(String phoneThree) {
        this.phoneThree = phoneThree;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
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

    public ContactGroup getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(ContactGroup contactGroup) {
        this.contactGroup = contactGroup;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneOne='" + phoneOne + '\'' +
                ", phoneTwo='" + phoneTwo + '\'' +
                ", phoneThree='" + phoneThree + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contactGroup=" + contactGroup.toString() +
                '}';
    }

    @Override
    public int compareTo(Contact another) {
        int tag = this.name.compareTo(another.name);
        if (tag != 0) {
            return tag;
        } else {
            return this.birthday.compareTo(another.birthday);
        }
    }
}