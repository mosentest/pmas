package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/10 0010.
 */
public class AttendCount implements Serializable {
    private String departname;//	班级名称
    private String departid;//	班级id
    private String countDate;//	统计时间参数
    private String countDate_begin;//	统计起始日期
    private String countDate_end;//	统计结束日期
    private String studentid;//	学生id
    private String stuname;//	学生姓名
    private String cq;//	出勤
    private String cd;//	迟到
    private String qj;//	请假
    private String qq;//	缺勤

    public AttendCount() {
    }

    public AttendCount(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.departname = jsonObject.getString("departname");
                this.departid = jsonObject.getString("departid");
                this.countDate = jsonObject.getString("countDate");
                this.countDate_begin = jsonObject.getString("countDate_begin");
                this.countDate_end = jsonObject.getString("countDate_end");
                this.studentid = jsonObject.getString("studentid");
                this.stuname = jsonObject.getString("stuname");
                this.cq = "出勤次数：" + jsonObject.getString("cq");
                this.cd = "迟到次数：" + jsonObject.getString("cd");
                this.qj = "请假次数：" + jsonObject.getString("qj");
                this.qq = "缺勤次数：" + jsonObject.getString("qq");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String getCountDate_begin() {
        return countDate_begin;
    }

    public void setCountDate_begin(String countDate_begin) {
        this.countDate_begin = countDate_begin;
    }

    public String getCountDate_end() {
        return countDate_end;
    }

    public void setCountDate_end(String countDate_end) {
        this.countDate_end = countDate_end;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getCq() {
        return cq;
    }

    public void setCq(String cq) {
        this.cq = cq;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttendCount{");
        sb.append("departname='").append(departname).append('\'');
        sb.append(", departid='").append(departid).append('\'');
        sb.append(", countDate='").append(countDate).append('\'');
        sb.append(", countDate_begin='").append(countDate_begin).append('\'');
        sb.append(", countDate_end='").append(countDate_end).append('\'');
        sb.append(", studentid='").append(studentid).append('\'');
        sb.append(", stuname='").append(stuname).append('\'');
        sb.append(", cq='").append(cq).append('\'');
        sb.append(", cd='").append(cd).append('\'');
        sb.append(", qj='").append(qj).append('\'');
        sb.append(", qq='").append(qq).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
