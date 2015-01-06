package org.mo.pmas.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.mo.common.activity.BaseFramgmentActivity;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public class NoteAddActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private EditText m_et_note_add_title;
    private EditText m_et_note_add_group;
    private EditText m_et_note_add_content;
    private Button m_btn_note_add_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    void init() {
        m_et_note_add_title = (EditText) findViewById(R.id.et_note_add_title);
        m_et_note_add_group = (EditText) findViewById(R.id.et_note_add_group);
        m_et_note_add_content = (EditText) findViewById(R.id.et_note_add_content);
        m_btn_note_add_save = (Button) findViewById(R.id.btn_note_add_save);
        m_et_note_add_group.setOnClickListener(this);
        m_btn_note_add_save.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_note_add_group:
                noteAddGroupClickListener();
                break;
            case R.id.btn_note_add_save:
                noteAddSaveClickListener();
                break;
        }
    }

    public void noteAddGroupClickListener() {
        ShowToast("OK");
    }

    public void noteAddSaveClickListener() {
        ShowToast("OK2");
    }
}