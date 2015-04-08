package org.mo.znyunxt.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.DateUtil;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.adapter.AttendCountAdapter;
import org.mo.znyunxt.entity.AttendCount;
import org.mo.znyunxt.entity.Depart;
import org.mo.znyunxt.entity.JsonToObjectUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendCountActivity extends BaseFramgmentActivity implements XListView.IXListViewListener, View.OnClickListener {

    private DatePickerDialog.OnDateSetListener changerListener;
    private DatePickerDialog datePickerDialog;

    private EditText et_attend_count_date_up;
    private EditText et_attend_count_date_down;
    private Spinner sp_depart_name;
    private XListView list_attend_count;

    private static int page = 1;//当前页
    private int rows = 10;//每页多少条记录

    private AsyncHttpClient instance;
    private String url;
    private RequestParams params;
    private String departId;
    private String departname;
    private List<Depart> departList;
    private List<AttendCount> attendCountList;
    private AttendCountAdapter attendCountAdapter;
    private String total;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_count);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        et_attend_count_date_up = (EditText) findViewById(R.id.et_attend_count_date_up);
        et_attend_count_date_down = (EditText) findViewById(R.id.et_attend_count_date_down);
        sp_depart_name = (Spinner) findViewById(R.id.sp_depart_name);
        list_attend_count = (XListView) findViewById(R.id.list_attend_count);

        list_attend_count.setPullLoadEnable(true);
        list_attend_count.setPullRefreshEnable(true);
        list_attend_count.setXListViewListener(this);//给xListView设置监听  ******

        //TODO 这里要修改
        et_attend_count_date_up.setOnClickListener(this);
        et_attend_count_date_down.setOnClickListener(this);
        et_attend_count_date_up.setFocusable(false);
        et_attend_count_date_down.setFocusable(false);

        Intent intent = getIntent();
        departId = intent.getStringExtra("departId");
        departname = intent.getStringExtra("departname");
        instance = HttpURLTools.getInstance();
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.DEPART_CONTROLLER_URL;
        params = new RequestParams();
        params.put("id", departId);
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, 30);
        //先拿部门
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    departList = new ArrayList<Depart>();
                    Depart newDepart = new Depart();
                    newDepart.setId(departId);
                    newDepart.setText(departname);
                    departList.add(0, newDepart);
                    JSONArray jsonArray = new JSONArray(s);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        String string = jsonArray.getString(j);
                        Depart depart = new Depart(string);
                        departList.add(depart);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                String[] arrays = new String[departList.size()];
                for (int i = 0; i < departList.size(); i++) {
                    arrays[i] = departList.get(i).getText();
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(AttendCountActivity.this, android.R.layout.simple_spinner_item, arrays);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_depart_name.setAdapter(arrayAdapter);

            }
        });

    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_attend_count_actions, menu);
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
            case R.id.attend_count_actions:
                page = 1;
                //学生考勤查询
                if (et_attend_count_date_up.getText().toString().equals("") || et_attend_count_date_down.getText().toString().equals("")) {
                    ShowToast("日期不能为空");
                    return true;
                }
                if ("2014-12-15".compareTo(et_attend_count_date_up.getText().toString()) < 0) {
                    ShowToast("第一个输入框的日期必须小于或等于2014-12-15");
                    return true;
                }
                if (et_attend_count_date_up.getText().toString().compareTo(et_attend_count_date_down.getText().toString()) > 0) {
                    ShowToast("输入的时间有误，请重新输入");
                    return true;
                }
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.VIEW_ATTEND_COUNT_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "departname,departid,countDate,countDate_begin,countDate_end,studentid,stuname,cq,cd,qj,qq");
                params.put(ConfigContract.userDid, departId);
                params.put("departid", departList.get(sp_depart_name.getSelectedItemPosition()).getId());
                params.put(ConfigContract.countDate_begin, et_attend_count_date_up.getText().toString());
                params.put(ConfigContract.countDate_end, et_attend_count_date_down.getText().toString());
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);
                showErrorIms(params.toString());
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        ShowToast("1.检测网络 2.请重新登录");
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        try {
                            JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                            attendCountList = new ArrayList<AttendCount>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                AttendCount attendCount = new AttendCount(jsonArray.getString(j));
                                attendCountList.add(attendCount);
                            }
                            attendCountAdapter = new AttendCountAdapter(AttendCountActivity.this, attendCountList);
                            list_attend_count.setAdapter(attendCountAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO 这里要修改
            case R.id.et_attend_count_date_up:
                createDetePickerDialog(R.id.et_attend_count_date_up);
                break;
            case R.id.et_attend_count_date_down:
                createDetePickerDialog(R.id.et_attend_count_date_down);
                break;
        }
    }

    //或的数据后一定要加onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        list_attend_count.stopRefresh();
        list_attend_count.stopLoadMore();
        list_attend_count.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        onLoad();
        page = 1;
        //学生考勤查询
        if (et_attend_count_date_up.getText().toString().equals("") || et_attend_count_date_down.getText().toString().equals("")) {
            ShowToast("日期不能为空");
            return;
        }
        if ("2014-12-15".compareTo(et_attend_count_date_up.getText().toString()) < 0) {
            ShowToast("第一个输入框的日期必须小于或等于2014-12-15");
            return;
        }
        if (et_attend_count_date_up.getText().toString().compareTo(et_attend_count_date_down.getText().toString()) > 0) {
            ShowToast("输入的时间有误，请重新输入");
            return;
        }
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.VIEW_ATTEND_COUNT_CONTROLLER_URL;
        params = new RequestParams();
        params.put(ConfigContract.filed, "departname,departid,countDate,countDate_begin,countDate_end,studentid,stuname,cq,cd,qj,qq");
        params.put(ConfigContract.userDid, departId);
        params.put("departid", departList.get(sp_depart_name.getSelectedItemPosition()).getId());
        params.put(ConfigContract.countDate_begin, et_attend_count_date_up.getText().toString());
        params.put(ConfigContract.countDate_end, et_attend_count_date_down.getText().toString());
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        showErrorIms(params.toString());
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    attendCountList = new ArrayList<AttendCount>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        AttendCount attendCount = new AttendCount(jsonArray.getString(j));
                        attendCountList.add(attendCount);
                    }
                    attendCountAdapter.update(attendCountList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onLoadMore() {
        onLoad();
        //学生考勤查询
        if (et_attend_count_date_up.getText().toString().equals("") || et_attend_count_date_down.getText().toString().equals("")) {
            ShowToast("日期不能为空");
            return;
        }
        if ("2014-12-15".compareTo(et_attend_count_date_up.getText().toString()) < 0) {
            ShowToast("第一个输入框的日期必须小于或等于2014-12-15");
            return;
        }
        if (et_attend_count_date_up.getText().toString().compareTo(et_attend_count_date_down.getText().toString()) > 0) {
            ShowToast("输入的时间有误，请重新输入");
            return;
        }
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.VIEW_ATTEND_COUNT_CONTROLLER_URL;
        params = new RequestParams();
        params.put(ConfigContract.filed, "departname,departid,countDate,countDate_begin,countDate_end,studentid,stuname,cq,cd,qj,qq");
        params.put(ConfigContract.userDid, departId);
        params.put("departid", departList.get(sp_depart_name.getSelectedItemPosition()).getId());
        params.put(ConfigContract.countDate_begin, et_attend_count_date_up.getText().toString());
        params.put(ConfigContract.countDate_end, et_attend_count_date_down.getText().toString());
        params.put(ConfigContract.PAGE, ++page);
        params.put(ConfigContract.ROWS, rows);
        showErrorIms(params.toString());
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    JSONObject jsonObject = new JSONObject(s);
                    total = jsonObject.getString("total");
                    //当查询到最后一页的时候
                    if((page * rows) > Integer.parseInt(total)){
                        ShowToast("已到底部");
                    }else{
                        List<AttendCount> appendAttendCountList = new ArrayList<AttendCount>();
                        for (int j = 0; j < jsonArray.length(); j++) {
                            AttendCount attendCount = new AttendCount(jsonArray.getString(j));
                            appendAttendCountList.add(attendCount);
                        }
                        attendCountList.addAll(appendAttendCountList);
                    }
                    attendCountAdapter.update(attendCountList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createDetePickerDialog(final int id) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        changerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                System.out.println("year" + year + "monthOfYear" + monthOfYear + "monthOfYear" + dayOfMonth);
                int ccmonth = monthOfYear + 1;
                String monthString = String.valueOf(ccmonth);
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                String dayString = String.valueOf(dayOfMonth);
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                String selectDateString = year + "-" + monthString + "-" + dayString;
                //TODO 这里要修改
                if (id == R.id.et_attend_count_date_up) {
                    et_attend_count_date_up.setText(selectDateString);
                } else if (id == R.id.et_attend_count_date_down) {
                    et_attend_count_date_down.setText(selectDateString);
                }

            }
        };
        datePickerDialog = new DatePickerDialog(this, changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}