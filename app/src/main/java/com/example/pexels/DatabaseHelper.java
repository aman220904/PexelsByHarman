package com.example.pexels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "photos_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "photos";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_IMAGE_URL = "image_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table SQL query
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_IMAGE_URL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add photo to the database
    public void addPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, photo.getTitle());
        values.put(COL_IMAGE_URL, photo.getImageUrl());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Retrieve all photos from the database
    public List<Photo> getAllPhotos() {
        List<Photo> photos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database for all rows
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            // Check if the columns exist before trying to fetch them
            int titleIndex = cursor.getColumnIndex(COL_TITLE);
            int imageUrlIndex = cursor.getColumnIndex(COL_IMAGE_URL);

            // Log column indices for debugging
            Log.d("DatabaseHelper", "Title Column Index: " + titleIndex);
            Log.d("DatabaseHelper", "Image URL Column Index: " + imageUrlIndex);

            // Check if column indices are valid (>= 0)
            if (titleIndex != -1 && imageUrlIndex != -1) {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(titleIndex);
                    String url = cursor.getString(imageUrlIndex);
                    photos.add(new Photo(title, url));
                }
            } else {
                Log.e("DatabaseHelper", "Column names are incorrect or missing in the database.");
            }
            cursor.close();
        } else {
            Log.e("DatabaseHelper", "Cursor is null.");
        }
        return photos;
    }

}

