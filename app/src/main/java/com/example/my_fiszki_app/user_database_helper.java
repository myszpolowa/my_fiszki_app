package com.example.my_fiszki_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class user_database_helper {
        private database_helper db_helper;
        private SQLiteDatabase database;
    private String username;
    private String password;
    private Integer progress;

    public user_database_helper(Context context) {
            db_helper = new database_helper(context);
        }

        public void open() {
            try {
                db_helper.createDataBase();
                database = db_helper.openDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void close() {
            if (database != null) {
                database.close();
            }
        }

        public boolean checkUserExists(String username) {
            if (database == null || !database.isOpen()) {
                return false;
            }

            String[] columns = {"login"};
            String selection = "login = ?";
            String[] selectionArgs = {username};

            Cursor cursor = database.query("logins", columns, selection, selectionArgs, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

        public boolean validateUser(String username, String password) {
            if (database == null || !database.isOpen()) {
                return false;
            }

            String[] columns = {"login"};
            String selection = "login = ? AND password = ?";
            String[] selectionArgs = {username, password};

            Cursor cursor = database.query("logins", columns, selection, selectionArgs, null, null, null);
            boolean isValid = cursor.getCount() > 0;
            cursor.close();
            return isValid;
        }


        public user getUser(String username) {
            if (database == null || !database.isOpen()) {
                return null;
            }

            String[] columns = {"user_id", "login", "password", "progress"};
            String selection = "login = ?";
            String[] selectionArgs = {username};

            Cursor cursor = database.query("logins", columns, selection, selectionArgs, null, null, null);

            user user = null;
            if (cursor.moveToFirst()) {
                user = new user(
                        cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("login")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("progress"))
                );
            }
            cursor.close();
            return user;
        }

    public boolean resetPassword(String login, String newPassword) {
        if (database == null || !database.isOpen()) {
            return false;
        }

        try {
            ContentValues values = new ContentValues();
            values.put("password", newPassword);

            String whereClause = "login = ?";
            String[] whereArgs = {login};

            int rowsAffected = database.update("logins", values, whereClause, whereArgs);
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String password, Integer progress) {
        if (database == null || !database.isOpen()) {
            return false;
        }

        try {
            ContentValues values = new ContentValues();
            values.put("login", username);
            values.put("password", password);
            values.put("0", progress);

            long result = database.insert("logins", null, values);
            return result != -1; // -1 - error
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
