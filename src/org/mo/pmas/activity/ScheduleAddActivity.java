package org.mo.pmas.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ToastUtil;

import java.util.Calendar;

/**
 * Created by moziqi on 2014/12/28 0028.
 */
public class ScheduleAddActivity extends BaseFramgmentActivity {

    private ViewHodler viewHodler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    private void init() {
        viewHodler = new ViewHodler();
        //TODO 弹出显示日期
        viewHodler.mEditText_date = (EditText) findViewById(R.id.et_schedule_add_time);
        viewHodler.mEditText_date.setInputType(InputType.TYPE_NULL);
        viewHodler.mEditText_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(ScheduleAddActivity.this, 5, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            viewHodler.mEditText_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });
        viewHodler.mEditText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(ScheduleAddActivity.this, 5, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        viewHodler.mEditText_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    final static class ViewHodler {
        EditText mEditText_title;
        EditText mEditText_date;
        EditText mEditText_content;
    }

}
