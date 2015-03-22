package org.mo.znyunxt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendFormActivity extends BaseFramgmentActivity implements XListView.IXListViewListener, View.OnClickListener {
    private EditText et_attend_form_time;
    private Spinner sp_attend_time;
    private Spinner sp_depart_name;
    private Spinner sp_attend_name;
    private XListView list_attend_form;

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {

    }
}