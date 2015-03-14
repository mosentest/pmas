package org.mo.pmas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.activity.IBaseActivity;
//import org.mo.pmas.bmob.entity.NoteGroup;

/**
 * Created by moziqi on 2015/1/8 0008.
 */
public class NoteGroupAddActivity extends BaseFramgmentActivity implements View.OnClickListener, IBaseActivity {
    private EditText m_et_note_group_add_name;
    private EditText m_et_note_group_add_description;
    private Button m_btn_note_group_add_save;
//    private NoteGroup noteGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_group_add);
        toInitUI();
        toInitDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_note_group_add_save:
                break;
        }
    }







    @Override
    public void toInitUI() {
        m_et_note_group_add_name = (EditText) findViewById(R.id.et_note_group_add_name);
        m_et_note_group_add_description = (EditText) findViewById(R.id.et_note_group_add_description);
        m_btn_note_group_add_save = (Button) findViewById(R.id.btn_note_group_add_save);
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public void toInitDate() {
        m_btn_note_group_add_save.setOnClickListener(this);
    }
}