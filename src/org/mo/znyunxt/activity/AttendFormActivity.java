package org.mo.znyunxt.activity;

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
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.adapter.AttendFormAdapter;
import org.mo.znyunxt.entity.AttendForm;
import org.mo.znyunxt.entity.Depart;
import org.mo.znyunxt.entity.JsonToObjectUtil;
import org.mo.znyunxt.entity.TbIo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendFormActivity extends BaseFramgmentActivity implements XListView.IXListViewListener, View.OnClickListener {

    private DatePickerDialog.OnDateSetListener changerListener;
    private DatePickerDialog datePickerDialog;

    private EditText et_attend_form_time;
    private Spinner sp_attend_time;
    private Spinner sp_depart_name;
    private Spinner sp_attend_name;
    private XListView list_attend_form;

    private AsyncHttpClient instance;

    private static int page = 1;//当前页
    private static int rows = 10;//每页多少条记录

    private String total;
    private String departId;
    private String departname;
    private String url;
    private RequestParams params;
    private ArrayList<Depart> departList;
    private List<AttendForm> attendFormList;
    private AttendFormAdapter attendFormAdapter;
    private ArrayList<TbIo> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_form);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        et_attend_form_time = (EditText) findViewById(R.id.et_attend_form_time);
        sp_attend_time = (Spinner) findViewById(R.id.sp_attend_time);
        sp_depart_name = (Spinner) findViewById(R.id.sp_depart_name);
        sp_attend_name = (Spinner) findViewById(R.id.sp_attend_name);
        list_attend_form = (XListView) findViewById(R.id.list_attend_form);

        ArrayAdapter<CharSequence> partType = ArrayAdapter.createFromResource(this, R.array.part_type, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> attendName = ArrayAdapter.createFromResource(this, R.array.attend_name, android.R.layout.simple_spinner_dropdown_item);
        sp_attend_time.setAdapter(partType);
        sp_attend_name.setAdapter(attendName);

        list_attend_form.setPullLoadEnable(true);
        list_attend_form.setPullRefreshEnable(true);
        list_attend_form.setXListViewListener(this);//给xListView设置监听  ******

        et_attend_form_time.setOnClickListener(this);
        et_attend_form_time.setFocusable(false);

        Intent intent = getIntent();
        departId = intent.getStringExtra("departId");
        departname = intent.getStringExtra("departname");
        instance = HttpURLTools.getInstance();

        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.DEPART_CONTROLLER_URL;
        params = new RequestParams();
        params.put("id", departId);
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        //先拿部门
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

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
                ArrayAdapter arrayAdapter = new ArrayAdapter(AttendFormActivity.this, android.R.layout.simple_spinner_item, arrays);
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
        getMenuInflater().inflate(R.menu.activity_attend_form_actions, menu);
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
            case R.id.attend_form_actions:
                page = 1;
                rows = 10;
                //学生考勤查询
                if (et_attend_form_time.getText().toString().equals("")) {
                    ShowToast("日期不能为空");
                    return true;
                }
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
                if (sp_attend_name.getSelectedItemPosition() == 0) {
                    params.put(ConfigContract.attendName, "");
                } else {
                    params.put(ConfigContract.attendName, sp_attend_name.getSelectedItemPosition() - 1);

                }
                params.put(ConfigContract.createTime, et_attend_form_time.getText().toString());
                if (sp_attend_time.getSelectedItemPosition() == 0) {
                    params.put(ConfigContract.cpart, "");
                } else {
                    params.put(ConfigContract.cpart, sp_attend_time.getSelectedItemPosition());
                }
                params.put(ConfigContract.userDid, departId);
                params.put(ConfigContract.departid, departList.get(sp_depart_name.getSelectedItemPosition()).getId());
                params.put(ConfigContract.PAGE, page);
                params.put(ConfigContract.ROWS, rows);

                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        try {
                            attendFormList = new ArrayList<AttendForm>();
                            JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                AttendForm attendForm = new AttendForm(jsonArray.getString(j));
                                attendFormList.add(attendForm);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
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
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(s);
                                    String rows1 = jsonObject.getString("rows");
                                    JSONArray jsonArray = new JSONArray(rows1);
                                    list = new ArrayList<TbIo>();
                                    for (int code = 0; code < jsonArray.length(); code++) {
                                        String string = jsonArray.getString(code);
                                        TbIo studentAttendance = new TbIo(string);
                                        list.add(studentAttendance);

                                    }
                                    Iterator<AttendForm> iterator = attendFormList.iterator();
                                    while (iterator.hasNext()) {
                                        AttendForm next = iterator.next();
                                        String lastIo = next.getLastIo();
                                        for (int i2 = 0; i2 < list.size(); i2++) {
                                            TbIo tbIo = list.get(i2);
                                            String io = tbIo.getIo();
                                            if (lastIo.equals(io)) {
                                                next.setLastIo(tbIo.getIoname());
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                attendFormAdapter = new AttendFormAdapter(AttendFormActivity.this, attendFormList);
                                list_attend_form.setAdapter(attendFormAdapter);
                            }
                        });
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //或的数据后一定要加onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        list_attend_form.stopRefresh();
        list_attend_form.stopLoadMore();
        list_attend_form.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        onLoad();
        page = 1;
        rows = 10;
        //学生考勤查询
        if (et_attend_form_time.getText().toString().equals("")) {
            ShowToast("日期不能为空");
            return;
        }
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_URL;
        params = new RequestParams();
        params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
        if (sp_attend_name.getSelectedItemPosition() == 0) {
            params.put(ConfigContract.attendName, "");
        } else {
            params.put(ConfigContract.attendName, sp_attend_name.getSelectedItemPosition() - 1);

        }
        params.put(ConfigContract.createTime, et_attend_form_time.getText().toString());
        if (sp_attend_time.getSelectedItemPosition() == 0) {
            params.put(ConfigContract.cpart, "");
        } else {
            params.put(ConfigContract.cpart, sp_attend_time.getSelectedItemPosition());
        }
        params.put(ConfigContract.userDid, departId);
        params.put(ConfigContract.departid, departList.get(sp_depart_name.getSelectedItemPosition()).getId());
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);

        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    attendFormList = new ArrayList<AttendForm>();
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        AttendForm attendForm = new AttendForm(jsonArray.getString(j));
                        attendFormList.add(attendForm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
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
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            String rows1 = jsonObject.getString("rows");
                            JSONArray jsonArray = new JSONArray(rows1);
                            list = new ArrayList<TbIo>();
                            for (int code = 0; code < jsonArray.length(); code++) {
                                String string = jsonArray.getString(code);
                                TbIo studentAttendance = new TbIo(string);
                                list.add(studentAttendance);

                            }
                            Iterator<AttendForm> iterator = attendFormList.iterator();
                            while (iterator.hasNext()) {
                                AttendForm next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        attendFormAdapter.update(attendFormList);
                    }
                });
            }
        });
    }

    @Override
    public void onLoadMore() {
        onLoad();
        //学生考勤查询
        if (et_attend_form_time.getText().toString().equals("")) {
            ShowToast("日期不能为空");
            return;
        }
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_URL;
        params = new RequestParams();
        params.put(ConfigContract.filed, "id,createTime,depart.departname,depart.id,student.name,part,realAttend,lastOccurtime,lastIo");
        if (sp_attend_name.getSelectedItemPosition() == 0) {
            params.put(ConfigContract.attendName, "");
        } else {
            params.put(ConfigContract.attendName, sp_attend_name.getSelectedItemPosition() - 1);

        }
        params.put(ConfigContract.createTime, et_attend_form_time.getText().toString());
        if (sp_attend_time.getSelectedItemPosition() == 0) {
            params.put(ConfigContract.cpart, "");
        } else {
            params.put(ConfigContract.cpart, sp_attend_time.getSelectedItemPosition());
        }
        params.put(ConfigContract.userDid, departId);
        params.put(ConfigContract.departid, departList.get(sp_depart_name.getSelectedItemPosition()).getId());
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);

        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    total = jsonObject.getString("total");
                    rows = rows + 10;
                    if (Integer.parseInt(total) < rows) {
                        ShowToast("无数据可以加载啦");
                        rows = Integer.parseInt(total);
                    }

                    attendFormList = new ArrayList<AttendForm>();
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        AttendForm attendForm = new AttendForm(jsonArray.getString(j));
                        attendFormList.add(attendForm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();

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
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            String rows1 = jsonObject.getString("rows");
                            JSONArray jsonArray = new JSONArray(rows1);
                            list = new ArrayList<TbIo>();
                            for (int code = 0; code < jsonArray.length(); code++) {
                                String string = jsonArray.getString(code);
                                TbIo studentAttendance = new TbIo(string);
                                list.add(studentAttendance);

                            }
                            Iterator<AttendForm> iterator = attendFormList.iterator();
                            while (iterator.hasNext()) {
                                AttendForm next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        attendFormAdapter.update(attendFormList);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO 这里要修改
            case R.id.et_attend_form_time:
                createDetePickerDialog(R.id.et_attend_form_time);
                break;
        }
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
                if (id == R.id.et_attend_form_time) {
                    et_attend_form_time.setText(selectDateString);
                }
            }
        };
        datePickerDialog = new DatePickerDialog(this, changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}