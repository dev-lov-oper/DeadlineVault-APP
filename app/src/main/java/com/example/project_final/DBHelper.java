package com.example.project_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "DeadlineDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE deadlines (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "subject TEXT," +
                "due_date TEXT," +
                "priority TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS deadlines");
        onCreate(db);
    }

    public void insertDeadline(Deadline deadline) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", deadline.getTitle());
        values.put("subject", deadline.getSubject());
        values.put("due_date", deadline.getDueDate());
        values.put("priority", deadline.getPriority());

        db.insert("deadlines", null, values);
        db.close();
    }


    public List<Deadline> getAllDeadlines() {

        List<Deadline> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM deadlines ORDER BY due_date ASC", null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String subject = cursor.getString(2);
                String date = cursor.getString(3);
                String priority = cursor.getString(4);

                list.add(new Deadline(title, subject, date, priority));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    // Delete deadline by title (simple version)
    public void deleteDeadline(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("deadlines", "title=?", new String[]{title});
    }
}