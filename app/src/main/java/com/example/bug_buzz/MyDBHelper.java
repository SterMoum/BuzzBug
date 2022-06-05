package com.example.bug_buzz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Main.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Players";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "player_username";
    private static final String COLUMN_HIGHSCORE = "player_highscore";


    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_HIGHSCORE + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void addPlayer(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_HIGHSCORE, 0);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to add player", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show();
        }
    }
    boolean deletePlayer(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COLUMN_USERNAME + "=?",new String[]{username}) > 0;
    }

    void updatePlayerScore(String username, String newScore){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_HIGHSCORE + " FROM " + TABLE_NAME + " where instr(" + COLUMN_USERNAME + ", ?)";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        String currentPlayerScore = "";
        if (db != null){
            cursor.moveToFirst();
            currentPlayerScore = cursor.getString(0);
            ContentValues cv = new ContentValues();
            if(Integer.parseInt(newScore) > Integer.parseInt(currentPlayerScore)){
                cv.put(COLUMN_HIGHSCORE, newScore);
                db.update(TABLE_NAME,cv, COLUMN_USERNAME + "=?", new String[]{username});
            }
        }

    }



}
