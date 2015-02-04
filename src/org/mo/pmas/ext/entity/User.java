package org.mo.pmas.ext.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.mo.pmas.entity.BaseColumn;

/**
 * Created by moziqi on 2015/2/4 0004.
 */
@DatabaseTable(tableName = "tb_user")
public class User extends BaseColumn {

    @DatabaseField(index = true, columnName = "username", unique = true, canBeNull = false)
    private String username;//学号吧

    @DatabaseField(columnName = "name", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "password", canBeNull = false)
    private String password;

    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    private ForeignCollection<Score> scores;//对应成绩查询

    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    private ForeignCollection<Attendance> attendances;//对应成绩查询

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<Score> getScores() {
        return scores;
    }

    public void setScores(ForeignCollection<Score> scores) {
        this.scores = scores;
    }

    public ForeignCollection<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(ForeignCollection<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
