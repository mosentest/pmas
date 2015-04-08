package org.mo.znyunxt.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

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
import org.mo.pmas.activity.adapter.AttendanceOneAdapter;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.entity.StudentAttendance;
import org.mo.znyunxt.entity.TbIo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by moziqi on 2015/3/10 0010.
 */
public class AttendanceOneActivity extends BaseFramgmentActivity implements XListView.IXListViewListener, View.OnClickListener {

    private DatePickerDialog.OnDateSetListener changerListener;
    private DatePickerDialog datePickerDialog;

    private XListView list_attendance;
    private EditText et_attendance_search_date_up;
    private EditText et_attendance_search_date_down;

    private AsyncHttpClient instance;

    private static int page = 1;//当前页
    private int rows = 10;//每页多少条记录
    private String total;
    private String studentId;//学生id
    private AttendanceOneAdapter attendanceOneAdapter;

    private List<StudentAttendance> studentAttendanceList;
    private List<TbIo> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_one);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        list_attendance = (XListView) findViewById(R.id.list_attendance);
        et_attendance_search_date_up = (EditText) findViewById(R.id.et_attendance_search_date_up);
        et_attendance_search_date_down = (EditText) findViewById(R.id.et_attendance_search_date_down);

        list_attendance.setPullLoadEnable(true);
        list_attendance.setPullRefreshEnable(true);
        list_attendance.setXListViewListener(this);//给xListView设置监听  ******

        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
        instance = HttpURLTools.getInstance();

        //TODO 这里要修改
        et_attendance_search_date_up.setOnClickListener(this);
        et_attendance_search_date_down.setOnClickListener(this);
        et_attendance_search_date_up.setFocusable(false);
        et_attendance_search_date_down.setFocusable(false);
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO 这里要修改
            case R.id.et_attendance_search_date_up:
                createDetePickerDialog(R.id.et_attendance_search_date_up);
                break;
            case R.id.et_attendance_search_date_down:
                createDetePickerDialog(R.id.et_attendance_search_date_down);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_attendance_one_actions, menu);
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
            case R.id.attendance_one_actions:
                page = 1;
                //学生考勤查询
                if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
                    ShowToast("请输入查询时间 > 2014-12-15");
                    return true;
                }
                if (et_attendance_search_date_up.getText().toString().compareTo(et_attendance_search_date_down.getText().toString()) > 0) {
                    ShowToast("输入的时间有误，请重新输入");
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
//                showErrorIms(params.toString());
                instance.post(url, params, new TextHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        super.onStart();
//                        ShowToast("正在加载...");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        String url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                        RequestParams params = new RequestParams();
                        params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                        params.put(ConfigContract.PAGE, 1);
                        params.put(ConfigContract.ROWS, 1000);
                        instance.post(url, params, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                ShowToast("1.检测网络 2.请重新登录");
                            }

                            @Override
                            public void onSuccess(int i, Header[] headers, String s) {
//                                Log.e(ConfigContract.CMD, "进出类型" + i + s);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(s);
                                    String rows1 = jsonObject.getString("rows");
                                    JSONArray jsonArray = new JSONArray(rows1);
                                    list = new ArrayList<TbIo>();
                                    for (int code = 0; code < jsonArray.length(); code++) {
                                        String string = jsonArray.getString(code);
                                        TbIo studentAttendance = new TbIo(string);
//                                        showErrorIms(studentAttendance.toString());
                                        list.add(studentAttendance);

                                    }
                                    Iterator<StudentAttendance> iterator = studentAttendanceList.iterator();
                                    while (iterator.hasNext()) {
                                        StudentAttendance next = iterator.next();
                                        String lastIo = next.getLastIo();
                                        for (int i2 = 0; i2 < list.size(); i2++) {
                                            TbIo tbIo = list.get(i2);
                                            String io = tbIo.getIo();
                                            if (lastIo.equals(io)) {
                                                next.setLastIo(tbIo.getIoname());
                                            }
                                        }
                                    }

                                    attendanceOneAdapter = new AttendanceOneAdapter(AttendanceOneActivity.this, studentAttendanceList);
                                    list_attendance.setAdapter(attendanceOneAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        ShowToast("1.检测网络 2.请重新登录");
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
//                        Log.e(ConfigContract.CMD, "学生考勤" + i + s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String rows1 = jsonObject.getString("rows");
                            JSONArray jsonArray = new JSONArray(rows1);
                            studentAttendanceList = new ArrayList<StudentAttendance>();
                            for (int code = 0; code < jsonArray.length(); code++) {
                                String string = jsonArray.getString(code);
                                StudentAttendance studentAttendance = new StudentAttendance(string);
//                                showErrorIms(studentAttendance.toString());
                                studentAttendanceList.add(studentAttendance);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        page = 1;
        //学生考勤查询
        if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
            ShowToast("请输入查询时间 > 2014-12-15");
            return;
        }
        if (et_attendance_search_date_up.getText().toString().compareTo(et_attendance_search_date_down.getText().toString()) > 0) {
            ShowToast("输入的时间有误，请重新输入");
            return;
        }
        String url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_COUNT_CONTROLLER_URL;
        RequestParams params = new RequestParams();
        params.put(ConfigContract.STUDENT_ID, studentId);
        params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
        params.put(ConfigContract.BEGIN_DATE, et_attendance_search_date_up.getText().toString());
        params.put(ConfigContract.END_DATE, et_attendance_search_date_down.getText().toString());
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
//        showErrorIms(params.toString());
        instance.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
//                ShowToast("正在加载...");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                String url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                RequestParams params = new RequestParams();
                params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                params.put(ConfigContract.PAGE, 1);
                params.put(ConfigContract.ROWS, 1000);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        ShowToast("1.检测网络 2.请重新登录");
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
//                        Log.e(ConfigContract.CMD, "进出类型" + i + s);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            String rows1 = jsonObject.getString("rows");
                            JSONArray jsonArray = new JSONArray(rows1);
                            list = new ArrayList<TbIo>();
                            for (int code = 0; code < jsonArray.length(); code++) {
                                String string = jsonArray.getString(code);
                                TbIo studentAttendance = new TbIo(string);
//                                showErrorIms(studentAttendance.toString());
                                list.add(studentAttendance);

                            }
                            Iterator<StudentAttendance> iterator = studentAttendanceList.iterator();
                            while (iterator.hasNext()) {
                                StudentAttendance next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                            attendanceOneAdapter.update(studentAttendanceList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.e(ConfigContract.CMD, "学生考勤" + i + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rows1 = jsonObject.getString("rows");
                    JSONArray jsonArray = new JSONArray(rows1);
                    studentAttendanceList = new ArrayList<StudentAttendance>();
                    for (int code = 0; code < jsonArray.length(); code++) {
                        String string = jsonArray.getString(code);
                        StudentAttendance studentAttendance = new StudentAttendance(string);
//                        showErrorIms(studentAttendance.toString());
                        studentAttendanceList.add(studentAttendance);
                    }
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
        if (et_attendance_search_date_up.getText().toString().equals("") || et_attendance_search_date_down.getText().toString().equals("")) {
            ShowToast("请输入查询时间 > 2014-12-15");
            return;
        }
        if (et_attendance_search_date_up.getText().toString().compareTo(et_attendance_search_date_down.getText().toString()) > 0) {
            ShowToast("输入的时间有误，请重新输入");
            return;
        }
        String url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_COUNT_CONTROLLER_URL;
        RequestParams params = new RequestParams();
        params.put(ConfigContract.STUDENT_ID, studentId);
        params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
        params.put(ConfigContract.BEGIN_DATE, et_attendance_search_date_up.getText().toString());
        params.put(ConfigContract.END_DATE, et_attendance_search_date_down.getText().toString());
        params.put(ConfigContract.PAGE, ++page);
        params.put(ConfigContract.ROWS, rows);
//        showErrorIms(params.toString());
        instance.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
//                ShowToast("正在加载...");
            }


            @Override
            public void onFinish() {
                super.onFinish();
                //TODO 待开发
                String url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                RequestParams params = new RequestParams();
                params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                params.put(ConfigContract.PAGE, 1);
                params.put(ConfigContract.ROWS, 30);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
//                        Log.e(ConfigContract.CMD, "进出类型" + i + s);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            String rows1 = jsonObject.getString("rows");
                            JSONArray jsonArray = new JSONArray(rows1);
                            list = new ArrayList<TbIo>();
                            for (int code = 0; code < jsonArray.length(); code++) {
                                String string = jsonArray.getString(code);
                                TbIo studentAttendance = new TbIo(string);
//                                showErrorIms(studentAttendance.toString());
                                list.add(studentAttendance);

                            }
                            Iterator<StudentAttendance> iterator = studentAttendanceList.iterator();
                            while (iterator.hasNext()) {
                                StudentAttendance next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                            attendanceOneAdapter.update(studentAttendanceList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.e(ConfigContract.CMD, "学生考勤" + i + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rows1 = jsonObject.getString("rows");
                    JSONArray jsonArray = new JSONArray(rows1);
                    total = jsonObject.getString("total");
                    //当查询到最后一页的时候
                    if((page * rows) > Integer.parseInt(total)){
                        ShowToast("已到底部");
                    }else{
                        List<StudentAttendance> appendStudentAttendanceList = new ArrayList<StudentAttendance>();
                        for (int code = 0; code < jsonArray.length(); code++) {
                            String string = jsonArray.getString(code);
                            StudentAttendance studentAttendance = new StudentAttendance(string);
//                        showErrorIms(studentAttendance.toString());
                            appendStudentAttendanceList.add(studentAttendance);
                        }
                        studentAttendanceList.addAll(appendStudentAttendanceList);
                    }
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
                if (id == R.id.et_attendance_search_date_up) {
                    et_attendance_search_date_up.setText(selectDateString);
                } else if (id == R.id.et_attendance_search_date_down) {
                    et_attendance_search_date_down.setText(selectDateString);
                }

            }
        };
        datePickerDialog = new DatePickerDialog(this, changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}