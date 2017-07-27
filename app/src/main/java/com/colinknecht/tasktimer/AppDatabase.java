package com.colinknecht.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by colinknecht on 7/26/17.
 * Basic database class for the application
 *
 * The only class that should use this is {@link AppProvider}.
 */

class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    public static final String DATABSE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION = 1;

    //Implement AppDatabase as a Singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context){
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "==========================AppDatabase: constructor called");
    }

    /**
     * Get and Instance of the app's singleton database helper object
     *
     * @param context the content providers context.
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context) {
        if (instance == null){
            Log.d(TAG, "================getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "==========================onCreate: starts");
        String sSQL; //Use a string variable to facilitate logging
//        sSQL = "CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER, CategoryID INTEGER);";
        sSQL = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
                + TaskContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + TaskContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
                + TaskContract.Columns.TASKS_DESCRIPTION + " TEXT, "
                + TaskContract.Columns.TASKS_SORT_ORDER + " INTEGER);";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "==========================onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "=============onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                //upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown new version: " + newVersion);

        }
        Log.d(TAG, "==================onUpgrade: ends");
    }
}
