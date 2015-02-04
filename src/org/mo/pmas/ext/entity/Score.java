package org.mo.pmas.ext.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.mo.pmas.entity.BaseColumn;

/**
 * Created by moziqi on 2015/2/4 0004.
 */
@DatabaseTable(tableName = "tb_score")
public class Score extends BaseColumn {

    @DatabaseField(index = true, columnName = "subject", unique = true, canBeNull = false)
    private String subject;//科目

    @DatabaseField(columnName = "score")
    private String score;//成绩

    @DatabaseField(columnName = "classroom")
    private String classroom;//考室

    @DatabaseField(columnName = "time")
    private String time;//考试时间

    @DatabaseField(columnName = "other_info1")
    private String otherInfo1;

    @DatabaseField(columnName = "other_info2")
    private String otherInfo2;

    @DatabaseField(columnName = "other_info3")
    private String otherInfo3;

    @DatabaseField(columnName = "other_info4")
    private String otherInfo4;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private User user;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtherInfo1() {
        return otherInfo1;
    }

    public void setOtherInfo1(String otherInfo1) {
        this.otherInfo1 = otherInfo1;
    }

    public String getOtherInfo2() {
        return otherInfo2;
    }

    public void setOtherInfo2(String otherInfo2) {
        this.otherInfo2 = otherInfo2;
    }

    public String getOtherInfo3() {
        return otherInfo3;
    }

    public void setOtherInfo3(String otherInfo3) {
        this.otherInfo3 = otherInfo3;
    }

    public String getOtherInfo4() {
        return otherInfo4;
    }

    public void setOtherInfo4(String otherInfo4) {
        this.otherInfo4 = otherInfo4;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Score{" +
                "subject='" + subject + '\'' +
                ", score='" + score + '\'' +
                ", classroom='" + classroom + '\'' +
                ", time='" + time + '\'' +
                ", otherInfo1='" + otherInfo1 + '\'' +
                ", otherInfo2='" + otherInfo2 + '\'' +
                ", otherInfo3='" + otherInfo3 + '\'' +
                ", otherInfo4='" + otherInfo4 + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
