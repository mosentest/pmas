package org.mo.taskmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.mo.pmas.comm.Constant;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends OrmLiteSqliteOpenHelper {


    private final static String TAG = DBHelper.class.getSimpleName();

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, Constant.MyDB.DATABASE_NAME, null, Constant.MyDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        sqLiteDatabase.execSQL("CREATE TABLE mytasks (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content VARCHAR(200)," +
                "cycle INTEGER," +
                "date VARCHAR(20)," +
                "startTime VARCHAR(20)," +
                "endTime VARCHAR(20)," +
                "reminder INTEGER," +
                "reminderdate VARCHAR(20)," +
                "type INTEGER," +
                "time INTEGER," +
                "expiredate VARCHAR(20)," +
                "updatetime DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP,'localtime')))");
        sqLiteDatabase.execSQL("CREATE TABLE taskscycle (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cycledate VARCHAR(20)," +
                "cyclestatus VARCHAR(1)," +
                "updatetime DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP,'localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null)
                    instance = new DBHelper(context);
            }
        }
        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
