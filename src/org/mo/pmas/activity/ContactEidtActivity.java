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
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class ContactEidtActivity extends BaseFramgmentActivity {
    private ViewHolder viewHolder;
    private String mImgPath;
    private Bitmap head;//头像Bitmap
    private FileOutputStream b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init(getIntent());
    }

    /**
     * 初始化
     *
     * @param intent
     */
    void init(Intent intent) {
//===============================================================================================
        viewHolder = new ViewHolder();
        viewHolder.mEditText_group = (EditText) findViewById(R.id.editText_group);
        viewHolder.mEditText_name = (EditText) findViewById(R.id.editText_name);
        viewHolder.mEditText_phoneNumber1 = (EditText) findViewById(R.id.editText_phoneNumber1);
        viewHolder.mEditText_phoneNumber2 = (EditText) findViewById(R.id.editText_phoneNumber2);
        viewHolder.mEditText_phoneNumber3 = (EditText) findViewById(R.id.editText_phoneNumber3);
        viewHolder.mEditText_phoneNumber4 = (EditText) findViewById(R.id.editText_phoneNumber4);
        viewHolder.mEditText_phoneNumber5 = (EditText) findViewById(R.id.editText_phoneNumber5);
        String phoneNumber1 = viewHolder.mEditText_phoneNumber1.getText().toString();
        if (StringUtil.notEmpty(phoneNumber1)) {

        }
        viewHolder.mEditText_birthday = (EditText) findViewById(R.id.editText_birthday);
        viewHolder.mImageView_head = (ImageView) findViewById(R.id.imageView_head);
//============================================================================================
        viewHolder.mEditText_group.setInputType(InputType.TYPE_NULL);
        //TODO 群组列表
        final String group[] = new String[]{"朋友", "同事"};
        if (group != null) {
            viewHolder.mEditText_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupAlertDialog(group);
                }
            });
        } else {
            Intent intent1 = new Intent();
        }
//===========================================================================================================
        //TODO 日期选择
        viewHolder.mEditText_birthday.setInputType(InputType.TYPE_NULL);
        viewHolder.mEditText_birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog();

                }
            }
        });
        viewHolder.mEditText_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog();
            }
        });
//============================================================================================================
        //TODO 显示用户信息
        Bundle bundle = intent.getExtras();
        long id = bundle.getLong("id");
        String name = bundle.getString("name");
        String phoneNumber = bundle.getString("phoneNumber");
        viewHolder.mEditText_name.setText(name);
        viewHolder.mEditText_phoneNumber1.setText(phoneNumber);
        Parcelable contactPhoto = bundle.getParcelable("contactPhoto");
        Bitmap bitmap = (Bitmap) contactPhoto;
        viewHolder.mImageView_head.setImageBitmap(bitmap);
//============================================================================================================
        viewHolder.mImageView_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAlertDialog();
            }
        });
        //异步加载图片
//        new ImgAsyncTask();
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
                viewHolder.mEditText_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
                                viewHolder.mEditText_group.setText(group[which]);
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
                        viewHolder.mImageView_head.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

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
                intent.setClass(ContactEidtActivity.this, ScheduleEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }


    final static class ViewHolder {
        EditText mEditText_group;
        EditText mEditText_name;
        EditText mEditText_phoneNumber1;
        EditText mEditText_phoneNumber2;
        EditText mEditText_phoneNumber3;
        EditText mEditText_phoneNumber4;
        EditText mEditText_phoneNumber5;
        EditText mEditText_birthday;
        EditText mEditText_email;
        EditText mEditText_address;
        ImageView mImageView_head;
        Button mButton_detete;
    }

//    class ImgAsyncTask extends AsyncTask<Void, Integer, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            String path = Environment.getExternalStorageDirectory().toString();
//            Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
//            if (bt != null) {
//                viewHolder.mImageView_head.setImageBitmap(bt);//用ImageView显示出来
//            }
//            return true;
//        }
//    }
}
