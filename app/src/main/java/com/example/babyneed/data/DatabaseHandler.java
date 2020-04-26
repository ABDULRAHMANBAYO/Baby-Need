package com.example.babyneed.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.babyneed.utils.Constant;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    //Write SQL Statement to create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create baby item table
        String CREATE_BABY_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY," +
                Constant.KEY_ITEM_NAME + " INTEGER," +
                Constant.KEY_ITEM_COLOR + " TEXT," +
                Constant.KEY_ITEM_QUANTITY + " INTEGER," +
                Constant.KEY_ITEM_SIZE + " INTEGER," +
                Constant.KEY_ITEM_DATE_ADDED + " LONG" + ")";
        db.execSQL(CREATE_BABY_TABLE);


    }


    // Upgrading database  Override
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }
}
