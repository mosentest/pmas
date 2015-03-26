package org.mo.znyunxt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.znyunxt.adapter.ScoreOneAdapter;
import org.mo.znyunxt.entity.Depart;
import org.mo.znyunxt.entity.JsonToObjectUtil;
import org.mo.znyunxt.entity.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class ScoreOneActivity extends BaseFramgmentActivity implements XListView.IXListViewListener {


    private AsyncHttpClient instance;

    private int page = 1;//当前页
    private int rows = 10;//每页多少条记录

    private String url = null;
    private RequestParams params = null;
    private List<Score> scoreList;

    private Spinner sp_depart_name;
//    private Spinner sp_wenli;
    private EditText et_student_name;
    private XListView list_score;

    private String semesterid;
    private String examID;
    private String departId;
    private ArrayList<Depart> departList;
    private String departname;

    private ScoreOneAdapter scoreOneAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_one);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        sp_depart_name = (Spinner) findViewById(R.id.sp_depart_name);
//        sp_wenli = (Spinner) findViewById(R.id.sp_wenli);
        et_student_name = (EditText) findViewById(R.id.et_student_name);
        list_score = (XListView) findViewById(R.id.list_score);

//        ArrayAdapter<CharSequence> wenli = ArrayAdapter.createFromResource(this, R.array.wenli, android.R.layout.simple_spinner_dropdown_item);
//        sp_wenli.setAdapter(wenli);

        list_score.setPullLoadEnable(true);
        list_score.setPullRefreshEnable(true);
        list_score.setXListViewListener(this);//给xListView设置监听  ******

        instance = HttpURLTools.getInstance();

        Intent intent = getIntent();
        semesterid = intent.getStringExtra("semesterid");
        examID = intent.getStringExtra("exam.id");
        departId = intent.getStringExtra("departId");
        departname = intent.getStringExtra("departname");

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
                ArrayAdapter arrayAdapter = new ArrayAdapter(ScoreOneActivity.this, android.R.layout.simple_spinner_item, arrays);
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
            case R.id.attend_score_one_actions:
                page = 1;//当前页
                rows = 10;//每页多少条记录
                url = ConfigContract.SERVICE_SCHOOL + ConfigContract.TB_CJ_CONTROLLER_URL;
                params = new RequestParams();
                params.put(ConfigContract.filed, "id,semesterid,exam.name,exam.id,wenli,depart.departname,depart.id,studentName,yuwen,shuxue,yingyu,kouyu,zhenzhi,lishi,dili,wuli,huaxu,shengwu,zonghe,total,ranking");
                params.put(ConfigContract.userDid, departId);
                params.put(ConfigContract.departid, departList.get(sp_depart_name.getSelectedItemPosition()).getId());
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
                        try {
                            scoreList = new ArrayList<Score>();
                            JSONArray jsonArray = JsonToObjectUtil.getJSONArray(s);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                Score score = new Score(jsonArray.getString(j));
                                scoreList.add(score);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showErrorIms(s);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        scoreOneAdapter = new ScoreOneAdapter(ScoreOneActivity.this, scoreList);
                        list_score.setAdapter(scoreOneAdapter);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}