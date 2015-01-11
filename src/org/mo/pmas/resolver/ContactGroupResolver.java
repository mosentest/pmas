package org.mo.pmas.resolver;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import org.mo.pmas.entity.ContactGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by moziqi on 2014/12/29 0029.
 */
public class ContactGroupResolver implements BaseResolver<ContactGroup> {

    private Context mContext;

    public ContactGroupResolver(Context mContext) {
        this.mContext = mContext;
    }

    private ContentResolver resolver;

    public ContactGroupResolver(ContentResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 获取所有的组
     *
     * @return
     */
    public List<HashMap<String, String>> getGroups() {
        List<HashMap<String, String>> groups = new ArrayList<HashMap<String, String>>();

        Cursor cursor = resolver.query(ContactsContract.Groups.CONTENT_URI, null, null, null, null);
        HashMap<String, String> group = new HashMap<String, String>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
            group.put("id", id + "");
            group.put("title", title);
            groups.add(group);
        }
        cursor.close();
        return groups;
    }

    /**
     * 创建组
     *
     * @return int
     */
    public long createGroup(String title) {
        if (TextUtils.isEmpty(title)) {
            return -1;
        }
        long gId = getGroupByTitle(title);
        if (gId == -1) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.Groups.TITLE, title);
            Uri uri = resolver.insert(ContactsContract.Groups.CONTENT_URI, values);
            gId = ContentUris.parseId(uri);
        }
        return gId;
    }

    /**
     * 根据组的名称查询组
     *
     * @return int
     */
    public int getGroupByTitle(String title) {
        int id = -1;
        Cursor cursor = mContext.getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{ContactsContract.Groups._ID},
                ContactsContract.Groups.TITLE + "='" + title + "'",
                null, null);
        if (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID));
        }
        cursor.close();
        return id;
    }

    /**
     * 根据组的id删除组
     *
     * @return int
     */
    public int delGroupById(String selection, String[] ids) {
        Uri uri = Uri.parse(ContactsContract.Groups.CONTENT_URI + "?" + ContactsContract.CALLER_IS_SYNCADAPTER + "=true");
        int i = resolver.delete(
                uri,
                ContactsContract.Groups._ID + selection,
                ids);
        return i;
    }

    @Override
    public boolean save(ContactGroup entity) {
        if (TextUtils.isEmpty(entity.getName())) {
            return false;
        }
        long gId = getGroupByTitle(entity.getName());
        if (gId == -1) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.Groups.TITLE, entity.getName());
            Uri uri = mContext.getContentResolver().insert(ContactsContract.Groups.CONTENT_URI, values);
            gId = ContentUris.parseId(uri);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(ContactGroup entity) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Groups.CONTENT_URI, entity.getId());
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Groups.TITLE, entity.getName());
        mContext.getContentResolver().update(uri, values, null, null);
        return true;
    }

    @Override
    public boolean delete(ContactGroup entity) {
        Uri uri = Uri.parse(ContactsContract.Groups.CONTENT_URI + "?" + ContactsContract.CALLER_IS_SYNCADAPTER + "=true");
        int i = mContext.getContentResolver().delete(
                uri,
                ContactsContract.Groups._ID + "=" + entity.getId(),
                null);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ContactGroup> findAll() {
        List<ContactGroup> groups = new ArrayList<ContactGroup>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                ContactGroup contactGroup = new ContactGroup();
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID));
                String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
                contactGroup.setId(id);
                contactGroup.setName(title);
                groups.add(contactGroup);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return groups;
    }
}
