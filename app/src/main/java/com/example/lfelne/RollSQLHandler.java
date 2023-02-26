package com.example.lfelne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class RollSQLHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "lfelnedb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "rolls";
    private static final String NAME_COL = "roll_name";
    private static final String TYPE_COL = "roll_type";
    private static final String MAX_EXPOSURES_COL = "max_exposures";
    private static final String EXPOSURES_COL = "exposures";
    private static final String START_DATE_COL = "start_date";
    private static final String END_DATE_COL = "end_date";
    private static final String IMAGE_URIS_COL = "image_uris";


    public RollSQLHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT,"
                + TYPE_COL + " TEXT,"
                + MAX_EXPOSURES_COL + " SMALLINT,"
                + EXPOSURES_COL + " SMALLINT,"
                + START_DATE_COL + " BIGINT,"
                + END_DATE_COL + " BIGINT,"
                + IMAGE_URIS_COL + " TEXT)";
        db.execSQL(query);
    }

    public void addNewRoll(Roll roll) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, roll.getNAME());
        values.put(TYPE_COL, roll.getROLL_TYPE());
        values.put(MAX_EXPOSURES_COL, roll.getMAX_EXPOSURES());
        values.put(EXPOSURES_COL, roll.getExposures());
        values.put(START_DATE_COL, roll.getSTART_DATE());
        values.put(END_DATE_COL, roll.getEnd_date());
        values.put(IMAGE_URIS_COL, "");

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Roll> collectRolls() {
        ArrayList<Roll> returnList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // Iterate through database
        while (!cursor.isAfterLast()) {
            String roll_init = cursor.getString(0) + "\n"
                    + cursor.getString(1) + "\n"
                    + cursor.getInt(2) + "\n"
                    + cursor.getInt(3) + "\n"
                    + cursor.getLong(4) + "\n"
                    + cursor.getLong(5);
            Roll roll = new Roll(roll_init);
            returnList.add(roll);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<Roll> collectNonFullRolls() {
        ArrayList<Roll> returnList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE exposures < max_exposures";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // Iterate through database
        while (!cursor.isAfterLast()) {
            String roll_init = cursor.getString(0) + "\n"
                    + cursor.getString(1) + "\n"
                    + cursor.getInt(2) + "\n"
                    + cursor.getInt(3) + "\n"
                    + cursor.getLong(4) + "\n"
                    + cursor.getLong(5);
            Roll roll = new Roll(roll_init);
            returnList.add(roll);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public String getImageUris(long start_date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = generateCursor(db, start_date);
        cursor.moveToFirst();
        String uris = cursor.getString(6);
        cursor.close();
        db.close();
        return uris;
    }

    public void incrementExposures(long start_date, short old_exposures) {
        SQLiteDatabase db = this.getWritableDatabase();
        short new_exposures = (short) (old_exposures + 1);
        String updateQuery = "UPDATE " + TABLE_NAME + " SET exposures = " +
                String.valueOf(new_exposures) +
                " WHERE start_date = " + start_date;
        db.execSQL(updateQuery);
        db.close();

        addBlankImage(start_date);
    }

    public void addBlankImage(long start_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        long date = new Date().getTime(); // get current date
        String init_string = "'n%" + date + ",'";
        String updateQuery = "UPDATE " + TABLE_NAME + " SET image_uris = image_uris || "
                + init_string + " WHERE start_date = " + start_date;
        db.execSQL(updateQuery);
        db.close();
    }

    public void removeRoll(long start_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "start_date=?", new String[]{String.valueOf(start_date)});
        db.close();
    }

    public Cursor generateCursor(SQLiteDatabase db, long start_date) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE start_date = " + start_date;
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
