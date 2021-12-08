package com.example.flag;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

public class DBAdapter {
    public static final String rowID = "id";
    public static final String flagName = "name";
    public static final String rate = "rate";
    public static final String comment = "comment";
    public static final String TAG = "DBAdapter";
    public static final String DATABASE_NAME = "MyDB";
    public static final String DATABASE_TABLE = "Rating";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table Rating(id integer primary key autoincrement," +
            "name text not null,rate float not null,comment text not null)";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }


        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {

                e.printStackTrace();

            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrade database from version " + oldVersion + " to "

                    + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS Rating");

            onCreate(db);

        }//end method onUpgrade

    }
//open the database

    public DBAdapter open() throws SQLException {

        db = DBHelper.getWritableDatabase();

        return this;

    }



    //close the database


    public void close() {

        DBHelper.close();

    }


//insert a contact into the database

    public long insertRate(String name, String com, float rateScore) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(flagName, name);
        initialValues.put(rate, rateScore);
        initialValues.put(comment, com);

        return db.insert(DATABASE_TABLE, null, initialValues);

    }


//delete a particular contact

    public boolean deleteRate(long rowId) {

        return db.delete(DATABASE_TABLE, rowID + "=" + rowId, null) > 0;

    }


//retrieve all the contacts

    public Cursor getAllRate() {

        return db.query(DATABASE_TABLE, new String[]{rowID, flagName,

                rate, comment}, null, null, null, null, null);

    }


//retrieve a single contact

    public Cursor getRate(long rowId) throws SQLException {

        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{rowID, flagName,

                rate, comment}, rowID + "=" + rowId, null, null, null, null, null);

        if (mCursor != null) {

            mCursor.moveToFirst();

        }

        return mCursor;

    }


//updates a contact

    public boolean updateRate(long rowId, String name, String com, float rateScore) {

        ContentValues cval = new ContentValues();

        cval.put(flagName, name);
        cval.put(comment, com);
        cval.put(rate, rateScore);


        return db.update(DATABASE_TABLE, cval, rowID + "=" + rowId, null) > 0;

    }
}

