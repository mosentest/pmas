package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/18 0018.
 */
public class Exam implements Serializable {

    private String id;//	主键ID
    private String semesterid;//	学期ID
    private String examType;//	考试类型
    private String name;//	考试名称
    private String createDatetime;//创建时间

    public Exam() {
    }


    public Exam(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.semesterid = jsonObject.getString("semesterid");
                this.examType = CodeUtil.getExamType(Integer.parseInt(jsonObject.getString("examType")));
                this.name = jsonObject.getString("name");
                this.createDatetime = jsonObject.getString("createDatetime");
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

    public String getSemesterid() {
        return semesterid;
    }

    public void setSemesterid(String semesterid) {
        this.semesterid = semesterid;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Exam{");
        sb.append("id='").append(id).append('\'');
        sb.append(", semesterid='").append(semesterid).append('\'');
        sb.append(", examType='").append(examType).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDatetime='").append(createDatetime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
