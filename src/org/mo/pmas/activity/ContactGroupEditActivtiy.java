package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.resolver.ContactGroupResolver;

/**
 * Created by moziqi on 2015/1/11 0011.
 */
public class ContactGroupEditActivtiy extends BaseFramgmentActivity implements View.OnClickListener {
    private EditText et_contact_group_edit_title;
    private Button btn_contact_group_edit_save;
    private ContactGroup contactGroup;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_contact_group_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    private void initUI() {
        et_contact_group_edit_title = (EditText) findViewById(R.id.et_contact_group_edit_title);
        btn_contact_group_edit_save = (Button) findViewById(R.id.btn_contact_group_edit_save);
        btn_contact_group_edit_save.setOnClickListener(this);
        contactGroup = new ContactGroup();
        intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        String name = intent.getStringExtra("name");
        et_contact_group_edit_title.setText(name);
        contactGroup.setId(id);
        contactGroup.setName(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact_group_edit_save:
                editContactGroup();
                break;
        }
    }

    private void editContactGroup() {
        if (TextUtils.isEmpty(et_contact_group_edit_title.getText().toString().trim())) {
            ShowToast("名称不能为空");
            return;
        }
        ContactGroupResolver contactGroupResolver = new ContactGroupResolver(this);
        contactGroup.setName(et_contact_group_edit_title.getText().toString().trim());
        boolean save = contactGroupResolver.update(contactGroup);
        if (save) {
            ShowToast("修改\"" + contactGroup.getName() + "\"成功");
        } else {
            ShowToast("已存在\"" + contactGroup.getName() + "\"群组");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoMain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoMain();
    }

    private void gotoMain() {
//        PmasAppliaction.getInstance().exit();
//        Intent intent = new Intent(ContactGroupEditActivtiy.this, ContactGroupActivity.class);
//        startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.myenteranim, R.anim.myexitanim);
//        this.setResult(2, intent);
    }
}