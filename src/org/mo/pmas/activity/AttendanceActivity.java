package org.mo.pmas.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.EncryptUtils;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.entity.StudentAttendance;
import org.mo.znyunxt.entity.TbIo;
import org.mo.znyunxt.entity.UserDetail;

import java.util.ArrayList;

/**
 * Created by moziqi on 2015/3/7 0007.
 */
public class AttendanceActivity extends BaseFramgmentActivity implements View.OnClickListener {


    private AsyncHttpClient instance;
    private SharedPreferences preferences;
    private String username;

    private int page = 1;//当前页
    private int rows = 10;//每页多少条记录
    private String studentId = null;//学生id
    private String departId;//用户所在部门id
    private String recordid;//考勤汇总id
    private String rolename;//角色名字

    private RelativeLayout layout_AttendRecord;
    private RelativeLayout layout_AttendCount;
    private RelativeLayout layout_AttendForm;
    private RelativeLayout layout_StudentAttendance;
    private View v1;
    private View v2;
    private View v3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        layout_AttendRecord = (RelativeLayout) findViewById(R.id.layout_AttendRecord);
        layout_AttendCount = (RelativeLayout) findViewById(R.id.layout_AttendCount);
        layout_AttendForm = (RelativeLayout) findViewById(R.id.layout_AttendForm);
        layout_StudentAttendance = (RelativeLayout) findViewById(R.id.layout_StudentAttendance);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);

        layout_AttendRecord.setOnClickListener(this);
        layout_AttendCount.setOnClickListener(this);
        layout_AttendForm.setOnClickListener(this);
        layout_StudentAttendance.setOnClickListener(this);

        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        instance = HttpURLTools.getInstance();
        username = preferences.getString(ConfigContract.USERNAME, null);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_AttendRecord:

                break;
            case R.id.layout_AttendCount:
                break;
            case R.id.layout_AttendForm:
                break;
            case R.id.layout_StudentAttendance:
                intent = new Intent(AttendanceActivity.this, AttendanceOneActivity.class);
                //把学生的id传过去
                intent.putExtra("studentId", studentId);
                intent.putExtra("rolename", rolename);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {
        String url = null;
        RequestParams params = null;
        if (username != null) {
            try {
                String encrypt3DES = EncryptUtils.Encrypt3DES(username, ConfigContract.CODE);
                url = ConfigContract.SERVICE_SCHOOL + "loginController.do?getUserInfo";
                params = new RequestParams();
                params.put("loginname", encrypt3DES);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        layout_AttendRecord.setVisibility(View.GONE);
                        layout_AttendCount.setVisibility(View.GONE);
                        layout_AttendForm.setVisibility(View.GONE);
                        layout_StudentAttendance.setVisibility(View.GONE);
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        showErrorIms(i + "--" + s);
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, s);
                        if (i == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String attributes = jsonObject.getString("attributes");
                                UserDetail userDetail = new UserDetail(attributes);
                                Log.e(ConfigContract.CMD, userDetail.toString());
                                if (userDetail != null) {
                                    studentId = userDetail.getId();
                                    departId = userDetail.getDepartid();
                                    rolename = userDetail.getRolename();
                                    if ("学生".equals(rolename)) {
                                        layout_AttendRecord.setVisibility(View.GONE);
                                        layout_AttendCount.setVisibility(View.GONE);
                                        layout_AttendForm.setVisibility(View.GONE);
                                        layout_StudentAttendance.setVisibility(View.VISIBLE);
                                        v1.setVisibility(View.GONE);
                                        v2.setVisibility(View.GONE);
                                        v3.setVisibility(View.GONE);
                                    } else {
                                        layout_AttendRecord.setVisibility(View.VISIBLE);
                                        layout_AttendCount.setVisibility(View.VISIBLE);
                                        layout_AttendForm.setVisibility(View.VISIBLE);
                                        layout_StudentAttendance.setVisibility(View.GONE);
                                        v1.setVisibility(View.VISIBLE);
                                        v2.setVisibility(View.VISIBLE);
                                        v3.setVisibility(View.VISIBLE);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(ConfigContract.CMD, e.getMessage());
                            }
                        } else {
                            showErrorIms(ConfigContract.GET_USER_INFO_ERROR);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ShowToast(ConfigContract.GET_USER_INFO_ERROR);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_attendance_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RequestParams params = null;
        String url = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.attendance_search_actions:
                //学生考勤查询
//                if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
//                    ShowToast("请输入查询时间 > 2014-12-15");
//                    return true;
//                }
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_COUNT_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.STUDENT_ID, studentId);
                params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
//                params.put(ConfigContract.BEGIN_DATE, et_attendance_search_date_up.getText().toString());
//                params.put(ConfigContract.END_DATE, et_attendance_search_date_down.getText().toString());
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                showErrorIms(params.toString());
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "学生考勤" + i + s);
                    }
                });
                return true;
            case R.id.attendance_depart_actions:
                //子部门
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.DEPART_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.departid, departId);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "子部门" + i + s);
                    }
                });
                return true;
            case R.id.attendance_Io_actions:
                //进出类型
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "进出类型" + i + s);
                    }
                });
                return true;
            case R.id.attendance_AttendRecord_actions:
                //考勤汇总
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.userDid, departId);
//                params.put(ConfigContract.createdate, et_attendance_search_date_up.getText().toString());
                params.put(ConfigContract.filed, "id,createdate,part,cq,cd,qj,qq,dj,ts,depart.departname");
                params.put(ConfigContract.cpart, 1);
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int code, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "考勤汇总" + code + s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray rows1 = jsonObject.getJSONArray("rows");
                            for (int i = 0; i < rows1.length(); i++) {
                                JSONObject jsonObject1 = rows1.getJSONObject(i);
                                recordid = jsonObject1.getString("id");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            case R.id.attendance_AttendCount_actions:
                //考勤汇总详细
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_BY_RECORD_ID_URL;
                params = new RequestParams();
                params.put(ConfigContract.recordid, recordid);
                params.put(ConfigContract.filed, "id,student.name,depart.departname,depart.id,part,attendName,realAttend,createTime,lastOccurtime,lastIo");
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int code, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "考勤汇总详细" + code + s);
                    }
                });
                return true;
            case R.id.attendance_AttendCountController_actions:
                //考勤报表
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.attendName, 0);//实际考勤代号
                params.put(ConfigContract.userDid, departId);
                params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
                params.put(ConfigContract.departid, departId);
//                params.put(ConfigContract.createdate, et_attendance_search_date_up.getText().toString());
                params.put(ConfigContract.cpart, 1);
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "考勤报表" + i + s);
                    }
                });
                return true;
            case R.id.attendance_viewAttendCountController_actions:
                //考勤统计
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.VIEW_ATTEND_COUNT_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.userDid, departId);
                params.put(ConfigContract.departid, departId);
                params.put(ConfigContract.filed, "departname,departid,countDate,countDate_begin,countDate_end,studentid,stuname,cq,cd,qj,qq");
//                params.put(ConfigContract.countDate_begin, et_attendance_search_date_up.getText().toString());
//                params.put(ConfigContract.countDate_end, et_attendance_search_date_down.getText().toString());
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "考勤统计" + i + s);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}