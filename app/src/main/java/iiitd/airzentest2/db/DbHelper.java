package iiitd.airzentest2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.DefectPreferences.CREATE_TABLE);
        db.execSQL(DbContract.Past24Hours.CREATE_TABLE);
        db.execSQL(DbContract.PastWeek.CREATE_TABLE);
        db.execSQL(DbContract.PastMonth.CREATE_TABLE);
        db.execSQL(DbContract.PastYear.CREATE_TABLE);
        Log.d("asasasa", "asasa");
        db.execSQL(DbContract.Aqi.CREATE_TABLE);
        Log.d("asasasa", "asasa");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbContract.Aqi.DELETE_TABLE);
        db.execSQL(DbContract.DefectPreferences.DELETE_TABLE);
        db.execSQL(DbContract.Past24Hours.DELETE_TABLE);
        db.execSQL(DbContract.PastWeek.DELETE_TABLE);
        db.execSQL(DbContract.PastMonth.DELETE_TABLE);
        db.execSQL(DbContract.PastYear.DELETE_TABLE);
        onCreate(db);
    }
}
