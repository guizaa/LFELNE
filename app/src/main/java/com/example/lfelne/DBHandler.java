package com.example.lfelne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "lfelnedb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "rolls";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "roll_name";
    private static final String TYPE_COL = "roll_type";
    private static final String MAX_EXPOSURES_COL = "max_exposures";
    private static final String EXPOSURES_COL = "exposures";
    private static final String START_DATE_COL = "start_date";
    private static final String END_DATE_COL = "end_date";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TYPE_COL + " TEXT,"
                + MAX_EXPOSURES_COL + " TEXT,"
                + EXPOSURES_COL + " TEXT,"
                + START_DATE_COL + " TEXT,"
                + END_DATE_COL + " TEXT)";
        db.execSQL(query);
    }

    public void addNewRoll(Roll roll) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, roll.getNAME());
        values.put(TYPE_COL, roll.getROLL_TYPE());
        values.put(MAX_EXPOSURES_COL, roll.getMAX_EXPOSURES());
        values.put(EXPOSURES_COL, roll.getExposures());
        values.put(START_DATE_COL, roll.getSTART_DATE().getTime());
        values.put(END_DATE_COL, roll.getEnd_date().getTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Pair<ArrayList<Roll>, Integer> collectRolls() {
        ArrayList<Roll> returnList = new ArrayList<Roll>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        int index;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // Iterate through database
        while (!cursor.isAfterLast()) {
            String roll_init = cursor.getString(1) + "-"
                    + cursor.getString(2) + "-"
                    + cursor.getInt(3) + "-"
                    + cursor.getInt(4) + "-"
                    + cursor.getLong(5) + "-"
                    + cursor.getLong(6);
            Roll roll = new Roll(roll_init);
            returnList.add(roll);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        index = returnList.size() - 1;
        return new Pair<>(returnList, index);
    }

    public int getLastIndex() {
        int returnInt = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToLast())
            returnInt = cursor.getPosition();
        cursor.close();
        db.close();

        return returnInt;
    }

    public Roll getRoll(int index) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToPosition(index);

        String roll_init = cursor.getString(1) + "-"
                + cursor.getString(2) + "-"
                + cursor.getInt(3) + "-"
                + cursor.getInt(4) + "-"
                + cursor.getLong(5) + "-"
                + cursor.getLong(6);
        Roll roll = new Roll(roll_init);

        cursor.close();
        db.close();
        return roll;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
