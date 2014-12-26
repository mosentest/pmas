package org.mo.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.mo.pmas.table.*;

/**
 * //TODO 数据库文档
 * http://www.w3cschool.cc/sqlite/sqlite-constraints.html
 * SQLite数据库的帮助类
 * 该类属于扩展类,主要承担数据库初始化和版本升级使用,其他核心全由核心父类完成
 * Created by moziqi on 2014/12/25.
 */
public class DataBaseHelper extends SDCardSQLiteOpenHelper {
    /**
     * @param context
     * @param name    //TODO 数据库名字
     * @param factory
     * @param version
     */
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    private final static String CREATE_TABLE_ONE = "CREATE TABLE " + ContactGroupTable.TABLE_NAME + "(" +
            ContactGroupTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            ContactGroupTable.NAME + " TEXT NOT NULL UNIQUE," +
            ContactGroupTable.DESCRIPTION + " CHAR(16)" +
            ");";
    private final static String CREATE_TABLE_TWO = "CREATE TABLE " + ContactTable.TABLE_NAME + "(" +
            ContactTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            ContactTable.CG_ID + " INTEGER," +
            ContactTable.NAME + " TEXT," +
            ContactTable.BIRTHDAY + " TEXT," +
            ContactTable.PHONE_ONE + " TEXT," +
            ContactTable.PHONE_TWO + " TEXT," +
            ContactTable.PHONE_THREE + " TEXT," +
            ContactTable.EMAIL + " TEXT," +
            ContactTable.ADDRESS + " VARCHAR(30)," +
            "FOREIGN KEY(" + ContactTable.CG_ID + ") REFERENCES " + ContactGroupTable.TABLE_NAME + "(" + ContactGroupTable.ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
            ");";
    private final static String CREATE_TABLE_THREE = "CREATE TABLE " + NoteGroupTable.TABLE_NAME + "(" +
            NoteGroupTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            NoteGroupTable.NAME + " TEXT NOT NULL UNIQUE," +
            NoteGroupTable.DESCRIPTION + " CHAR(16)" +
            ");";
    private final static String CREATE_TABLE_FOUR = "CREATE TABLE " + NoteTable.TABLE_NAME + "(" +
            NoteTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            NoteTable.NG_ID + " INTEGER," +
            NoteTable.TITLE + " TEXT," +
            NoteTable.CREATE_DATE + " TEXT," +
            NoteTable.CONTENT + " VARCHAR(128)," +
            "FOREIGN KEY(" + NoteTable.NG_ID + ") REFERENCES " + NoteGroupTable.TABLE_NAME + "(" + NoteGroupTable.ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
            ");";
    private final static String CREATE_TABLE_FIVE = "CREATE TABLE " + ScheduleGroupTable.TABLE_NAME + "(" +
            ScheduleGroupTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            ScheduleGroupTable.NAME + " TEXT NOT NULL UNIQUE," +
            ScheduleGroupTable.DESCRIPTION + " CHAR(16)" +
            ");";
    private final static String CREATE_TABLE_SIX = "CREATE TABLE " + ScheduleTable.TABLE_NAME + "(" +
            ScheduleTable.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            ScheduleTable.SG_ID + " INTEGER," +
            ScheduleTable.TITLE + " TEXT," +
            ScheduleTable.REMIND_DATE + " TEXT," +
            ScheduleTable.CONTENT + " VARCHAR(128)," +
            "FOREIGN KEY(" + ScheduleTable.SG_ID + ") REFERENCES " + ScheduleGroupTable.TABLE_NAME + "(" + ScheduleGroupTable.ID + ") ON DELETE SET NULL ON UPDATE CASCADE" +
            ");";
    private final static String INSERT_CONTACT_GROUP = "INSERT INTO tb_contact_group(name,description) VALUES('默认','无')";
    private final static String INSERT_NOTE_GROUP = "INSERT INTO tb_note_group(name,description) VALUES('默认','无')";
    private final static String INSERT_SCHEDULE_GROUP = "INSERT INTO tb_schedule_group(name,description) VALUES('默认','无')";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO 写创表sql语句
        db.execSQL(CREATE_TABLE_ONE);
        db.execSQL(CREATE_TABLE_TWO);
        db.execSQL(CREATE_TABLE_THREE);
        db.execSQL(CREATE_TABLE_FOUR);
        db.execSQL(CREATE_TABLE_FIVE);
        db.execSQL(CREATE_TABLE_SIX);
        //预处理插入一些数据库
        db.execSQL(INSERT_CONTACT_GROUP);
        db.execSQL(INSERT_NOTE_GROUP);
        db.execSQL(INSERT_SCHEDULE_GROUP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
