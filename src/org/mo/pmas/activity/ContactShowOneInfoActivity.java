package org.mo.pmas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.mo.common.activity.BaseFramgmentActivity;

/**
 * Created by moziqi on 2015/1/12 0012.
 */
public class ContactShowOneInfoActivity extends BaseFramgmentActivity implements View.OnClickListener {

    private ImageView imageView_show_head;
    private TextView tv_contact_show_name;
    private EditText et_contact_show_birthday;
    private LinearLayout ll_show_phone1;
    private LinearLayout ll_show_phone2;
    private LinearLayout ll_show_phone3;
    private LinearLayout ll_show_phone4;
    private LinearLayout ll_show_phone5;
    private EditText et_contact_show_phone1;
    private EditText et_contact_show_phone2;
    private EditText et_contact_show_phone3;
    private EditText et_contact_show_phone4;
    private EditText et_contact_show_phone5;
    private EditText et_contact_show_email;
    private EditText et_contact_show_address;
    private EditText et_contact_show_contact_group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show_one_info);
    }

    @Override
    protected void toInitUI() {
        imageView_show_head = (ImageView) findViewById(R.id.imageView_show_head);
        tv_contact_show_name = (TextView) findViewById(R.id.tv_contact_show_name);
        et_contact_show_birthday = (EditText) findViewById(R.id.et_contact_show_birthday);
        ll_show_phone1 = (LinearLayout) findViewById(R.id.ll_show_phone1);
        ll_show_phone2 = (LinearLayout) findViewById(R.id.ll_show_phone2);
        ll_show_phone3 = (LinearLayout) findViewById(R.id.ll_show_phone3);
        ll_show_phone4 = (LinearLayout) findViewById(R.id.ll_show_phone4);
        ll_show_phone5 = (LinearLayout) findViewById(R.id.ll_show_phone5);
        et_contact_show_phone1 = (EditText) findViewById(R.id.et_contact_show_phone1);
        et_contact_show_phone2 = (EditText) findViewById(R.id.et_contact_show_phone2);
        et_contact_show_phone3 = (EditText) findViewById(R.id.et_contact_show_phone3);
        et_contact_show_phone4 = (EditText) findViewById(R.id.et_contact_show_phone4);
        et_contact_show_phone5 = (EditText) findViewById(R.id.et_contact_show_phone5);
        et_contact_show_email = (EditText) findViewById(R.id.et_contact_show_email);
        et_contact_show_address = (EditText) findViewById(R.id.et_contact_show_address);
        et_contact_show_contact_group = (EditText) findViewById(R.id.et_contact_edit_contact_group);
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public void onClick(View v) {

    }
}