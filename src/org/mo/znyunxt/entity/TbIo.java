package org.mo.znyunxt.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 进出类型
 *
 * @author Administrator
 */
public class TbIo implements Serializable{
    /**
     * 主键id
     */
    private String id;
    /**
     * 进出代码
     */
    private String io;
    /**
     * 门代码
     */
    private String gate;
    /**
     * 进或出 出0，进1
     */
    private String ioType;
    /**
     * 进出短名
     */
    private String ioSortName;
    /**
     * 大门类型 0校门，1宿舍
     */
    private String gateType;
    /**
     * 0不在校，1在校
     */
    private String inschool;
    /**
     * 进出名称,中文名称
     */
    private String ioname;

    public TbIo() {
    }


    public TbIo(String jsonStr) {
        JSONObject jsonObject = null;
        if (jsonStr != null) {
            try {
                jsonObject = new JSONObject(jsonStr);
                this.id = jsonObject.getString("id");
                this.io = jsonObject.getString("io");
                this.gate = jsonObject.getString("gate");
                this.ioType = Integer.parseInt(jsonObject.getString("ioType")) == 0 ? "出" : "进";
                this.ioSortName = jsonObject.getString("ioSortName");
                this.gateType = Integer.parseInt(jsonObject.getString("gateType")) == 0 ? "校门" : "宿舍";
                this.inschool = Integer.parseInt(jsonObject.getString("inschool")) == 0 ? "不在校" : "在校";
                this.ioname = jsonObject.getString("ioname");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TbIo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", io='").append(io).append('\'');
        sb.append(", gate='").append(gate).append('\'');
        sb.append(", ioType='").append(ioType).append('\'');
        sb.append(", ioSortName='").append(ioSortName).append('\'');
        sb.append(", gateType='").append(gateType).append('\'');
        sb.append(", inschool='").append(inschool).append('\'');
        sb.append(", ioname='").append(ioname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    public String getIoSortName() {
        return ioSortName;
    }

    public void setIoSortName(String ioSortName) {
        this.ioSortName = ioSortName;
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    public String getInschool() {
        return inschool;
    }

    public void setInschool(String inschool) {
        this.inschool = inschool;
    }

    public String getIoname() {
        return ioname;
    }

    public void setIoname(String ioname) {
        this.ioname = ioname;
    }
}
