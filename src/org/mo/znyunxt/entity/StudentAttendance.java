package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/10 0010.
 */
public class StudentAttendance implements Serializable {
    private String id;//	考勤报表id
    private String createTime;//	创建时间
    private String depart_departname;//	学生所在班级
    private String depart_id;//	学生所在班级id
    private String student_name;//	学生姓名
    private String part;//	早午晚
    private String realAttend;//	真实考勤（考勤代号）
    private String lastOccurtime;//	最后进出时间
    private String lastIo;//	最后进出口（io对应的ioname）

    public StudentAttendance() {
    }

    public StudentAttendance(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.createTime = jsonObject.getString("createTime");
                this.depart_departname = jsonObject.getString("depart.departname");
                this.depart_id = jsonObject.getString("depart.id");
                this.student_name = jsonObject.getString("student.name");
                this.part = CodeUtil.getPart(Integer.parseInt(jsonObject.getString("part")));
                this.realAttend = CodeUtil.getAttendName(Integer.parseInt(jsonObject.getString("realAttend")));
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getRealAttend() {
        return realAttend;
    }

    public void setRealAttend(String realAttend) {
        this.realAttend = realAttend;
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
        final StringBuilder sb = new StringBuilder("StudentAttendance{");
        sb.append("id='").append(id).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", depart_departname='").append(depart_departname).append('\'');
        sb.append(", depart_id='").append(depart_id).append('\'');
        sb.append(", student_name='").append(student_name).append('\'');
        sb.append(", part='").append(part).append('\'');
        sb.append(", realAttend='").append(realAttend).append('\'');
        sb.append(", lastOccurtime='").append(lastOccurtime).append('\'');
        sb.append(", lastIo='").append(lastIo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
