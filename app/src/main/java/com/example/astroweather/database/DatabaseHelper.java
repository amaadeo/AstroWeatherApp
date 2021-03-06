package com.example.astroweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Weather";
    private static final String TABLE_NAME = "City";
    private static final int FAIL_CODE = -1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CITY", city);

        if ((db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)) == FAIL_CODE) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteData(String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete(TABLE_NAME, "CITY=?", new String[]{city}) == FAIL_CODE) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return data;
    }

    public ArrayList<String> citites() {
        ArrayList<String> array = new ArrayList<>();

        Cursor data = getListContents();
        array.clear();
        while (data.moveToNext()) {
            array.add(data.getString(1));
        }

        return array;
    }
}
