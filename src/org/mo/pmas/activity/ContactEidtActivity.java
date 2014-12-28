package org.mo.pmas.activity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class ContactEidtActivity extends FragmentActivity {
private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init(getIntent());
    }

    void init(Intent intent) {
        viewHolder = new ViewHolder();
        viewHolder.mSpinner_group = (Spinner) findViewById(R.id.spinner_group);
        viewHolder.mEditText_name = (EditText) findViewById(R.id.editText_name);
        viewHolder.mEditText_phoneNumber = (EditText) findViewById(R.id.editText_phoneNumber);
        viewHolder.mEditText_birthday = (EditText) findViewById(R.id.editText_birthday);
        //TODO 显示下拉表
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.jazzy_effects, android.R.layout.select_dialog_item);
        viewHolder.mSpinner_group.setAdapter(adapter);

        //TODO 日期选择
        viewHolder.mEditText_birthday.setInputType(InputType.TYPE_NULL);
        viewHolder.mEditText_birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(ContactEidtActivity.this,5, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            viewHolder.mEditText_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });
        viewHolder.mEditText_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(ContactEidtActivity.this, 5, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        viewHolder.mEditText_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //TODO 显示用户信息
        Bundle bundle = intent.getExtras();
        long id = bundle.getLong("id");
        String name = bundle.getString("name");
        String phoneNumber = bundle.getString("phoneNumber");
        viewHolder.mEditText_name.setText(name);
        viewHolder.mEditText_phoneNumber.setText(phoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:
                ContactEidtActivity.this.finish();
                return true;
            case R.id.save:
                intent.setClass(ContactEidtActivity.this, ScheduleEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }


    final static class ViewHolder {
        Spinner mSpinner_group;
        EditText mEditText_name;
        EditText mEditText_phoneNumber;
        EditText mEditText_birthday;
        EditText mEditText_email;
        EditText mEditText_address;
        Button mButton_detete;
    }
}
