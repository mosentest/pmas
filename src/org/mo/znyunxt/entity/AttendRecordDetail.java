package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/10 0010.
 */
public class AttendRecordDetail implements Serializable{
    private String id;//	主键id
    private String student_name;    //学生姓名
    private String depart_departname;//	学生所在班级
    private String depart_id;    //学生所在班级id
    private String part;    //考勤时间段（早午晚代号）
    private String attendName;    //卡判断考勤
    private String realAttend;    //实际考勤（考勤代号）
    private String createTime;    //记录生成时间
    private String lastOccurtime;    //最后进出时间
    private String lastIo;    //最后进出口（io对应的ioname）

    public AttendRecordDetail() {
    }

    public AttendRecordDetail(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.student_name = jsonObject.getString("student.name");
                this.part = jsonObject.getString("part");
                this.depart_departname = jsonObject.getString("depart.departname");
                this.depart_id = jsonObject.getString("depart.id");
                this.attendName = jsonObject.getString("attendName");
                this.realAttend = jsonObject.getString("realAttend");
                this.createTime = jsonObject.getString("dj");
                this.lastOccurtime = jsonObject.getString("lastOccurtime");
                this.lastIo = jsonObject.getString("lastIo");
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

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getDepart_departname() {
        return depart_departname;
    }

    public void setDepart_departname(String depart_departname) {
        this.depart_departname = depart_departname;
    }

    public String getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(String depart_id) {
        this.depart_id = depart_id;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getAttendName() {
        return attendName;
    }

    public void setAttendName(String attendName) {
        this.attendName = attendName;
    }

    public String getRealAttend() {
        return realAttend;
    }

    public void setRealAttend(String realAttend) {
        this.realAttend = realAttend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastOccurtime() {
        return lastOccurtime;
    }

    public void setLastOccurtime(String lastOccurtime) {
        this.lastOccurtime = lastOccurtime;
    }

    public String getLastIo() {
        return lastIo;
    }

    public void setLastIo(String lastIo) {
        this.lastIo = lastIo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttendCountDetail{");
        sb.append("id='").append(id).append('\'');
        sb.append(", student_name='").append(student_name).append('\'');
        sb.append(", depart_departname='").append(depart_departname).append('\'');
        sb.append(", depart_id='").append(depart_id).append('\'');
        sb.append(", part='").append(part).append('\'');
        sb.append(", attendName='").append(attendName).append('\'');
        sb.append(", realAttend='").append(realAttend).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", lastOccurtime='").append(lastOccurtime).append('\'');
        sb.append(", lastIo='").append(lastIo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
