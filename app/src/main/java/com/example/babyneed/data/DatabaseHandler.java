package com.example.babyneed.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.os.Build;
import android.util.Log;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.babyneed.model.BabyItem;
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


    // Upgrading database  OverrideBabyItem
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    //CRUD operation

    //Add baby item to database
    public void addBabyItem(BabyItem babyItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_ITEM_NAME, babyItem.getItemName());//Insert baby item name
        values.put(Constant.KEY_ITEM_COLOR, babyItem.getItemColor());//Insert baby item color
        values.put(Constant.KEY_ITEM_QUANTITY, babyItem.getItemQuantity());//Insert item quantity
        values.put(Constant.KEY_ITEM_SIZE, babyItem.getItemSize());//Insert item size
        values.put(Constant.KEY_ITEM_DATE_ADDED, java.lang.System.currentTimeMillis());//Insert time stamp of the system

        // Inserting Row
        db.insert(Constant.TABLE_NAME, null, values);

        Log.d("Added", "Values Added to DB");
        // Closing database connection
        db.close();
    }

    //Get a single baby item
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BabyItem getBabyItem(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(Constant.TABLE_NAME, new String[]
                {
                        Constant.KEY_ID,
                        Constant.KEY_ITEM_NAME,
                        Constant.KEY_ITEM_COLOR,
                        Constant.KEY_ITEM_DATE_ADDED,
                        Constant.KEY_ITEM_SIZE,
                        Constant.KEY_ITEM_QUANTITY

                }, Constant.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BabyItem babyItem = new BabyItem();
        if (cursor != null) {
            babyItem.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
            babyItem.setItemName(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_NAME)));
            babyItem.setItemColor(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_COLOR)));
            babyItem.setItemSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_SIZE))));
            babyItem.setItemQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_QUANTITY))));

            //Convert timestamp to date
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_ITEM_DATE_ADDED))).getTime());
            babyItem.setDateItemAdded(String.valueOf(formattedDate));
        }
        // return babyItem
        return babyItem;


    }

    //Get all baby items
    public List<BabyItem> getAllBabyItems() {
        SQLiteDatabase db = getReadableDatabase();

        List<BabyItem> babyItemList = new ArrayList<>();

        Cursor cursor = db.query(Constant.TABLE_NAME, new String[]
                {
                        Constant.KEY_ID,
                        Constant.KEY_ITEM_NAME,
                        Constant.KEY_ITEM_COLOR,
                        Constant.KEY_ITEM_DATE_ADDED,
                        Constant.KEY_ITEM_SIZE,
                        Constant.KEY_ITEM_QUANTITY

                }, null, null, null, null, Constant.KEY_ITEM_DATE_ADDED + " DESC");

        if (cursor.moveToFirst()) {
            do {
                BabyItem babyItem = new BabyItem();
                babyItem.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
                babyItem.setItemName(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_NAME)));
                babyItem.setItemColor(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_COLOR)));
                babyItem.setItemSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_SIZE))));
                babyItem.setItemQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ITEM_QUANTITY))));
                //Convert timestamp to date
                Timestamp ts = new Timestamp(cursor.getColumnIndex(Constant.KEY_ITEM_DATE_ADDED));
                Date date = new Date(ts.getTime());
                babyItem.setDateItemAdded(String.valueOf(date));

                babyItemList.add(babyItem);


            }
            while (cursor.moveToNext());

        }
        return babyItemList;
    }

    //Update baby item
    public int updateBabyItem(BabyItem babyItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_ITEM_NAME, babyItem.getItemName());//Insert baby item name
        values.put(Constant.KEY_ITEM_COLOR, babyItem.getItemColor());//Insert baby item color
        values.put(Constant.KEY_ITEM_QUANTITY, babyItem.getItemQuantity());//Insert item quantity
        values.put(Constant.KEY_ITEM_SIZE, babyItem.getItemSize());//Insert item size
        values.put(Constant.KEY_ITEM_DATE_ADDED, java.lang.System.currentTimeMillis());//Insert time stamp of the system

        return db.update(Constant.TABLE_NAME, values, Constant.KEY_ID + "=?", new String[]{String.valueOf(babyItem.getId())});
    }

    //Delete a baby item
    public void deleteBabyItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constant.TABLE_NAME,
                Constant.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        //close db
        db.close();


    }

    //Count  number of baby item
    public int numberOfBabyItem() {
        String countBabyItem = "SELECT * FROM " + Constant.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countBabyItem, null);
        cursor.close();
        return cursor.getCount();
    }

}
