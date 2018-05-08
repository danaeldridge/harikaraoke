package com.danaeldridge.recyclerviewclickplayground.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper{
    public static final String LOG_TAG = DBController.class.getSimpleName();
    private static final String DATABASE_NAME = "Harikaraoke.db";
    private static final int DATABASE_VERSION = 1;

    public DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SONGS_TABLE = "CREATE TABLE " + SongContract.SongEntry.TABLE_NAME + " ("
                + SongContract.SongEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SongContract.SongEntry.COLUMN_SONG_TITLE + " TEXT NOT NULL, "
                + SongContract.SongEntry.COLUMN_SONG_ARTIST + " TEXT NOT NULL, "
                + SongContract.SongEntry.COLUMN_SONG_FAVORITE + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //placeholder
    }
}
