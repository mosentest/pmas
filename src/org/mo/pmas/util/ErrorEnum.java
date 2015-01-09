package org.mo.pmas.util;

import java.util.HashMap;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public enum ErrorEnum {

    S9001(9001, "AppKey为空，请初始化"),
    S9002(9002, "解析返回数据出错"),
    S9003(9003, "上传文件出错"),
    S9004(9004, "文件上传失败"),
    S9005(9005, "批量操作只支持最多50条"),
    S9006(9006, "objectId为空"),
    S9007(9007, "文件大小超过10M"),
    S9008(9008, "上传文件不存在"),
    S9009(9009, "没有缓存数据"),
    S9010(9010, "网络超时"),
    S9011(9011, "BmobUser类不支持批量操作"),
    S9012(9012, "上下文为空"),
    S9013(9013, "BmobObject（数据表名称）格式不正确"),
    S9014(9014, "第三方账号授权失败"),
    S9015(9015, "其他错误均返回此code"),
    S9016(9016, "无网络连接，请检查您的手机网络"),
    S305(305,"帐号密码不能为空"),
    S100(100,"服务器太忙,请稍后...");

    private int code;
    private String message;
    private static final HashMap<Integer, ErrorEnum> map = new HashMap<Integer, ErrorEnum>();

    static {
        for (ErrorEnum errorEnum : values()) {
            map.put(errorEnum.code, errorEnum);
        }
    }

    private ErrorEnum(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static ErrorEnum ident(int code) {
        return map.get(code);
    }

}
