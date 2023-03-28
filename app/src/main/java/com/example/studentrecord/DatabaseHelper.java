package com.example.studentrecord;

import static android.os.Build.ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "Mystudent.db";
     public final static String TABLE_NAME = "MyStudent_Table";
   public final static String Col_1 = "ID";
     public  final static String Col_2 = "NAME";
    public final static String Col_3 = "EMAIL";
    public final static String Col_4 = "COURSE_COUNT";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "
        + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "NAME TEXT , " +
               "EMAIL TEXT , "+
              "COURSE_COUNT INTEGER) " );
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
        public boolean insertData(String name,String email,String courseCount) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_2, name);
            contentValues.put(Col_3, email);
            contentValues.put(Col_4, courseCount);

            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
           public boolean updateData(String ID,String name,String email,String courseCount) {
             SQLiteDatabase     db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(Col_1, ID);
                contentValues.put(Col_2, name);
                contentValues.put(Col_3, email);
                contentValues.put(Col_4, courseCount);

                db.update(TABLE_NAME,contentValues,"ID=?",new String[]{ID});
                    return true;
                }

          public Cursor getData(String ID){
                SQLiteDatabase db = this.getWritableDatabase();
            String query = " SELECT * FROM "+TABLE_NAME+
                   " WHERE ID = " + ID + "";

            Cursor cursor = db.rawQuery(query,null);
            return  cursor;
          }

         public Integer deleteData(String ID){
               SQLiteDatabase db = this.getWritableDatabase();
              return db.delete(TABLE_NAME ," ID=? " ,new String[]{ID});
            }
          public  Cursor getAllData(){
               SQLiteDatabase db = this.getWritableDatabase();
              Cursor cursor = db.rawQuery(" SELECT * FROM " +TABLE_NAME,null);
              return cursor;
            }

        }


