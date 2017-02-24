package dev.rism.bookshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by risha on 3/15/2016.
 */
public class myhomedb extends SQLiteOpenHelper
{
    public static final String TABLE_ADD="addhome";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_BOOKNAME="bname";
    public static final String COLUMN_CATEGORY="bcat";
    public static final String COLUMN_AUTHOR="bauthor";
    public static final String COLUMN_PRICE="bprice";
    private static final String DATABASE_NAME="home.db";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_CREATE="create table "+TABLE_ADD+"( "+COLUMN_ID+" integer primary key autoincrement,"+COLUMN_BOOKNAME+" text not null," +
            COLUMN_CATEGORY+" text not null,"+COLUMN_AUTHOR+" text not null,"+COLUMN_PRICE+" text not null);";

    public myhomedb(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        {
            db.execSQL(DATABASE_CREATE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADD);
        onCreate(db);
    }
    public void addBook(Bookdb bookdb)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_BOOKNAME, bookdb.get_bname());
        values.put(COLUMN_CATEGORY,bookdb.get_bcat());
        values.put(COLUMN_AUTHOR,bookdb.get_bauthor());
        values.put(COLUMN_PRICE,bookdb.get_bprice());
        db.insert(TABLE_ADD, null, values);
        db.close();
    }

    public Cursor getBook(String s)
    {
        String query;
        SQLiteDatabase db=this.getReadableDatabase();
        query="select * from "+TABLE_ADD+" where bcat= '"+s+"'";
        Cursor cursor=db.rawQuery(query, null);
        return cursor;
    }
}
