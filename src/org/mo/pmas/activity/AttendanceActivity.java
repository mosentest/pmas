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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        instance = HttpURLTools.getInstance();
    }

    @Override
    protected void toInitUI() {
        list_attendance = (XListView) findViewById(R.id.list_attendance);
        et_attendance_search_date_up = (EditText) findViewById(R.id.et_attendance_search_date_up);
        et_attendance_search_date_down = (EditText) findViewById(R.id.et_attendance_search_date_down);
        list_attendance.setPullLoadEnable(true);
        list_attendance.setPullRefreshEnable(true);
        list_attendance.setXListViewListener(this);//给xListView设置监听  ******
    }

    @Override
    protected void toUIOper() {
        username = preferences.getString(ConfigContract.USERNAME, null);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.attendance_search_actions:
                if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
                    ShowToast("请输入查询时间（2014-12-15）");
                    return true;
                }
                String attend_count = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_COUNT_CONTROLLER_URL;
                RequestParams params = new RequestParams();
                params.put(ConfigContract.STUDENT_ID, studentId);
                params.put(ConfigContract.BEGIN_DATE, et_attendance_search_date_up.getText().toString());
                params.put(ConfigContract.END_DATE, et_attendance_search_date_down.getText().toString());
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                instance.post(attend_count, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Log.e(ConfigContract.CMD, "出勤" + "失败");
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Log.e(ConfigContract.CMD, "出勤" + i + s);
                    }
                });
                return true;
            case R.id.attendance_depart_actions:
                //子部门
                return true;
            case R.id.attendance_Io_actions:
                //进出类型
                return true;
            case R.id.attendance_AttendRecord_actions:
                //考勤汇总
                return true;
            case R.id.attendance_AttendCount_actions:
                //考勤汇总详细
                return true;
            case R.id.attendance_AttendCountController_actions:
                //考勤报表
                return true;
            case R.id.attendance_viewAttendCountController_actions:
                //考勤统计
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