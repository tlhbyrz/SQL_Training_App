package com.example.boyraztalha.sql_training_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper  extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "sqltraining.db";
    public static final int DATABASE_VERSION_NUM = 1;

    SQLiteDatabase sqLiteDatabase;

    public SqliteHelper(Context c){
        super(c,DATABASE_NAME,null,DATABASE_VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.sqLiteDatabase = db;

        String CRETAE_TABLE_STATEMENT = "CREATE TABLE " +
                Database_Column_Names.ColumnNames.TABLE_NAME + " ( " +
                Database_Column_Names.ColumnNames._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Database_Column_Names.ColumnNames.WORKER_NAME + " TEXT, "+
                Database_Column_Names.ColumnNames.WORKER_SALARY + " INTEGER " + " )";

        sqLiteDatabase.execSQL(CRETAE_TABLE_STATEMENT);

        putFirstDummyData();
    }

    private void putFirstDummyData() {
        People_info peopleInfo = new People_info("Talha",10000);
        addToTheDatabase(peopleInfo);

        People_info peopleInfo2 = new People_info("Ahmet",1000);
        addToTheDatabase(peopleInfo2);

        People_info peopleInfo3 = new People_info("Musa",5400);
        addToTheDatabase(peopleInfo3);
    }

    public void addToTheDatabase(People_info people_info){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database_Column_Names.ColumnNames.WORKER_NAME,people_info.getName());
        contentValues.put(Database_Column_Names.ColumnNames.WORKER_SALARY,people_info.getSalary());

        sqLiteDatabase.insert(Database_Column_Names.ColumnNames.TABLE_NAME,null,contentValues);
    }

    public void updateDatabase(String name, int salary){
        sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database_Column_Names.ColumnNames.WORKER_SALARY,salary);

        //isme gore salary update etmemizi sagliyor.
        String selection = Database_Column_Names.ColumnNames.WORKER_NAME + " LIKE ? ";
        String[] selectedArgs = {name};

        sqLiteDatabase.update(Database_Column_Names.ColumnNames.TABLE_NAME,
                contentValues,selection,selectedArgs);

    }

    public void deleteInfoFromDatabase(String name){
        String selection = Database_Column_Names.ColumnNames.WORKER_NAME + " LIKE ? ";
        String[] selectedArgs = {name};

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Database_Column_Names.ColumnNames.TABLE_NAME,
                selection,selectedArgs);
    }

    public ArrayList<String> getAllDatas(){
        ArrayList<String> arrayList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+
                Database_Column_Names.ColumnNames.TABLE_NAME ,null);

        if (cursor.moveToFirst()){
            do {
                People_info peopleInfo = new People_info();
                peopleInfo.setName(cursor.getString(cursor.getColumnIndex(Database_Column_Names.ColumnNames.WORKER_NAME)));
                peopleInfo.setSalary(cursor.getInt(cursor.getColumnIndex(Database_Column_Names.ColumnNames.WORKER_SALARY)));
                arrayList.add(peopleInfo.toString());

            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database_Column_Names.ColumnNames.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
