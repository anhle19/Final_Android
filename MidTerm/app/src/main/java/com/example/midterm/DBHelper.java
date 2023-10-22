package com.example.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Lớp này tương tác với CSDL
 * Tạo CSDL
 * Tạo bảng
 * */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FinalDatabase";
    private static final int DATABASE_VERSION = 1;

    //Tên bảng và các cột

    //Bảng Players
    public static final String TABLE_PLAYERS = "Players";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_TOTAL_SCORE = "totalScore";
    public static final String COLUMN_TOTAL_PLAY = "totalPlay";
    public static final String COLUMN_PHONE = "phone";
    public static final String TABLE_SCORES = "Scores";
    public static final String COLUMN_GAME_SCORE = "gameScore";
    public static final String COLUMN_PLAYER_ID = "playerId";
    public static final String COLUMN_GAME_ID = "gameId";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng Players
        String createPlayesTable = "CREATE TABLE " + TABLE_PLAYERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " + COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_TOTAL_SCORE + " INTEGER, " + COLUMN_TOTAL_PLAY + " INTEGER, " + COLUMN_PHONE + " TEXT NOT NULL)";
        db.execSQL(createPlayesTable);

        //Tạo bảng Scores
        String createScoresTable = "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAYER_ID + " INTEGER NOT NULL, " + COLUMN_GAME_SCORE + " INTEGER NOT NULL, " +
                COLUMN_GAME_ID + " INTEGER NOT NULL)";
        db.execSQL(createScoresTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
    }
}
