package com.example.w1581003.coursework2;

import static android.provider.BaseColumns._ID;
import static com.example.w1581003.coursework2.Events.TABLE_NAME;
import static com.example.w1581003.coursework2.Events.TIME;
import static com.example.w1581003.coursework2.Events.TITLE;
import static com.example.w1581003.coursework2.Events.DESC;
import static com.example.w1581003.coursework2.Events.DATE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Please note that this class, as well as the events interface, are based on code supplied by the textbook
public class EData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events.db";
    private static int DATABASE_VERSION = 1;

    public EData(Context ctx){

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + TIME + " TEXT, " + DATE + " TEXT, " + DESC + " TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
