package org.mo.pmas.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moziqi on 2015/1/9 0009.
 */
public class MyUser implements Parcelable {
    private Integer id;
    private String objectId;
    private String username;
    private String password;
    private String createdAt;
    private String email;
    private String emailVerified;

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified='" + emailVerified + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(objectId);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(createdAt);
        dest.writeString(email);
        dest.writeString(emailVerified);
    }

    public static final Parcelable.Creator<MyUser> myUserCreator = new Creator<MyUser>() {
        @Override
        public MyUser createFromParcel(Parcel source) {
            MyUser myUser = new MyUser();
            myUser.setId(source.readInt());
            myUser.setObjectId(source.readString());
            myUser.setUsername(source.readString());
            myUser.setPassword(source.readString());
            myUser.setCreatedAt(source.readString());
            myUser.setEmail(source.readString());
            myUser.setEmailVerified(source.readString());
            return myUser;
        }

        @Override
        public MyUser[] newArray(int size) {
            return new MyUser[size];
        }
    };

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }
}
