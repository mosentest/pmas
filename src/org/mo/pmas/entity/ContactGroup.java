package org.mo.pmas.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class ContactGroup implements Parcelable {

    private Integer id;

    private String name;

    private String description;

    private MyUser myUser;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
