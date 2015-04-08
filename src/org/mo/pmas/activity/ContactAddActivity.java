package org.mo.pmas.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.Bitmap2Byte;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.entity.Phone;
import org.mo.pmas.resolver.ContactGroupResolver;
import org.mo.pmas.resolver.ContactResolver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by moziqi on 2015/1/13 0013.
 */
public class ContactAddActivity extends BaseFramgmentActivity implements View.OnClickListener {
//    private ImageView imageView_add_head;
    private EditText et_contact_add_name;
//    private EditText et_contact_add_birthday;
    private LinearLayout ll_add_phone1;
    private LinearLayout ll_add_phone2;
    private LinearLayout ll_add_phone3;
    private LinearLayout ll_add_phone4;
    private LinearLayout ll_add_phone5;
    private EditText et_contact_add_phone1;
    private EditText et_contact_add_phone2;
    private EditText et_contact_add_phone3;
    private EditText et_contact_add_phone4;
    private EditText et_contact_add_phone5;
    private EditText et_contact_add_email;
    private EditText et_contact_add_address;
    private EditText et_contact_add_contact_group;
    private Button btn_contact_add_save;
    private List<ContactGroup> contactGroups;
    private String[] group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
//        imageView_add_head = (ImageView) findViewById(R.id.imageView_add_head);
        et_contact_add_name = (EditText) findViewById(R.id.et_contact_add_name);
//        et_contact_add_birthday = (EditText) findViewById(R.id.et_contact_add_birthday);
        ll_add_phone1 = (LinearLayout) findViewById(R.id.ll_add_phone1);
        ll_add_phone2 = (LinearLayout) findViewById(R.id.ll_add_phone2);
        ll_add_phone3 = (LinearLayout) findViewById(R.id.ll_add_phone3);
        ll_add_phone4 = (LinearLayout) findViewById(R.id.ll_add_phone4);
        ll_add_phone5 = (LinearLayout) findViewById(R.id.ll_add_phone5);
        ll_add_phone1 = (LinearLayout) findViewById(R.id.ll_add_phone1);
        et_contact_add_phone1 = (EditText) findViewById(R.id.et_contact_add_phone1);
        et_contact_add_phone2 = (EditText) findViewById(R.id.et_contact_add_phone2);
        et_contact_add_phone3 = (EditText) findViewById(R.id.et_contact_add_phone3);
        et_contact_add_phone4 = (EditText) findViewById(R.id.et_contact_add_phone4);
        et_contact_add_phone5 = (EditText) findViewById(R.id.et_contact_add_phone5);
        et_contact_add_email = (EditText) findViewById(R.id.et_contact_add_email);
        et_contact_add_address = (EditText) findViewById(R.id.et_contact_add_address);
        et_contact_add_contact_group = (EditText) findViewById(R.id.et_contact_add_contact_group);
        btn_contact_add_save = (Button) findViewById(R.id.btn_contact_add_save);
        et_contact_add_phone1.addTextChangedListener(new TextWatcherListener1());
        et_contact_add_phone2.addTextChangedListener(new TextWatcherListener2());
        et_contact_add_phone3.addTextChangedListener(new TextWatcherListener3());
        et_contact_add_phone4.addTextChangedListener(new TextWatcherListener4());
        ll_add_phone2.setVisibility(View.GONE);
        ll_add_phone3.setVisibility(View.GONE);
        ll_add_phone4.setVisibility(View.GONE);
        ll_add_phone5.setVisibility(View.GONE);
        btn_contact_add_save.setOnClickListener(this);
        et_contact_add_contact_group.setInputType(InputType.TYPE_NULL);
        //TODO 群组列表
        ContactGroupResolver contactGroupResolver = new ContactGroupResolver(ContactAddActivity.this);
        contactGroups = contactGroupResolver.findAll();
        group = new String[contactGroups.size()];
        for (int i = 0; i < contactGroups.size(); i++) {
            group[i] = new String(contactGroups.get(i).getName());
        }
        if (group != null) {
            et_contact_add_contact_group.setOnClickListener(this);
        } else {
            ShowToast("没有分组");
        }
//        et_contact_add_birthday.setInputType(InputType.TYPE_NULL);
//        et_contact_add_birthday.setOnClickListener(this);
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact_add_save:
//                Drawable drawable = PmasAppliaction.getInstance().getResources().getDrawable(R.drawable.h001);
//                Bitmap bitmap = Bitmap2Byte.drawableToBitmap(drawable);
                Contact contact = new Contact();
                if (TextUtils.isEmpty(this.et_contact_add_name.getText())) {
                    ShowToast("名字不能为空");
                    return;
                }
                contact.setName(this.et_contact_add_name.getText().toString());

