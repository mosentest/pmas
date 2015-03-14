package org.mo.znyunxt.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.adapter.AttendRecordAdapter;
import org.mo.znyunxt.entity.AttendRecord;
import org.mo.znyunxt.entity.TbIo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendRecordActivity extends BaseFramgmentActivity implements XListView.IXListViewListener, View.OnClickListener {

    private static String[] mTime = {"--请选择--", "早上", "中午", "晚上"};

    private Spinner sp_time;
    private EditText et_attend_record_date_up;
    private XListView list_attend_record;
    private AttendRecordAdapter attendRecordAdapter;

    private DatePickerDialog.OnDateSetListener changerListener;
    private DatePickerDialog datePickerDialog;

    private AsyncHttpClient instance;

    private static int page = 1;//当前页
    private static int rows = 10;//每页多少条记录
    private String departId;//部门id
    private String recordid;//汇总id
    private List<AttendRecord> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_record);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        sp_time = (Spinner) findViewById(R.id.sp_time);
        et_attend_record_date_up = (EditText) findViewById(R.id.et_attend_record_date_up);
        list_attend_record = (XListView) findViewById(R.id.list_attend_record);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mTime);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_time.setAdapter(arrayAdapter);

        //TODO 这里要修改
        et_attend_record_date_up.setOnClickListener(this);
        et_attend_record_date_up.setFocusable(false);

        list_attend_record.setPullLoadEnable(true);
        list_attend_record.setPullRefreshEnable(true);
        list_attend_record.setXListViewListener(this);//给xListView设置监听  ******

        Intent intent = getIntent();
        departId = intent.getStringExtra("departId");
        instance = HttpURLTools.getInstance();

        list_attend_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttendRecord attendRecord = list.get(position - 1);
                ShowToast(attendRecord.toString());
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
        getMenuInflater().inflate(R.menu.activity_attend_record_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.attend_record_actions:
                searchAttendRecord();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchAttendRecord() {
        page = 1;//当前页
        rows = 10;//每页多少条记录
        RequestParams params = null;
        String url = null;
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_URL;
        params = new RequestParams();
        params.put(ConfigContract.userDid, departId);
        params.put(ConfigContract.createdate, et_attend_record_date_up.getText().toString());
        params.put(ConfigContract.filed, "id,createdate,part,cq,cd,qj,qq,dj,ts,depart.departname");
        if (sp_time.getSelectedItemPosition() != 0) {
            params.put(ConfigContract.cpart, sp_time.getSelectedItemPosition());
        } else {
            params.put(ConfigContract.cpart, "");
        }
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
//                Log.e(ConfigContract.CMD, "考勤汇总" + code + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray rows1 = jsonObject.getJSONArray("rows");
                    list = new ArrayList<AttendRecord>();
                    for (int i = 0; i < rows1.length(); i++) {
                        String jsonObject1 = rows1.getString(i);
//                        showErrorIms(jsonObject1);
                        AttendRecord studentAttendance = new AttendRecord(jsonObject1);
//                        showErrorIms(studentAttendance.toString());
                        list.add(studentAttendance);
                    }
                    attendRecordAdapter = new AttendRecordAdapter(list, AttendRecordActivity.this);
                    list_attend_record.setAdapter(attendRecordAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void searchAttendRecord2() {
        RequestParams params = null;
        String url = null;
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_URL;
        params = new RequestParams();
        params.put(ConfigContract.userDid, departId);
        params.put(ConfigContract.createdate, et_attend_record_date_up.getText().toString());
        params.put(ConfigContract.filed, "id,createdate,part,cq,cd,qj,qq,dj,ts,depart.departname");
        if (sp_time.getSelectedItemPosition() != 0) {
            params.put(ConfigContract.cpart, sp_time.getSelectedItemPosition());
        } else {
            params.put(ConfigContract.cpart, "");
        }
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
//                Log.e(ConfigContract.CMD, "考勤汇总" + code + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray rows1 = jsonObject.getJSONArray("rows");
                    list = new ArrayList<AttendRecord>();
                    rows += 10;
                    for (int i = 0; i < rows1.length(); i++) {
                        String jsonObject1 = rows1.getString(i);
//                        showErrorIms(jsonObject1);
                        AttendRecord studentAttendance = new AttendRecord(jsonObject1);
//                        showErrorIms(studentAttendance.toString());
                        list.add(studentAttendance);
                    }
//                    attendRecordAdapter = new AttendRecordAdapter(list, AttendRecordActivity.this);
//                    list_attend_record.setAdapter(attendRecordAdapter);
                    attendRecordAdapter.update(list);
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
                if (id == R.id.et_attend_record_date_up) {
                    et_attend_record_date_up.setText(selectDateString);
                }
            }
        };
        datePickerDialog = new DatePickerDialog(this, changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onRefresh() {
        onLoad();
        searchAttendRecord();
    }

    @Override
    public void onLoadMore() {
        onLoad();
        searchAttendRecord2();
    }


    //或的数据后一定要加onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        list_attend_record.stopRefresh();
        list_attend_record.stopLoadMore();
        list_attend_record.setRefreshTime("刚刚");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO 这里要修改
            case R.id.et_attend_record_date_up:
                createDetePickerDialog(R.id.et_attend_record_date_up);
                break;

        }
    }
}