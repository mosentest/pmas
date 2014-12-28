package org.mo.pmas.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class ContactEidtActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init(getIntent());
    }

    void init(Intent intent) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mSpinner_group = (Spinner) findViewById(R.id.spinner_group);
        viewHolder.mEditText_name = (EditText) findViewById(R.id.editText_name);
        viewHolder.mEditText_phoneNumber = (EditText) findViewById(R.id.editText_phoneNumber);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.jazzy_effects, android.R.layout.simple_spinner_dropdown_item);
        viewHolder.mSpinner_group.setAdapter(adapter);

        //显示用户信息
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
                intent.setClass(ContactEidtActivity.this, NoteEditActivity.class);
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
