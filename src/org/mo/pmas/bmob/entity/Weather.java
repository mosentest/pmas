package org.mo.pmas.bmob.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class Weather extends BmobObject{
    private BmobDate currentDate;
    private String description;
    private BmobPointer user;

    public BmobDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(BmobDate currentDate) {
        this.currentDate = currentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }
}
