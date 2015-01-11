package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.resolver.ContactGroupResolver;

/**
 * Created by moziqi on 2015/1/11 0011.
 */
public class ContactGroupAddActivtiy extends BaseFramgmentActivity implements View.OnClickListener {

    private EditText et_contact_group_add_title;
    private Button btn_contact_group_add_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_group_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        et_contact_group_add_title = (EditText) findViewById(R.id.et_contact_group_add_title);
        btn_contact_group_add_save = (Button) findViewById(R.id.btn_contact_group_add_save);
        btn_contact_group_add_save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact_group_add_save:
                addContactGroup();
                break;
        }
    }

    private void addContactGroup() {
        if (et_contact_group_add_title.getText().toString().trim().length() == 0) {
            ShowToast("名称不能为空");
        }
        ContactGroupResolver contactGroupResolver = new ContactGroupResolver(this);
        ContactGroup contactGroup = new ContactGroup();
        contactGroup.setName(et_contact_group_add_title.getText().toString().trim());
        boolean save = contactGroupResolver.save(contactGroup);
        if (save) {
            ShowToast("保存\"" + contactGroup.getName() + "\"成功");
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
        Intent intent = new Intent(ContactGroupAddActivtiy.this, ContactGroupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.myenteranim, R.anim.myexitanim);
        this.finish();
    }
}