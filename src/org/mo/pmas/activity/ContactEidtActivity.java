package org.mo.pmas.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.StringUtil;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.resolver.ContactGroupResolver;
import org.mo.pmas.resolver.ContactResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class ContactEidtActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private String mImgPath;
    private Bitmap head;//头像Bitmap
    private FileOutputStream b = null;
    private List<ContactGroup> contactGroups;
    //对应xml的id
    private ImageView imageView_head;
    private EditText et_contact_edit_name;
    private EditText et_contact_edit_birthday;
    private LinearLayout ll_edit_phone1;
    private LinearLayout ll_edit_phone2;
    private LinearLayout ll_edit_phone3;
    private LinearLayout ll_edit_phone4;
    private LinearLayout ll_edit_phone5;
    private EditText et_contact_edit_phone1;
    private EditText et_contact_edit_phone2;
    private EditText et_contact_edit_phone3;
    private EditText et_contact_edit_phone4;
    private EditText et_contact_edit_phone5;
    private EditText et_contact_edit_email;
    private EditText et_contact_edit_address;
    private EditText et_contact_edit_contact_group;
    private String[] group;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_head:
                imgAlertDialog();
                break;
            case R.id.et_contact_edit_name:
                break;
            case R.id.et_contact_edit_birthday:
                datePickerDialog();
                break;
            case R.id.ll_edit_phone1:
                break;
            case R.id.ll_edit_phone2:
                break;
            case R.id.ll_edit_phone3:
                break;
            case R.id.ll_edit_phone4:
                break;
            case R.id.ll_edit_phone5:
                break;
            case R.id.et_contact_edit_phone1:
                String trim1 = et_contact_edit_phone1.getText().toString().trim();
                if (trim1.length() == 0) {
                    ll_edit_phone2.setVisibility(View.GONE);
                    ll_edit_phone3.setVisibility(View.GONE);
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                } else {
                    ll_edit_phone2.setVisibility(View.VISIBLE);
                    ll_edit_phone3.setVisibility(View.GONE);
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                }
                break;
            case R.id.et_contact_edit_phone2:
                String trim2 = et_contact_edit_phone2.getText().toString().trim();
                if (trim2.length() == 0) {
                    ll_edit_phone3.setVisibility(View.GONE);
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                } else {
                    ll_edit_phone2.setVisibility(View.VISIBLE);
                    ll_edit_phone3.setVisibility(View.VISIBLE);
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                }
                break;
            case R.id.et_contact_edit_phone3:
                String trim3 = et_contact_edit_phone3.getText().toString().trim();
                if (trim3.length() == 0) {
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                } else {
                    ll_edit_phone2.setVisibility(View.VISIBLE);
                    ll_edit_phone3.setVisibility(View.VISIBLE);
                    ll_edit_phone4.setVisibility(View.VISIBLE);
                    ll_edit_phone5.setVisibility(View.GONE);
                }
                break;
            case R.id.et_contact_edit_phone4:
                String trim4 = et_contact_edit_phone4.getText().toString().trim();
                if (trim4.length() == 0) {
                    ll_edit_phone5.setVisibility(View.GONE);
                } else {
                    ll_edit_phone2.setVisibility(View.VISIBLE);
                    ll_edit_phone3.setVisibility(View.VISIBLE);
                    ll_edit_phone4.setVisibility(View.VISIBLE);
                    ll_edit_phone5.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.et_contact_edit_phone5:
                ll_edit_phone5.setVisibility(View.VISIBLE);
                break;
            case R.id.et_contact_edit_email:
                break;
            case R.id.et_contact_edit_address:
                break;
            case R.id.et_contact_edit_contact_group:
                groupAlertDialog(group);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
        imageView_head = (ImageView) findViewById(R.id.imageView_head);
        et_contact_edit_name = (EditText) findViewById(R.id.et_contact_edit_name);
        et_contact_edit_birthday = (EditText) findViewById(R.id.et_contact_edit_birthday);
        ll_edit_phone1 = (LinearLayout) findViewById(R.id.ll_edit_phone1);
        ll_edit_phone2 = (LinearLayout) findViewById(R.id.ll_edit_phone2);
        ll_edit_phone3 = (LinearLayout) findViewById(R.id.ll_edit_phone3);
        ll_edit_phone4 = (LinearLayout) findViewById(R.id.ll_edit_phone4);
        ll_edit_phone5 = (LinearLayout) findViewById(R.id.ll_edit_phone5);
        et_contact_edit_phone1 = (EditText) findViewById(R.id.et_contact_edit_phone1);
        et_contact_edit_phone2 = (EditText) findViewById(R.id.et_contact_edit_phone2);
        et_contact_edit_phone3 = (EditText) findViewById(R.id.et_contact_edit_phone3);
        et_contact_edit_phone4 = (EditText) findViewById(R.id.et_contact_edit_phone4);
        et_contact_edit_phone5 = (EditText) findViewById(R.id.et_contact_edit_phone5);
        et_contact_edit_email = (EditText) findViewById(R.id.et_contact_edit_email);
        et_contact_edit_address = (EditText) findViewById(R.id.et_contact_edit_address);
        et_contact_edit_contact_group = (EditText) findViewById(R.id.et_contact_edit_contact_group);
        et_contact_edit_name.setOnClickListener(this);
        et_contact_edit_phone1.addTextChangedListener(new TextWatcherListener1());
        et_contact_edit_phone2.addTextChangedListener(new TextWatcherListener2());
        et_contact_edit_phone3.addTextChangedListener(new TextWatcherListener3());
        et_contact_edit_phone4.addTextChangedListener(new TextWatcherListener4());
        et_contact_edit_email.setOnClickListener(this);
        et_contact_edit_address.setOnClickListener(this);
    }

    @Override
    protected void toUIOper() {
        init(getIntent());
    }

    /**
     * 初始化
     *
     * @param intent
     */
    void init(Intent intent) {
        et_contact_edit_contact_group.setInputType(InputType.TYPE_NULL);
        //TODO 群组列表
        ContactGroupResolver contactGroupResolver = new ContactGroupResolver(ContactEidtActivity.this);
        contactGroups = contactGroupResolver.findAll();
        group = new String[contactGroups.size()];
        for (int i = 0; i < contactGroups.size(); i++) {
            group[i] = new String(contactGroups.get(i).getName());
        }
        if (group != null) {
            et_contact_edit_contact_group.setOnClickListener(this);
        } else {
            Intent intent1 = new Intent();
        }
//===========================================================================================================
        //TODO 日期选择
        et_contact_edit_birthday.setInputType(InputType.TYPE_NULL);
        et_contact_edit_birthday.setOnClickListener(this);
//============================================================================================================
        //TODO 显示用户信息
        Integer id = intent.getIntExtra("id", 0);
        ContactResolver contactResolver = new ContactResolver(ContactEidtActivity.this);
        Contact oneById = contactResolver.findOneById(id);
        et_contact_edit_name.setText(oneById.getName());
        et_contact_edit_birthday.setText(oneById.getBirthday());
        ContactGroup contactGroup = oneById.getmContactGroup();
        if (contactGroup != null) {
            et_contact_edit_contact_group.setText(contactGroup.getName());
        }
        et_contact_edit_email.setText(oneById.getEmail());
        et_contact_edit_address.setText(oneById.getAddress());
        if (oneById.getPhones() != null && oneById.getPhones().size() >= 1) {
            switch (oneById.getPhones().size()) {
                case 1:
                    ll_edit_phone3.setVisibility(View.GONE);
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                    et_contact_edit_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 2:
                    ll_edit_phone4.setVisibility(View.GONE);
                    ll_edit_phone5.setVisibility(View.GONE);
                    et_contact_edit_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    et_contact_edit_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 3:
                    ll_edit_phone5.setVisibility(View.GONE);
                    et_contact_edit_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    et_contact_edit_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    et_contact_edit_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 4:
                    et_contact_edit_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    et_contact_edit_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    et_contact_edit_phone4.setText(oneById.getPhones().get(3).getPhoneNumber());
                    et_contact_edit_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 5:
                    et_contact_edit_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    et_contact_edit_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    et_contact_edit_phone4.setText(oneById.getPhones().get(3).getPhoneNumber());
                    et_contact_edit_phone5.setText(oneById.getPhones().get(4).getPhoneNumber());
                    et_contact_edit_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                default:
                    break;
            }
        } else {
            ll_edit_phone2.setVisibility(View.GONE);
            ll_edit_phone3.setVisibility(View.GONE);
            ll_edit_phone4.setVisibility(View.GONE);
            ll_edit_phone5.setVisibility(View.GONE);
        }
        imageView_head.setImageBitmap(oneById.getContactPhoto());
//============================================================================================================
        imageView_head.setOnClickListener(this);
    }

    private void imgAlertDialog() {
        new AlertDialog.Builder(this).setTitle("请选择").
                setSingleChoiceItems(
                        new String[]{"拍照", "从相册取"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0:
                                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                                "head.jpg")));
                                        startActivityForResult(intent2, 2);//采用ForResult打开
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                        startActivityForResult(intent1, 1);
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
    }

    private void datePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, 5, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_contact_edit_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void groupAlertDialog(final String[] group) {
        new AlertDialog.Builder(this).setTitle("群组").
                setSingleChoiceItems(
                        group, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                et_contact_edit_contact_group.setText(group[which]);
                            }
                        }).setNegativeButton("取消", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        imageView_head.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        b = null;
        mImgPath = Environment.getExternalStorageDirectory().toString();
        File file = new File(mImgPath);
        file.mkdirs();// 创建文件夹
        String fileName = mImgPath + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            //TODO 待处理1
//            try {
//                //关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_actions, menu);
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
//                intent.setClass(ContactEidtActivity.this, ScheduleEditActivity.class);
//                startActivity(intent);
                return true;
            default:
                return false;
        }
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
            String trim1 = et_contact_edit_phone1.getText().toString().trim();
            if (trim1.length() == 0) {
                ll_edit_phone2.setVisibility(View.GONE);
                ll_edit_phone3.setVisibility(View.GONE);
                ll_edit_phone4.setVisibility(View.GONE);
                ll_edit_phone5.setVisibility(View.GONE);
            } else {
                ll_edit_phone2.setVisibility(View.VISIBLE);
                ll_edit_phone3.setVisibility(View.GONE);
                ll_edit_phone4.setVisibility(View.GONE);
                ll_edit_phone5.setVisibility(View.GONE);
            }
            et_contact_edit_phone1.addTextChangedListener(this);
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
            String trim2 = et_contact_edit_phone2.getText().toString().trim();
            if (trim2.length() == 0) {
                ll_edit_phone3.setVisibility(View.GONE);
                ll_edit_phone4.setVisibility(View.GONE);
                ll_edit_phone5.setVisibility(View.GONE);
            } else {
                ll_edit_phone2.setVisibility(View.VISIBLE);
                ll_edit_phone3.setVisibility(View.VISIBLE);
                ll_edit_phone4.setVisibility(View.GONE);
                ll_edit_phone5.setVisibility(View.GONE);
            }
            et_contact_edit_phone2.addTextChangedListener(this);
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
            String trim3 = et_contact_edit_phone3.getText().toString().trim();
            if (trim3.length() == 0) {
                ll_edit_phone4.setVisibility(View.GONE);
                ll_edit_phone5.setVisibility(View.GONE);
            } else {
                ll_edit_phone2.setVisibility(View.VISIBLE);
                ll_edit_phone3.setVisibility(View.VISIBLE);
                ll_edit_phone4.setVisibility(View.VISIBLE);
                ll_edit_phone5.setVisibility(View.GONE);
            }
            et_contact_edit_phone3.addTextChangedListener(this);
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
            String trim4 = et_contact_edit_phone4.getText().toString().trim();
            if (trim4.length() == 0) {
                ll_edit_phone5.setVisibility(View.GONE);
            } else {
                ll_edit_phone2.setVisibility(View.VISIBLE);
                ll_edit_phone3.setVisibility(View.VISIBLE);
                ll_edit_phone4.setVisibility(View.VISIBLE);
                ll_edit_phone5.setVisibility(View.VISIBLE);
            }
            et_contact_edit_phone4.addTextChangedListener(this);
        }
    }
}
