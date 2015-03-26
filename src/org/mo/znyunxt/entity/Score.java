package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/3/25 0025.
 */
public class Score implements Serializable {
    private String id;//主键ID
    private String semesterid;//学期ID
    private String exam_name;//考试名称
    private String exam_id;//考试ID
    private String wenli;//文理
    private String depart_departname;//班别
    private String depart_id;//班别ID
    private String studentName;//学生姓名
    private String yuwen;//语文
    private String shuxue;//数学
    private String yingyu;//英语
    private String kouyu;//口语
    private String zhenzhi;//政治
    private String lishi;//历史
    private String dili;//地理
    private String wuli;//物理
    private String huaxu;//化学
    private String shengwu;//生物
    private String zonghe;//综合
    private String total;//总分
    private String ranking;//排名

    public Score() {
    }

    public Score(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.semesterid = jsonObject.getString("semesterid");//学期ID
                this.exam_name = jsonObject.getString("exam.name");//考试名称
                this.exam_id = jsonObject.getString("exam.id");//考试ID
                this.wenli = "文理:" + CodeUtil.getWenLi(Integer.parseInt(jsonObject.getString("wenli")));//文理
                this.depart_departname = "班别:" + jsonObject.getString("depart.departname");//班别
                this.depart_id = jsonObject.getString("depart.id");//班别ID
                this.studentName = "姓名:" + jsonObject.getString("studentName");//学生姓名
                this.yuwen = "语文:" + jsonObject.getString("yuwen");//语文
                this.shuxue = "数学:" + jsonObject.getString("shuxue");//数学
                this.yingyu = "英语:" + jsonObject.getString("yingyu");//英语
                this.kouyu = "口语:" + (jsonObject.getString("kouyu").length() == 0 || jsonObject.getString("kouyu") == null ? "--" : jsonObject.getString("kouyu"));//口语
                this.zhenzhi = "政治:" + jsonObject.getString("zhenzhi");//政治
                this.lishi = "历史:" + jsonObject.getString("lishi");//历史
                this.dili = "地理:" + jsonObject.getString("dili");//地理
                this.wuli = "物理:" + jsonObject.getString("wuli");//物理
                this.huaxu = "化学:" + jsonObject.getString("huaxu");//化学
                this.shengwu = "生物:" + jsonObject.getString("shengwu");//生物
                this.zonghe = "综合:" + (jsonObject.getString("zonghe").length() == 0 || jsonObject.getString("zonghe") == null ? "--" : jsonObject.getString("zonghe"));//综合
                this.total = "总分:" + jsonObject.getString("total");//总分
                this.ranking = "排名:" + jsonObject.getString("ranking");//排名
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

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getWenli() {
        return wenli;
    }

    public void setWenli(String wenli) {
        this.wenli = wenli;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getYuwen() {
        return yuwen;
    }

    public void setYuwen(String yuwen) {
        this.yuwen = yuwen;
    }

    public String getShuxue() {
        return shuxue;
    }

    public void setShuxue(String shuxue) {
        this.shuxue = shuxue;
    }

    public String getYingyu() {
        return yingyu;
    }

    public void setYingyu(String yingyu) {
        this.yingyu = yingyu;
    }

    public String getKouyu() {
        return kouyu;
    }

    public void setKouyu(String kouyu) {
        this.kouyu = kouyu;
    }

    public String getZhenzhi() {
        return zhenzhi;
    }

    public void setZhenzhi(String zhenzhi) {
        this.zhenzhi = zhenzhi;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getDili() {
        return dili;
    }

    public void setDili(String dili) {
        this.dili = dili;
    }

    public String getWuli() {
        return wuli;
    }

    public void setWuli(String wuli) {
        this.wuli = wuli;
    }

    public String getHuaxu() {
        return huaxu;
    }

    public void setHuaxu(String huaxu) {
        this.huaxu = huaxu;
    }

    public String getShengwu() {
        return shengwu;
    }

    public void setShengwu(String shengwu) {
        this.shengwu = shengwu;
    }

    public String getZonghe() {
        return zonghe;
    }

    public void setZonghe(String zonghe) {
        this.zonghe = zonghe;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Score{");
        sb.append("id='").append(id).append('\'');
        sb.append(", semesterid='").append(semesterid).append('\'');
        sb.append(", exam_name='").append(exam_name).append('\'');
        sb.append(", exam_id='").append(exam_id).append('\'');
        sb.append(", wenli='").append(wenli).append('\'');
        sb.append(", depart_departname='").append(depart_departname).append('\'');
        sb.append(", depart_id='").append(depart_id).append('\'');
        sb.append(", studentName='").append(studentName).append('\'');
        sb.append(", yuwen='").append(yuwen).append('\'');
        sb.append(", shuxue='").append(shuxue).append('\'');
        sb.append(", yingyu='").append(yingyu).append('\'');
        sb.append(", kouyu='").append(kouyu).append('\'');
        sb.append(", zhenzhi='").append(zhenzhi).append('\'');
        sb.append(", lishi='").append(lishi).append('\'');
        sb.append(", dili='").append(dili).append('\'');
        sb.append(", wuli='").append(wuli).append('\'');
        sb.append(", huaxu='").append(huaxu).append('\'');
        sb.append(", shengwu='").append(shengwu).append('\'');
        sb.append(", zonghe='").append(zonghe).append('\'');
        sb.append(", total='").append(total).append('\'');
        sb.append(", ranking='").append(ranking).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
