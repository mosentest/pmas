package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/10 0010.
 */
public class AttendRecord  implements Serializable{
    private String Id;//	主键id
    private String createdate;//	考勤日期
    private String part;//考勤时间段（早午晚代号）
    private String cq;//	出勤人数
    private String cd;    //迟到人数
    private String qj;//	请假人数
    private String qq;//	缺勤人数
    private String dj;    //冻结人数
    private String ts;    //特殊人数
    private String depart_departname; //	班别

    public AttendRecord() {
    }

    public AttendRecord(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.Id = jsonObject.getString("Id");
                this.createdate = jsonObject.getString("createdate");
                this.part = jsonObject.getString("part");
                this.cq = jsonObject.getString("cq");
                this.cd = jsonObject.getString("cd");
                this.qj = jsonObject.getString("qj");
                this.qq = jsonObject.getString("qq");
                this.dj = jsonObject.getString("dj");
                this.ts = jsonObject.getString("ts");
                this.depart_departname = jsonObject.getString("depart.departname");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
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

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getDepart_departname() {
        return depart_departname;
    }

    public void setDepart_departname(String depart_departname) {
        this.depart_departname = depart_departname;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttendRecord{");
        sb.append("Id='").append(Id).append('\'');
        sb.append(", createdate='").append(createdate).append('\'');
        sb.append(", part='").append(part).append('\'');
        sb.append(", cq='").append(cq).append('\'');
        sb.append(", cd='").append(cd).append('\'');
        sb.append(", qj='").append(qj).append('\'');
        sb.append(", qq='").append(qq).append('\'');
        sb.append(", dj='").append(dj).append('\'');
        sb.append(", ts='").append(ts).append('\'');
        sb.append(", depart_departname='").append(depart_departname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
