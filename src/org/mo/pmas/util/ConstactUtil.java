package org.mo.pmas.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import org.mo.pmas.activity.R;
import org.mo.pmas.entity.Contact;

public class ConstactUtil {


    /**
     * 获取所有数据
     *
     * @return
     */
    public static List<Contact> getAllCallRecords(Context context) {
        List<Contact> contaList = new ArrayList<Contact>();
        Cursor c = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
                        + " COLLATE LOCALIZED ASC");
        if (c.moveToFirst()) {
            do {
                // 获得联系人的ID号
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                // 获得联系人姓名
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String number = null;
                if (phoneCount > 0) {
                    // 获得联系人的电话号码
                    Cursor phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }
                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;
                //得到联系人头像ID
                Long photoid = c.getLong(c.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    contactPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.h001);
                }
                //TODO 添加通讯录的信息
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(contactId));
                contact.setName(name);
                contact.setPhoneNumber(number);
                contact.setContactPhoto(contactPhoto);
                contaList.add(contact);
            } while (c.moveToNext());
        }
        c.close();
        return contaList;
    }

}
