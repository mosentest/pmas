package org.mo.pmas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import org.mo.common.db.DBManager;
import org.mo.common.db.SQLiteTemplate;
import org.mo.common.util.StringUtil;
import org.mo.pmas.comm.Constant;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.table.ContactTable;

/**
 * Created by moziqi on 2014/12/25 0025.
 */
public class ContactRepository {

    private static ContactRepository contactRepository = null;

    private static DBManager dbManager = null;

    private ContactRepository(Context context) {
        dbManager = DBManager.getInstance(context, Constant.DBNAME);
    }

    public static ContactRepository getInstance(Context context) {
        if (contactRepository == null) {
            contactRepository = new ContactRepository(context);
        }
        return contactRepository;
    }

    public long save(Contact contact) {
        SQLiteTemplate sqLiteTemplate = SQLiteTemplate.getInstance(dbManager, false);
        ContentValues contentValues = new ContentValues();
        if (StringUtil.notEmpty(contact.getName())) {
            contentValues.put(ContactTable.NAME, StringUtil.doEmpty(contact.getName()));
        }
        if (StringUtil.notEmpty(contact.getEmail())) {
            contentValues.put(ContactTable.EMAIL, StringUtil.doEmpty(contact.getEmail()));
        }
        if (StringUtil.notEmpty(contact.getAddress())) {
            contentValues.put(ContactTable.ADDRESS, StringUtil.doEmpty(contact.getAddress()));
        }
//        contentValues.put(ContactTable.BIRTHDAY, contact.getBirthday());
        return sqLiteTemplate.insert(ContactTable.TABLE_NAME, contentValues);
    }
}
