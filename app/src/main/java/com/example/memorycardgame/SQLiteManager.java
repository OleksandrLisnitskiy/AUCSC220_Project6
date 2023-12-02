package com.example.memorycardgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "ScoreDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ScoreDB";
    private static final String COUNTER = "ScoreDB";

    private static final String SCORE_FIELD = "Score";
    private static final String DATE_FIELD = "Date";
    private static final String DURATION_FIELD = "Duration";
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null){
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder().append("CREATE TABLE ").append(TABLE_NAME).append("(")
                .append(COUNTER).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(DATE_FIELD)
                .append(" DATETIME DEFAULT CURRENT_TIMESTAMP").append(", ").append(SCORE_FIELD).append(" INT,").append(DURATION_FIELD)
                .append(" INT)");

        db.execSQL(sql.toString());
        for (int i = 0; i < 3; i++) {
            String sql2 = "INSERT INTO " + TABLE_NAME + "(" + SCORE_FIELD + ", " +
                    DURATION_FIELD + ") VALUES (" + 0 + ", " + 0 + ");" ;
            db.execSQL(sql2);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Object> getTopScores(int difficulty){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Object> res = Arrays.asList(0, 0, LocalDateTime.now());
        try (Cursor result = db.rawQuery("SELECT * FROM  " + TABLE_NAME + " WHERE " + COUNTER + " = " + difficulty, null))
        {
            if (result.getCount() != 0)
            {
                System.out.println(result.getColumnCount());
                    res.set(0, result.getString(2));
                    res.set(2, result.getInt(4));
                    res.set(1, LocalDateTime.parse(result.getString(3)));


                }
            }
        return res;
        }


    public void updateTopScore(String topScore, long topTime, int difficulty){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DURATION_FIELD, topTime);
        contentValues.put(SCORE_FIELD, topScore);
        contentValues.put(DATE_FIELD, String.valueOf(LocalDateTime.now()));

        db.update(TABLE_NAME, contentValues, "pk =? ", new String[]{String.valueOf(difficulty-1)});
    }
}
