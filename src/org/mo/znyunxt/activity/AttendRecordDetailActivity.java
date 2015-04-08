package org.mo.znyunxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
import org.mo.pmas.activity.adapter.AttendanceOneAdapter;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.adapter.AttendRecordAdapter;
import org.mo.znyunxt.adapter.AttendRecordDetailAdapter;
import org.mo.znyunxt.entity.AttendRecord;
import org.mo.znyunxt.entity.AttendRecordDetail;
import org.mo.znyunxt.entity.JsonToObjectUtil;
import org.mo.znyunxt.entity.StudentAttendance;
import org.mo.znyunxt.entity.TbIo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendRecordDetailActivity extends BaseFramgmentActivity implements XListView.IXListViewListener {

    private AsyncHttpClient instance;
    private RequestParams params = null;
    private String url = null;

    private List<AttendRecordDetail> lists;
    private ArrayList<TbIo> list;
    private AttendRecordDetailAdapter attendRecordDetailAdapter;

    private XListView list_attend_record_detail;
    private TextView tv_depart_name;
    private TextView tv_depart_time;

    private static int page = 1;//当前页
    private int rows = 10;//每页多少条记录
    private String recordid;
    private String total;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_record_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        list_attend_record_detail = (XListView) findViewById(R.id.list_attend_record_detail);
        tv_depart_name = (TextView) findViewById(R.id.tv_depart_name);
        tv_depart_time = (TextView) findViewById(R.id.tv_depart_time);

        list_attend_record_detail.setPullLoadEnable(true);
        list_attend_record_detail.setPullRefreshEnable(true);
        list_attend_record_detail.setXListViewListener(this);//给xListView设置监听  ******

        instance = HttpURLTools.getInstance();
        Intent intent = getIntent();
        recordid = intent.getStringExtra("recordid");

        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_BY_RECORD_ID_URL;
        params = new RequestParams();
        params.put(ConfigContract.recordid, recordid);
        params.put(ConfigContract.filed, "id,student.name,depart.departname,depart.id,part,attendName,realAttend,createTime,lastOccurtime,lastIo");
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
                try {
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    lists = new ArrayList<AttendRecordDetail>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getString(i);
                        AttendRecordDetail attendRecordDetail = new AttendRecordDetail(string);
                        lists.add(attendRecordDetail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                params = new RequestParams();
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
                            Iterator<AttendRecordDetail> iterator = lists.iterator();
                            while (iterator.hasNext()) {
                                AttendRecordDetail next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                            if (lists.size() > 1) {
                                AttendRecordDetail attendRecordDetail = lists.get(0);
                                Date date = DateUtil.str2Date(attendRecordDetail.getCreateTime());
                                String dateString = DateUtil.date2Str(date, "yyyy-MM-dd");
                                tv_depart_time.setText(dateString);
                                tv_depart_name.setText(attendRecordDetail.getDepart_departname());
                                attendRecordDetailAdapter = new AttendRecordDetailAdapter(AttendRecordDetailActivity.this, lists);
                                list_attend_record_detail.setAdapter(attendRecordDetailAdapter);
                            } else {
                                tv_depart_time.setText("没有考勤信息");
                                ShowToast("没有考勤信息");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
        getMenuInflater().inflate(R.menu.activity_attend_record_detail_actions, menu);
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //或的数据后一定要加onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        list_attend_record_detail.stopRefresh();
        list_attend_record_detail.stopLoadMore();
        list_attend_record_detail.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        onLoad();
        page = 1;
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_BY_RECORD_ID_URL;
        params = new RequestParams();
        params.put(ConfigContract.recordid, recordid);
        params.put(ConfigContract.filed, "id,student.name,depart.departname,depart.id,part,attendName,realAttend,createTime,lastOccurtime,lastIo");
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ShowToast("1.检测网络 2.请重新登录");
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
                try {
                    JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                    lists = new ArrayList<AttendRecordDetail>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getString(i);
                        AttendRecordDetail attendRecordDetail = new AttendRecordDetail(string);
                        lists.add(attendRecordDetail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                params.put(ConfigContract.PAGE, 1);
                params.put(ConfigContract.ROWS, 1000);
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
                            Iterator<AttendRecordDetail> iterator = lists.iterator();
                            while (iterator.hasNext()) {
                                AttendRecordDetail next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                            if (lists.size() > 1) {
                                AttendRecordDetail attendRecordDetail = lists.get(0);
                                Date date = DateUtil.str2Date(attendRecordDetail.getCreateTime());
                                String dateString = DateUtil.date2Str(date, "yyyy-MM-dd");
                                tv_depart_time.setText(dateString);
                                tv_depart_name.setText(attendRecordDetail.getDepart_departname());
                                attendRecordDetailAdapter.update(lists);
                            } else {
                                tv_depart_time.setText("没有考勤信息");
                                ShowToast("没有考勤信息");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onLoadMore() {
        onLoad();
        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_ATTEND_RECORD_CONTROLLER_DATA_GRID_BY_RECORD_ID_URL;
        params = new RequestParams();
        params.put(ConfigContract.recordid, recordid);
        params.put(ConfigContract.filed, "id,student.name,depart.departname,depart.id,part,attendName,realAttend,createTime,lastOccurtime,lastIo");
        params.put(ConfigContract.PAGE, ++page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    total = jsonObject.getString("total");
                    //当查询到最后一页的时候
                    if((page * rows) > Integer.parseInt(total)){
                        ShowToast("已到底部");
                    }else{
                        JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                        List<AttendRecordDetail> appendLists = new ArrayList<AttendRecordDetail>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String string = jsonArray.getString(i);
                            AttendRecordDetail attendRecordDetail = new AttendRecordDetail(string);
                            lists.add(attendRecordDetail);
                        }
                        lists.addAll(appendLists);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_IO_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "id,io,gate,ioType,ioSortName,gateType,inschool,ioname");
                params.put(ConfigContract.PAGE, 1);
                params.put(ConfigContract.ROWS, 1000);
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
                            Iterator<AttendRecordDetail> iterator = lists.iterator();
                            while (iterator.hasNext()) {
                                AttendRecordDetail next = iterator.next();
                                String lastIo = next.getLastIo();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    TbIo tbIo = list.get(i2);
                                    String io = tbIo.getIo();
                                    if (lastIo.equals(io)) {
                                        next.setLastIo(tbIo.getIoname());
                                    }
                                }
                            }
                            if (lists.size() > 1) {
                                AttendRecordDetail attendRecordDetail = lists.get(0);
                                Date date = DateUtil.str2Date(attendRecordDetail.getCreateTime());
                                String dateString = DateUtil.date2Str(date, "yyyy-MM-dd");
                                tv_depart_time.setText(dateString);
                                tv_depart_name.setText(attendRecordDetail.getDepart_departname());
                                attendRecordDetailAdapter.update(lists);
                            } else {
                                tv_depart_time.setText("没有考勤信息");
                                ShowToast("没有考勤信息");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}