package org.mo.znyunxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.R;
import org.mo.znyunxt.entity.JsonToObjectUtil;
import org.mo.znyunxt.entity.Semester;

import java.util.ArrayList;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class ScoreOneActivity extends BaseFramgmentActivity {


    private AsyncHttpClient instance;

    private int page = 1;//当前页
    private int rows = 10;//每页多少条记录

    private String url = null;
    private RequestParams params = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_one);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        instance = HttpURLTools.getInstance();

        Intent intent = getIntent();
        String semesterid = intent.getStringExtra("semesterid");
        String examID = intent.getStringExtra("exam.id");
        String departId = intent.getStringExtra("departId");


        url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_CJ_CONTROLLER_URL;
        params = new RequestParams();
        params.put(ConfigContract.filed, "id,semesterid,exam.name,exam.id,wenli,depart.departname,depart.id,studentName,yuwen,shuxue,yingyu,kouyu,zhenzhi,lishi,dili,wuli,huaxu,shengwu,zonghe,total,ranking");
        params.put(ConfigContract.userDid, departId);
        params.put(ConfigContract.departid, departId);
        params.put(ConfigContract.SEMESTER_ID, semesterid);
        params.put(ConfigContract.EXAM_ID, examID);
        params.put(ConfigContract.studentName, "");
        params.put(ConfigContract.PAGE, page);
        params.put(ConfigContract.ROWS, rows);
        instance.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                showErrorIms(s);
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
        getMenuInflater().inflate(R.menu.activity_score_one_actions, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

}