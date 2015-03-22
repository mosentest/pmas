package org.mo.common.util;

/**
 * Created by moziqi on 2015/3/6 0006.
 */
public interface ConfigContract {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String LOGIN_NAME = "loginname";

    public static final String CMD = "moziqi";

    public static final String CREATE_DATE = "createDate";


    public final static String CODE = "zn$*&app";

    public final static String GET_USER_INFO_ERROR = "获取用户信息失败，请重新登录";
    public final static String NO_USER = "用户不存在";

    public final static String student_username = "gem";
    public final static String student_password = "654321";

    public final static String teacher_username = "66259";
    public final static String teacher_password = "741862359";

    public final static String SERVICE_SCHOOL = "http://www.znyunxt.cn:9080/school/";

    /**
     * 8.单个学生考勤统计查询
     */
    public final static String TB_ATTEND_COUNT_CONTROLLER_URL = "tbAttendCountController.do?studentAttendCountDatagrid";
    /**
     * 2.获取子部门
     */
    public final static String DEPART_CONTROLLER_URL = "departController.do?setPFunction";

    /**
     * 3.获取进出类型
     */
    public final static String TB_IO_CONTROLLER_URL = "tbIoController.do?datagrid";
    /**
     * 4.考勤汇总
     */
    public final static String TB_ATTEND_RECORD_CONTROLLER_URL = "tbAttendRecordController.do?datagrid";
    /**
     * 5.考勤汇总详情
     */
    public final static String TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_BY_RECORD_ID_URL = "tbAttendCountController.do?datagridByRecordid";
    /**
     * 6.考勤报表
     */
    public final static String TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_URL = "tbAttendCountController.do?datagrid";
    /**
     * 7.考勤统计
     */
    public final static String VIEW_ATTEND_COUNT_CONTROLLER_URL = "viewAttendCountController.do?datagrid";


    /**
     * 9.获取学期列表（1）
     */
    public final static String TB_SEMESTER_CONTROLLER_URL = "tbSemesterController.do?getSemesterComboList";

    /**
     * 10.获取学期列表（2）
     */
    public final static String TB_SEMESTER_CONTROLLER_URL2 = "tbSemesterController.do?datagrid";

    /**
     * 11.获取考试列表（1）
     */
    public final static String TB_EXAM_CONTROLLER_URL1 = "tbExamController.do?datagrid";

    /**
     * 12.获取考试列表（2）
     */
    public final static String TB_EXAM_CONTROLLER_URL2 = "tbExamController.do?getExamComboList";

    /**
     * 13.查询某个学生的成绩
     */
    public final static String TB_CJ_CONTROLLER_URL = "tbCjController.do?datagrid";


    /**
     * 8个人查询
     */
    public final static String STUDENT_ID = "studentid";//学生id
    public final static String BEGIN_DATE = "beginDate";
    public final static String END_DATE = "endDate";

    /**
     * 7.考勤统计
     */
    public final static String userDid = "userDid";//用户所在部门id
    public final static String departid = "depart.id";//要查询的班级id
    public final static String countDate_begin = "countDate_begin";//统计起始日期
    public final static String countDate_end = "countDate_end";//统计结束日期

    /**
     * 6.考勤报表
     */
    public final static String attendName = "attendName";//实际考勤
    public final static String createTime = "createTime";//考勤日期
    public final static String cpart = "part";//考勤时间段（早午晚代号）

    /**
     * 5.考勤汇总详情
     */
    public final static String recordid = "recordid";//考勤汇总id
    /**
     * 4.考勤汇总
     */
    public final static String createdate = "createdate";//考勤日期

    public final static String PAGE = "page";
    public final static String ROWS = "rows";

    public final static String filed = "field";


    public final static String EXAM_ID = "exam.id";

    public final static String SEMESTER_ID = "semester.id";

    public final static String EXAM_TYPE = "examType";

    public final static String studentName = "studentName";
}
