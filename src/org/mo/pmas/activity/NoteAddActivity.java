package org.mo.pmas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.entity.Note;
import org.mo.pmas.service.INoteService;
import org.mo.pmas.service.NoteService;
import org.mo.taskmanager.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public class NoteAddActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private final static String TAG = NoteAddActivity.class.getSimpleName();
    private final static String[] condition = new String[]{"学习", "工作", "生活", "其他"};
    private EditText m_et_note_add_title;
    private Spinner mSpinner;
    private EditText m_et_note_add_content;
    private final static int MAX_COUNT = 300;
    private long id;
    private NoteService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
        m_et_note_add_title = (EditText) findViewById(R.id.et_note_add_title);
        mSpinner = (Spinner) findViewById(R.id.sp_note_add_group);
        m_et_note_add_content = (EditText) findViewById(R.id.et_note_add_content);

        ArrayAdapter adpt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, condition);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adpt);

        //监听字数
        m_et_note_add_content.addTextChangedListener(new TextWatcher() {

            private int editStart;
            private int editEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = m_et_note_add_content.getSelectionStart();
                editEnd = m_et_note_add_content.getSelectionEnd();
                // 先去掉监听器，否则会出现栈溢出
                m_et_note_add_content.removeTextChangedListener(this);

                // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
                // mEditText.setText(s);将这行代码注释掉就不会出现后面所说的输入法在数字界面自动跳转回主界面的问题了，多谢@ainiyidiandian的提醒
                m_et_note_add_content.setSelection(editStart);
                // 恢复监听器
                m_et_note_add_content.addTextChangedListener(this);
            }
        });
        Intent intent = getIntent();
        String oper = intent.getStringExtra("oper");
        if ("update".equals(oper)) {
            id = intent.getLongExtra("id",0);
            service = new NoteService(this);
            Note oneById = service.getOneById(id);
            m_et_note_add_title.setText(oneById.getTitle());
            mSpinner.setSelection(oneById.getNoteType());
            m_et_note_add_content.setText(oneById.getContent());
        }
    }

    @Override
    protected void toUIOper() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!TextUtils.isEmpty(m_et_note_add_title.getText().toString())) {
                    Intent intent = getIntent();
                    String oper = intent.getStringExtra("oper");
                    if ("update".equals(oper)) {
                        updateNote();
                    } else {
                        saveNote();
                    }
                }
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(m_et_note_add_title.getText().toString())) {
            Intent intent = getIntent();
            String oper = intent.getStringExtra("oper");
            if ("update".equals(oper)) {
                updateNote();
            } else {
                saveNote();
            }
        }
    }

    private void saveNote() {
        service = new NoteService(this);
        Note note = new Note();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = dateFormat.format(date);
        note.setTitle(m_et_note_add_title.getText().toString().trim());
        note.setContent(m_et_note_add_content.getText().toString().trim());
        note.setNoteType(mSpinner.getSelectedItemPosition());
        note.setCreateDate(format);
        note.setUpdateDate(format);
        service.save(note);
    }

    private void updateNote() {
        service = new NoteService(this);
        Note note = new Note();
        note.setId(id);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = dateFormat.format(date);
        note.setTitle(m_et_note_add_title.getText().toString().trim());
        note.setContent(m_et_note_add_content.getText().toString().trim());
        note.setNoteType(mSpinner.getSelectedItemPosition());
        note.setUpdateDate(format);
        service.update(note);
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        m_et_note_add_content.setText(String.valueOf((MAX_COUNT - getInputCount())));
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(m_et_note_add_content.getText().toString());
    }

}