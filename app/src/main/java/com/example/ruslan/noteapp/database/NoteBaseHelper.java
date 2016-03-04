package com.example.ruslan.noteapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ruslan.noteapp.database.NoteDbSchema.NoteTable;

/**
 * Created by rusla on 10.02.2016.
 */
public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "noteBase.db";

    public NoteBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NoteTable.NAME + " (" +
                " _id integer primary key autoincrement, "
                + NoteTable.Cols.UUID + ", " + NoteTable.Cols.TITLE + ", " +
        NoteTable.Cols.DATE + ", " + NoteTable.Cols.SOLVED + ", " + NoteTable.Cols.PARTNER + ", " +
                NoteTable.Cols.PARTNER_ID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Update from version " + oldVersion + "to version " + newVersion );

        db.execSQL("DROP TABLE IF IT EXISTS " + NoteTable.NAME);
        onCreate(db);
    }
}
