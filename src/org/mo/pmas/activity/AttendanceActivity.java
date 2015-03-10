package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.JsonObject;
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
import org.mo.pmas.activity.fragment.ScoreSearchFragment;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.entity.UserDetail;

import java.util.zip.Inflater;

/**
 * Created by moziqi on 2015/3/7 0007.
 */
public class AttendanceActivity extends BaseFramgmentActivity implements XListView.IXListViewListener {

    private XListView list_attendance;
    private EditText et_attendance_search_date_up;
    private EditText et_attendance_search_date_down;

    private AsyncHttpClient instance;
    private SharedPreferences preferences;
    private String username;

    private int page = 1;//当前页
    private int rows = 10;//每页多少条记录
    private String studentId;//学生id
    private String departId;//用户所在部门id
    private String recordid;//考勤汇总id

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        list_attendance = (XListView) findViewById(R.id.list_attendance);
        et_attendance_search_date_up = (EditText) findViewById(R.id.et_attendance_search_date_up);
        et_attendance_search_date_down = (EditText) findViewById(R.id.et_attendance_search_date_down);
        list_attendance.setPullLoadEnable(true);
        list_attendance.setPullRefreshEnable(true);
        list_attendance.setXListViewListener(this);//给xListView设置监听  ******

        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        instance = HttpURLTools.getInstance();
        username = preferences.getString(ConfigContract.USERNAME, null);
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

        if (username != null) {
            try {
                String encrypt3DES = EncryptUtils.Encrypt3DES(username, ConfigContract.CODE);
                String url = ConfigContract.SERVICE_SCHOOL + "loginController.do?getUserInfo";
                RequestParams params = new RequestParams();
                params.put("loginname", encrypt3DES);
                instance.post(url, params, new TextHttpResponseHandler() {
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
                if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
                    ShowToast("请输入查询时间（2014-12-15）");
                    return true;
                }
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_COUNT_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.STUDENT_ID, studentId);
                params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
                params.put(ConfigContract.BEGIN_DATE, et_attendance_search_date_up.getText().toString());
                params.put(ConfigContract.END_DATE, et_attendance_search_date_down.getText().toString());
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
                params.put(ConfigContract.createdate, et_attendance_search_date_up.getText().toString());
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
                params.put(ConfigContract.createdate, et_attendance_search_date_up.getText().toString());
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
                params.put(ConfigContract.countDate_begin, et_attendance_search_date_up.getText().toString());
                params.put(ConfigContract.countDate_end, et_attendance_search_date_down.getText().toString());
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


    //或的数据后一定要加onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        list_attendance.stopRefresh();
        list_attendance.stopLoadMore();
        list_attendance.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        onLoad();

    }

    @Override
    public void onLoadMore() {
        onLoad();
    }
}