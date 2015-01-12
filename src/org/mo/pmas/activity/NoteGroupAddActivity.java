package org.mo.pmas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.activity.IBaseActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.bmob.entity.NoteGroup;

import java.util.List;

/**
 * Created by moziqi on 2015/1/8 0008.
 */
public class NoteGroupAddActivity extends BaseFramgmentActivity implements View.OnClickListener, IBaseActivity {
    private EditText m_et_note_group_add_name;
    private EditText m_et_note_group_add_description;
    private Button m_btn_note_group_add_save;
    private MyUser myUser;
    private NoteGroup noteGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_group_add);
        myUser = MyUser.getCurrentUser(this, MyUser.class);
        toInitUI();
        toInitDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_note_group_add_save:
                findNoteGroupInfoByName();
                break;
        }
    }

    /**
     * 查询是否存在已存在的名字
     */
    private void findNoteGroupInfoByName() {
        if (TextUtils.isEmpty(m_et_note_group_add_name.getText().toString().trim())) {
            ShowToast("名字不能为空");
            return;
        }
        BmobQuery<NoteGroup> noteGroups = new BmobQuery<NoteGroup>();
        noteGroups.addWhereRelatedTo("noteGroups", new BmobPointer(myUser));
        noteGroups.addWhereEqualTo("name", m_et_note_group_add_name.getText().toString());
        noteGroups.findObjects(this, new FindListener<NoteGroup>() {
            @Override
            public void onSuccess(List<NoteGroup> noteGroups) {
                if (noteGroups.size() == 0) {
                    saveNoteGroupInfo();
                } else {
                    ShowToast("已存在\"" + m_et_note_group_add_name.getText().toString() + "\"类型");
                }
            }

            @Override
            public void onError(int i, String s) {
                showErrorIms(i);
            }
        });
    }

    /**
     * 保存note组
     */
    private void saveNoteGroupInfo() {
        noteGroup = new NoteGroup();
        noteGroup.setName(m_et_note_group_add_name.getText().toString());
        noteGroup.setDescription(m_et_note_group_add_description.getText().toString());
        noteGroup.setUser(myUser);
        noteGroup.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                addNoteGroupToUser();
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
    }

    /**
     * 把note组关联到User
     */
    private void addNoteGroupToUser() {
        BmobRelation noteGroups = new BmobRelation();
        noteGroups.add(noteGroup);
        myUser.setNoteGroups(noteGroups);
        myUser.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                BmobQuery.clearAllCachedResults(getApplicationContext());
                ShowToast("保存\"" + m_et_note_group_add_name.getText().toString() + "\"成功");
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
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