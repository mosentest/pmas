package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/16 0016.
 */
public class Semester implements Serializable {
    private String id;//	主键ID
    private String year;//	学年
    private String semester;//	学期
    private String name;//	学期名称

    public Semester() {
    }

    public Semester(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.year = jsonObject.getString("year");
                this.semester = jsonObject.getString("semester");
                this.name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Semester{");
        sb.append("id='").append(id).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append(", semester='").append(semester).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
