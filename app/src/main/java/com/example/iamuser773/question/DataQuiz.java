package com.example.iamuser773.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IamUser773 on 19/8/2558.
 */
public class DataQuiz extends SQLiteOpenHelper {
    private static final String SQL_NAME = "Quiz";
    private static final int SQL_VERSION = 1;

    public static final String TABLE_NAME = "MyQuestion";
    public static final String COL_QUIZ = "Question";
    public static final String COL_A = "A";
    public static final String COL_B = "B";
    public static final String COL_C = "C";
    public static final String COL_D = "D";
    public static final String COL_ANSWERS = "ANSWERS";


    public DataQuiz(Context context) {
        super(context, SQL_NAME, null, SQL_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_QUIZ + " TEXT, " + COL_A + " TEXT, "
                + COL_B + " TEXT, " + COL_C + " TEXT, "
                + COL_D + " TEXT, " + COL_ANSWERS + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}

