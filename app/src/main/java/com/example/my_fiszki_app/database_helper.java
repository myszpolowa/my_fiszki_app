package com.example.my_fiszki_app;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class database_helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fiszki.db";
    private static final int DATABASE_VERSION = 1;
    private Context myContext;
    private String databasePath;

    public database_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        this.databasePath = getDatabasePath(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            myContext.deleteDatabase(DATABASE_NAME);
            try {
                createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDatabase() throws IOException {
        InputStream is = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = databasePath + DATABASE_NAME;

        File dbDir = new File(databasePath);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        try (OutputStream os = new FileOutputStream(outFileName)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        }
        is.close();
    }

    private static String getDatabasePath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getApplicationInfo().dataDir + "/databases/";
        } else {
            return "/data/data/" + context.getPackageName() + "/databases/";
        }
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new IOException("error", e);
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(databasePath + DATABASE_NAME);
        return dbFile.exists();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String myPath = databasePath + DATABASE_NAME;
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}