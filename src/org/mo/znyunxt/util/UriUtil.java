package org.mo.znyunxt.util;

import java.util.HashMap;

public final class UriUtil {


    private static HashMap<Integer, String> ATTEND_NAME = new HashMap<Integer, String>();// 考勤代号
    private static HashMap<Integer, String> PART = new HashMap<Integer, String>();// 早午晚代号
    private static HashMap<String, String> ROLE_CODE = new HashMap<String, String>();// 角色代号

    static {

        ATTEND_NAME.put(0, "出勤");
        ATTEND_NAME.put(1, "迟到");
        ATTEND_NAME.put(2, "缺勤");
        ATTEND_NAME.put(3, "请假");
        ATTEND_NAME.put(4, "冻结");
        ATTEND_NAME.put(5, "特殊");

        PART.put(1, "早上");
        PART.put(2, "下午");
        PART.put(3, "晚上");

        ROLE_CODE.put("super", "超级管理员");
        ROLE_CODE.put("admin", "管理员");
        ROLE_CODE.put("student", "学生");
        ROLE_CODE.put("banzhuren", "班主任");
        ROLE_CODE.put("teacher", "老师");
        ROLE_CODE.put("yuangong", "员工");
        ROLE_CODE.put("grade", "级组长");
        ROLE_CODE.put("leader", "学校领导");
        ROLE_CODE.put("office", "办公后勤");
        ROLE_CODE.put("depmanager", "部门主管");
        ROLE_CODE.put("school", "行政领导");
    }

    /**
     * 通过key获取对应的考勤名字
     *
     * @param key
     * @return
     */
    public static String getAttendName(int key) {
        String result = null;
        boolean containsKey = ATTEND_NAME.containsKey(key);
        if (containsKey) {
            result = ATTEND_NAME.get(key);
        }
        return result;
    }

    /**
     * 通过key获取对应的早午晚名字
     *
     * @param key
     * @return
     */
    public static String getPart(int key) {
        String result = null;
        boolean containsKey = PART.containsKey(key);
        if (containsKey) {
            result = PART.get(key);
        }
        return result;
    }

    /**
     * 通过key获取对应的角色名字
     *
     * @param key
     * @return
     */
    public static String getRoleCode(String key) {
        String result = null;
        boolean containsKey = ROLE_CODE.containsKey(key);
        if (containsKey) {
            result = ROLE_CODE.get(key);
        }
        return result;
    }


}
