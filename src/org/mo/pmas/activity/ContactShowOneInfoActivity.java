package org.mo.pmas.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.resolver.ContactGroupResolver;
import org.mo.pmas.resolver.ContactResolver;

/**
 * Created by moziqi on 2015/1/12 0012.
 */
public class ContactShowOneInfoActivity extends BaseFramgmentActivity implements View.OnClickListener {

    private ImageView imageView_show_head;
    private TextView tv_contact_show_name;
    //    private TextView tv_contact_show_birthday;
    private LinearLayout ll_show_phone1;
    private LinearLayout ll_show_phone2;
    private LinearLayout ll_show_phone3;
    private LinearLayout ll_show_phone4;
    private LinearLayout ll_show_phone5;
    private View view_show_phone1;
    private View view_show_phone2;
    private View view_show_phone3;
    private View view_show_phone4;
    private View view_show_phone5;
    private TextView tv_contact_show_phone1;
    private TextView tv_contact_show_phone2;
    private TextView tv_contact_show_phone3;
    private TextView tv_contact_show_phone4;
    private TextView tv_contact_show_phone5;
    private TextView tv_contact_show_email;
    private TextView tv_contact_show_address;
    private TextView tv_contact_show_contact_group;
    //    private final static String NOINFO = "无";
    private int id;
    private Contact oneById;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show_one_info);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
        imageView_show_head = (ImageView) findViewById(R.id.imageView_show_head);
        tv_contact_show_name = (TextView) findViewById(R.id.tv_contact_show_name);
//        tv_contact_show_birthday = (TextView) findViewById(R.id.tv_contact_show_birthday);
        ll_show_phone1 = (LinearLayout) findViewById(R.id.ll_show_phone1);
        ll_show_phone2 = (LinearLayout) findViewById(R.id.ll_show_phone2);
        ll_show_phone3 = (LinearLayout) findViewById(R.id.ll_show_phone3);
        ll_show_phone4 = (LinearLayout) findViewById(R.id.ll_show_phone4);
        ll_show_phone5 = (LinearLayout) findViewById(R.id.ll_show_phone5);
        view_show_phone1 = findViewById(R.id.view_show_phone1);
        view_show_phone2 = findViewById(R.id.view_show_phone2);
        view_show_phone3 = findViewById(R.id.view_show_phone3);
        view_show_phone4 = findViewById(R.id.view_show_phone4);
        view_show_phone5 = findViewById(R.id.view_show_phone5);
        tv_contact_show_phone1 = (TextView) findViewById(R.id.tv_contact_show_phone1);
        tv_contact_show_phone2 = (TextView) findViewById(R.id.tv_contact_show_phone2);
        tv_contact_show_phone3 = (TextView) findViewById(R.id.tv_contact_show_phone3);
        tv_contact_show_phone4 = (TextView) findViewById(R.id.tv_contact_show_phone4);
        tv_contact_show_phone5 = (TextView) findViewById(R.id.tv_contact_show_phone5);
        tv_contact_show_email = (TextView) findViewById(R.id.tv_contact_show_email);
        tv_contact_show_address = (TextView) findViewById(R.id.tv_contact_show_address);
        tv_contact_show_contact_group = (TextView) findViewById(R.id.tv_contact_show_contact_group);
        ll_show_phone1.setOnClickListener(this);
        ll_show_phone2.setOnClickListener(this);
        ll_show_phone3.setOnClickListener(this);
        ll_show_phone4.setOnClickListener(this);
        ll_show_phone5.setOnClickListener(this);
    }

    @Override
    protected void toUIOper() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        ContactResolver contactResolver = new ContactResolver(ContactShowOneInfoActivity.this);
        oneById = contactResolver.findOneById(id);
        tv_contact_show_name.setText(oneById.getName());
