package com.example.my_fiszki_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FiszkiDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fiszki.db";
    private static final int DATABASE_VERSION = 1;

    public FiszkiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // NIC nie twórz! Baza już istnieje, tylko podłączamy!
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // NIE modyfikuj bazy
    }
}