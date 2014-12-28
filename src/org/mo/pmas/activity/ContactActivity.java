package org.mo.pmas.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class ContactActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
    }

    void init() {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.spinner = (Spinner) findViewById(R.id.spinner_group);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.jazzy_effects, android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    final static class ViewHolder {
        Spinner spinner;
    }
}