//        tv_contact_show_birthday.setText(oneById.getBirthday());
        ContactGroup contactGroup = oneById.getmContactGroup();
        if (contactGroup != null) {
            tv_contact_show_contact_group.setText(contactGroup.getName());
        }
        tv_contact_show_email.setText(oneById.getEmail());
        tv_contact_show_address.setText(oneById.getAddress());
        if (oneById.getPhones() != null && oneById.getPhones().size() >= 1) {
            switch (oneById.getPhones().size()) {
                case 1:
                    ll_show_phone2.setVisibility(View.GONE);
                    ll_show_phone3.setVisibility(View.GONE);
                    ll_show_phone4.setVisibility(View.GONE);
                    ll_show_phone5.setVisibility(View.GONE);
                    view_show_phone2.setVisibility(View.GONE);
                    view_show_phone3.setVisibility(View.GONE);
                    view_show_phone4.setVisibility(View.GONE);
                    view_show_phone5.setVisibility(View.GONE);
                    tv_contact_show_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 2:
                    ll_show_phone3.setVisibility(View.GONE);
                    ll_show_phone4.setVisibility(View.GONE);
                    ll_show_phone5.setVisibility(View.GONE);
                    view_show_phone3.setVisibility(View.GONE);
                    view_show_phone4.setVisibility(View.GONE);
                    view_show_phone5.setVisibility(View.GONE);
                    tv_contact_show_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    tv_contact_show_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 3:
                    ll_show_phone4.setVisibility(View.GONE);
                    ll_show_phone5.setVisibility(View.GONE);
                    view_show_phone4.setVisibility(View.GONE);
                    view_show_phone5.setVisibility(View.GONE);
                    tv_contact_show_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    tv_contact_show_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    tv_contact_show_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 4:
                    ll_show_phone5.setVisibility(View.GONE);
                    view_show_phone5.setVisibility(View.GONE);
                    tv_contact_show_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    tv_contact_show_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    tv_contact_show_phone4.setText(oneById.getPhones().get(3).getPhoneNumber());
                    tv_contact_show_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                case 5:
                    tv_contact_show_phone2.setText(oneById.getPhones().get(1).getPhoneNumber());
                    tv_contact_show_phone3.setText(oneById.getPhones().get(2).getPhoneNumber());
                    tv_contact_show_phone4.setText(oneById.getPhones().get(3).getPhoneNumber());
                    tv_contact_show_phone5.setText(oneById.getPhones().get(4).getPhoneNumber());
                    tv_contact_show_phone1.setText(oneById.getPhones().get(0).getPhoneNumber());
                    break;
                default:
                    break;
            }
        } else {
            ll_show_phone2.setVisibility(View.GONE);
            ll_show_phone3.setVisibility(View.GONE);
            ll_show_phone4.setVisibility(View.GONE);
            ll_show_phone5.setVisibility(View.GONE);
            view_show_phone2.setVisibility(View.GONE);
            view_show_phone3.setVisibility(View.GONE);
            view_show_phone4.setVisibility(View.GONE);
            view_show_phone5.setVisibility(View.GONE);
        }
        imageView_show_head.setImageBitmap(oneById.getContactPhoto());
    }

    @Override
    public void onClick(View v) {
        String strMobile;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        switch (v.getId()) {
            case R.id.ll_show_phone1:
                strMobile = tv_contact_show_phone1.getText().toString();
                intent.setData(Uri.parse("tel:" + strMobile));
                break;
            case R.id.ll_show_phone2:
                strMobile = tv_contact_show_phone1.getText().toString();
                intent.setData(Uri.parse("tel:" + strMobile));
                break;
            case R.id.ll_show_phone3:
                strMobile = tv_contact_show_phone1.getText().toString();
                intent.setData(Uri.parse("tel:" + strMobile));
                break;
            case R.id.ll_show_phone4:
                strMobile = tv_contact_show_phone1.getText().toString();
                intent.setData(Uri.parse("tel:" + strMobile));
                break;
            case R.id.ll_show_phone5:
                strMobile = tv_contact_show_phone1.getText().toString();
                intent.setData(Uri.parse("tel:" + strMobile));
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_show_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.item_edit_contact:
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.setClass(this, ContactEidtActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_delete_contact:
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactShowOneInfoActivity.this);
                builder.setMessage("确认删除吗？");
                builder.setTitle("温馨提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ContactResolver contactResolver = new ContactResolver(ContactShowOneInfoActivity.this);
                        contactResolver.delete(oneById);
                        ContactShowOneInfoActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            default:
                return true;
        }
    }
}