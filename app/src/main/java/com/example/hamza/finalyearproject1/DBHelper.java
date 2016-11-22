package com.example.hamza.finalyearproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hamza on 11/7/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ComplainDB";
    private static final String COMP_TABLE_NAME = "ComplainTable";
    private static final String COL_1 = "C_ID";
    private static final String COL_2 = "TOWN_NAME";
    private static final String COL_3 = "UC";
    private static final String COL_4 = "COMPLAIN_TYPE";
    private static final String COL_5 = "DESC";
    private static final String COL_6 = "Image";
    private static final String SIGNUP_TABLE_NAME = "SIGNUP TABLE";
    private static final String S1 = "";
    private static final String S2 = "";
    private static final String S3= "";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override

    public void onCreate(SQLiteDatabase db) {


      /**  db.execSQL(
                "create table" +TABLE_NAME+
                        "(C_ID integer primary key, TOWN NAME text,UC INTEGER,ComplainType text, Description text)"**/
  //      );
        String CREATE_DAY_TABLE = "CREATE TABLE " + COMP_TABLE_NAME + "("
                +COL_1 + " INTEGER PRIMARY KEY," + COL_2 + " TEXT," + COL_3 + " TEXT, "
                + COL_4 + " TEXT," + COL_5 + " TEXT," + COL_6 +" BLOB"+");";
        db.execSQL(CREATE_DAY_TABLE);
      //  db.execSQL("create table"+TABLE_NAME+"(C_ID INTEGER PRIMARY KEY AUTOINCREMENT,TOWN_NAME TEXT,UC TEXT,COMPLAIN_TYPE TEXT,DESC TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+COMP_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertComplain (String townName, String uc_Num, String complainType,String Desc,byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TOWN_NAME", townName);
        contentValues.put("UC", uc_Num);
        contentValues.put("COMPLAIN_TYPE", complainType);
        contentValues.put(COL_5,Desc);
       contentValues.put(COL_6, image );
      long result =   db.insert(COMP_TABLE_NAME, null, contentValues);
        if(result== -1)
        {
            db.close();
            return false;
        }
        else
        {
            db.close();
        return true;}

    }




// Android DATABASE MANAGER

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }


    }}