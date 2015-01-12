package org.mo.pmas.resolver;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import org.mo.pmas.activity.R;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.entity.Phone;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moziqi on 2015/1/9 0009.
 */
public class ContactResolver implements BaseResolver<Contact> {
    private Context mContext;

    public ContactResolver(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean save(Contact entity) {
        return false;
    }

    @Override
    public boolean update(Contact entity) {
        return false;
    }

    @Override
    public boolean delete(Contact entity) {
        return false;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<Contact>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
                        + " COLLATE LOCALIZED ASC");
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                // 获得联系人的ID号
                int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                // 获得联系人姓名
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                //获取联系人生日
//                String[] projection = new String[]{ContactsContract.CommonDataKinds.Event.DATA1};
//                String selection = ContactsContract.Data.MIMETYPE
//                        + "='"
//                        + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
//                        + "'"
//                        + " and "
//                        + ContactsContract.CommonDataKinds.Event.TYPE
//                        + "='"
//                        + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
//                        + "'"
//                        + " and "
//                        + ContactsContract.CommonDataKinds.Event.CONTACT_ID
//                        + " = " + contactId;
//                Cursor birthdayCursor = mContext.getContentResolver().query(
//                        ContactsContract.Data.CONTENT_URI,
//                        projection,
//                        selection,
//                        null,
//                        null);
//                String birthday = null;
//                if (birthdayCursor.moveToFirst()) {
//                    birthday = birthdayCursor.getString(birthdayCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));
//                }
//                birthdayCursor.close();

                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;
                //得到联系人头像ID
                int photoid = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, (long) contactId);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.h001);
                }

                // 获取该联系人邮箱
                //我这里只需要获取一个,没有遍历全部出来
//                Cursor cursorEmail = mContext.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                        null,
//                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
//                                + " = " + contactId, null, null);
//                String email = null;
//                if (cursorEmail.moveToFirst()) {
//                    email = cursorEmail.getString(
//                            cursorEmail.getColumnIndex(
//                                    ContactsContract.CommonDataKinds.Email.DATA));
//                }
//                cursorEmail.close();

                // 获取该联系人地址
                //我这里只需要获取一个,没有遍历全部出来
//                Cursor cursorAddress = mContext.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
//                        null,
//                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
//                                + " = " + contactId, null, null);
//                String address = null;
//                if (cursorAddress.moveToFirst()) {
//                    //获取CITY
//                    address = cursorAddress.getString(cursorAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
//                }
//                cursorAddress.close();

                contact.setId(contactId);
                contact.setName(contactName);
                contact.setContactPhoto(contactPhoto);
//                contact.setEmail(email);
//                contact.setAddress(address);
//                contact.setBirthday(birthday);

                // 查看该联系人有多少个电话号码。如果没有这返回值为0
//                int has_phone_number = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                if (has_phone_number > 0) {
//                    // 获得联系人的电话号码
//                    Cursor cursorPhone = mContext.getContentResolver().query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                    + " = " + contactId, null, null);
//                    List<Phone> phones = new ArrayList<Phone>();
//                    if (cursorPhone.moveToFirst()) {
//                        do {
//                            Phone phone = new Phone();
//                            int phoneId = cursorPhone.getInt(
//                                    cursorPhone.getColumnIndex(
//                                            ContactsContract.CommonDataKinds.Phone._ID));
//                            String phoneNumber = cursorPhone.getString(
//                                    cursorPhone.getColumnIndex(
//                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            phone.setContact(contact);
//                            phone.setId(phoneId);
//                            phone.setPhoneNumber(phoneNumber);
//                            //保存phone list集合
//                            phones.add(phone);
//                        } while (cursorPhone.moveToNext());
//                    }
//                    cursorPhone.close();
//                    //如果有电话号码就把phones添加到contact上
//                    contact.setPhones(phones);
//                }
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }

    @Override
    public Contact findOneById(Serializable id) {
        Contact contact = null;
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + "=?",
                new String[]{Integer.parseInt(String.valueOf(id)) + ""},
                null);
        if (cursor.moveToFirst()) {
            contact = new Contact();
            // 获得联系人的ID号
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // 获得联系人姓名
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //获取联系人生日
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Event.DATA1};
            String selection = ContactsContract.Data.MIMETYPE
                    + "='"
                    + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
                    + "'"
                    + " and "
                    + ContactsContract.CommonDataKinds.Event.TYPE
                    + "='"
                    + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
                    + "'"
                    + " and "
                    + ContactsContract.CommonDataKinds.Event.CONTACT_ID
                    + " = " + contactId;
            Cursor birthdayCursor = mContext.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);
            String birthday = null;
            if (birthdayCursor.moveToFirst()) {
                birthday = birthdayCursor.getString(birthdayCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));
            }
            birthdayCursor.close();
            //得到联系人头像Bitamp
            Bitmap contactPhoto = null;
            //得到联系人头像ID
            int photoid = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
            //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
            if (photoid > 0) {
                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, (long) contactId);
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), uri);
                contactPhoto = BitmapFactory.decodeStream(input);
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.h001);
            }
            // 获取该联系人邮箱
            //我这里只需要获取一个,没有遍历全部出来
            Cursor cursorEmail = mContext.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID
                            + " = " + contactId, null, null);
            String email = null;
            if (cursorEmail.moveToFirst()) {
                email = cursorEmail.getString(
                        cursorEmail.getColumnIndex(
                                ContactsContract.CommonDataKinds.Email.DATA));
            }
            cursorEmail.close();
            // 获取该联系人地址
            //我这里只需要获取一个,没有遍历全部出来
            Cursor cursorAddress = mContext.getContentResolver().query(
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
                            + " = " + contactId, null, null);
            String address = null;
            if (cursorAddress.moveToFirst()) {
                //获取CITY
                address = cursorAddress.getString(cursorAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            }
            cursorAddress.close();
            contact.setId(contactId);
            contact.setName(contactName);
            contact.setContactPhoto(contactPhoto);
            contact.setEmail(email);
            contact.setAddress(address);
            contact.setBirthday(birthday);
            // 查看该联系人有多少个电话号码。如果没有这返回值为0
            int has_phone_number = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (has_phone_number > 0) {
                // 获得联系人的电话号码
                Cursor cursorPhone = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                List<Phone> phones = new ArrayList<Phone>();
                if (cursorPhone.moveToFirst()) {
                    do {
                        Phone phone = new Phone();
                        int phoneId = cursorPhone.getInt(
                                cursorPhone.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone._ID));
                        String phoneNumber = cursorPhone.getString(
                                cursorPhone.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phone.setContact(contact);
                        phone.setId(phoneId);
                        phone.setPhoneNumber(phoneNumber);
                        //保存phone list集合
                        phones.add(phone);
                    } while (cursorPhone.moveToNext());
                }
                cursorPhone.close();
                //如果有电话号码就把phones添加到contact上
                contact.setPhones(phones);
            }
        }
        return contact;
    }
}
