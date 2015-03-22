package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 详细用户信息模块用的
 */
public class UserDetail implements Serializable {

    /**
     * 用户主键id
     */
    private String id;
    /**
     * 真实名字
     */
    private String name;

    /**
     * 用户所在部门名称
     */
    private String departname;
    /**
     * 用户所在部门id
     */
    private String departid;
    /**
     * 性别：0男，1女
     */
    private String sex;
    /**
     * 角色名称
     */
    private String rolename;
    /**
     * 角色id
     */
    private String roleid;
    /**
     * 角色代号（rolecode）
     */
    private String rolecode;
    /**
     * 学号
     */
    private String sxh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDetail{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", departname='").append(departname).append('\'');
        sb.append(", departid='").append(departid).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", rolename='").append(rolename).append('\'');
        sb.append(", roleid='").append(roleid).append('\'');
        sb.append(", rolecode='").append(rolecode).append('\'');
        sb.append(", sxh='").append(sxh).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public UserDetail() {
    }

    public UserDetail(String jsonStr) {
        if (jsonStr != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.name = jsonObject.getString("name");
                this.departname = jsonObject.getString("departname");
                this.departid = jsonObject.getString("departid");
                this.sex = jsonObject.getString("sex");
                this.rolename = jsonObject.getString("rolename");
                this.roleid = jsonObject.getString("roleid");
                this.rolecode = jsonObject.getString("rolecode");
                this.sxh = jsonObject.getString("sxh");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
