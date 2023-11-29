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
import java.util.HashMap;

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
                .append(" DATETIME DEFAULT ").append(LocalDateTime.now()).append(", ").append(SCORE_FIELD).append(" INT)").append(DURATION_FIELD)
                .append(" INT)");

        db.execSQL(sql.toString());
        String sql2 = "INSERT INTO " + TABLE_NAME + "(" + SCORE_FIELD + ", " +
                DURATION_FIELD + ") VALUES (" + null + ", " + null + ");" ;
        db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void  setNewTopScore(String topScore, Duration topTime, int difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DURATION_FIELD, topTime.toMillis());
        contentValues.put(SCORE_FIELD, topScore);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void getTopScores(){
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM  " + TABLE_NAME , null))
        {
            if (result.getCount() != 0)
            {
                while(result.moveToNext()){
                    int score = result.getInt(1);
                    LocalDateTime date = LocalDateTime.parse(result.getString(2));
                    Duration duration = Duration.ofMillis(result.getInt(3));
                }
            }
        }
    }

    public void updateTopScore(String topScore, Duration topTime, int difficulty){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DURATION_FIELD, topTime.toMillis());
        contentValues.put(SCORE_FIELD, topScore);
        contentValues.put(DATE_FIELD, String.valueOf(LocalDateTime.now()));

        db.update(TABLE_NAME, contentValues, "pk =? ", new String[]{String.valueOf(difficulty-1)});
    }
}