                if (!TextUtils.isEmpty(this.et_contact_add_address.getText())) {
                    contact.setAddress(this.et_contact_add_address.getText().toString());
                }
//                if (!TextUtils.isEmpty(this.et_contact_add_birthday.getText())) {
//                    contact.setBirthday(this.et_contact_add_birthday.getText().toString());
//                }
                if (!TextUtils.isEmpty(this.et_contact_add_email.getText())) {
                    contact.setEmail(this.et_contact_add_email.getText().toString());
                }
                int k = 0;
                if (!TextUtils.isEmpty(this.et_contact_add_contact_group.getText())) {
                    for (int i = 0; i < contactGroups.size(); i++, k++) {
                        ContactGroup contactGroup = contactGroups.get(i);
                        if (contactGroup.getName().equals(this.et_contact_add_contact_group.getText().toString())) {
                            break;
                        }
                    }
                    ShowToast(k + contactGroups.get(k).toString());
                    contact.setmContactGroup(contactGroups.get(k));
                }
                List<Phone> phones = new ArrayList<Phone>();
                if (!TextUtils.isEmpty(this.et_contact_add_phone1.getText())) {
                    Phone phone = new Phone();
                    phone.setPhoneNumber(this.et_contact_add_phone1.getText().toString());
                    phones.add(phone);
                }
                if (!TextUtils.isEmpty(this.et_contact_add_phone2.getText())) {
                    Phone phone = new Phone();
                    phone.setPhoneNumber(this.et_contact_add_phone2.getText().toString());
                    phones.add(phone);
                }
                if (!TextUtils.isEmpty(this.et_contact_add_phone3.getText())) {
                    Phone phone = new Phone();
                    phone.setPhoneNumber(this.et_contact_add_phone3.getText().toString());
                    phones.add(phone);
                }
                if (!TextUtils.isEmpty(this.et_contact_add_phone4.getText())) {
                    Phone phone = new Phone();
                    phone.setPhoneNumber(this.et_contact_add_phone4.getText().toString());
                    phones.add(phone);
                }
                if (!TextUtils.isEmpty(this.et_contact_add_phone5.getText())) {
                    Phone phone = new Phone();
                    phone.setPhoneNumber(this.et_contact_add_phone5.getText().toString());
                    phones.add(phone);
                }
                contact.setPhones(phones);
                ContactResolver contactResolver = new ContactResolver(this);
                boolean save = contactResolver.save(contact);
                if (save) {
                    ShowToast("保存成功");
                } else {
                    ShowToast("保存失败");
                }
                break;
//            case R.id.et_contact_add_birthday:
//                datePickerDialog();
//                break;
            case R.id.et_contact_add_contact_group:
                groupAlertDialog();
            default:
                break;
        }
    }


//    private void datePickerDialog() {
//        Calendar c = Calendar.getInstance();
//        new DatePickerDialog(this, 5, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                et_contact_add_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//            }
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
//    }

    private void groupAlertDialog() {
        new AlertDialog.Builder(this).setTitle("群组").
                setSingleChoiceItems(
                        group, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                et_contact_add_contact_group.setText(group[which]);
                            }
                        }).setNegativeButton("取消", null).show();
    }

    private class TextWatcherListener1 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String trim1 = et_contact_add_phone1.getText().toString().trim();
            if (trim1.length() == 0) {
                ll_add_phone2.setVisibility(View.GONE);
                ll_add_phone3.setVisibility(View.GONE);
                ll_add_phone4.setVisibility(View.GONE);
                ll_add_phone5.setVisibility(View.GONE);
            } else {
                ll_add_phone2.setVisibility(View.VISIBLE);
                ll_add_phone3.setVisibility(View.GONE);
                ll_add_phone4.setVisibility(View.GONE);
                ll_add_phone5.setVisibility(View.GONE);
            }
            et_contact_add_phone1.addTextChangedListener(this);
        }

    }

    private class TextWatcherListener2 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String trim2 = et_contact_add_phone2.getText().toString().trim();
            if (trim2.length() == 0) {
                ll_add_phone3.setVisibility(View.GONE);
                ll_add_phone4.setVisibility(View.GONE);
                ll_add_phone5.setVisibility(View.GONE);
            } else {
                ll_add_phone2.setVisibility(View.VISIBLE);
                ll_add_phone3.setVisibility(View.VISIBLE);
                ll_add_phone4.setVisibility(View.GONE);
                ll_add_phone5.setVisibility(View.GONE);
            }
            et_contact_add_phone2.addTextChangedListener(this);
        }
    }

    private class TextWatcherListener3 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String trim3 = et_contact_add_phone3.getText().toString().trim();
            if (trim3.length() == 0) {
                ll_add_phone4.setVisibility(View.GONE);
                ll_add_phone5.setVisibility(View.GONE);
            } else {
                ll_add_phone2.setVisibility(View.VISIBLE);
                ll_add_phone3.setVisibility(View.VISIBLE);
                ll_add_phone4.setVisibility(View.VISIBLE);
                ll_add_phone5.setVisibility(View.GONE);
            }
            et_contact_add_phone3.addTextChangedListener(this);
        }
    }

    private class TextWatcherListener4 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String trim4 = et_contact_add_phone4.getText().toString().trim();
            if (trim4.length() == 0) {
                ll_add_phone5.setVisibility(View.GONE);
            } else {
                ll_add_phone2.setVisibility(View.VISIBLE);
                ll_add_phone3.setVisibility(View.VISIBLE);
                ll_add_phone4.setVisibility(View.VISIBLE);
                ll_add_phone5.setVisibility(View.VISIBLE);
            }
            et_contact_add_phone4.addTextChangedListener(this);
        }
    }
}