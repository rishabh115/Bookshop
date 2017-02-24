package dev.rism.bookshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by risha on 2/29/2016.
 */
public class mycartdb extends SQLiteOpenHelper {
    public static final String TABLE_CART="addcart";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_BOOKNAME="bname";
    public static final String COLUMN_CATEGORY="bcat";
    public static final String COLUMN_AUTHOR="bauthor";
    public static final String COLUMN_PRICE="bprice";
    private static final String DATABASE_NAME="cart.db";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_CREATE="create table "+TABLE_CART+"( "+COLUMN_ID+" integer primary key autoincrement,"+
            COLUMN_BOOKNAME+" text not null,"+COLUMN_CATEGORY+" text not null," +COLUMN_AUTHOR+" text not null,"+COLUMN_PRICE+" text not null);";
    public mycartdb(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
    }//creating the database

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);

    }
    public void addBook(Bookdb bookdb)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_BOOKNAME,bookdb.get_bname());
        values.put(COLUMN_CATEGORY,bookdb.get_bcat());
        values.put(COLUMN_AUTHOR,bookdb.get_bauthor());
        values.put(COLUMN_PRICE,bookdb.get_bprice());
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public Cursor getBook()
    {
        String query;
        SQLiteDatabase db=this.getReadableDatabase();
        query="select * from "+TABLE_CART;
        Cursor cursor=db.rawQuery(query, null);
        return cursor;
    }
    public int totalprice()
    {int sum=0;
        ArrayList<Integer> itemPriceList=new ArrayList<Integer>();
        Cursor cursor=getBook();
        if (cursor.moveToFirst()) {
            do {
                itemPriceList.add(cursor.getInt(4));
            }
            while (cursor.moveToNext());
        }
        for(Integer i:itemPriceList)
        {
            sum=sum+(int)i;
        }
        return sum;
    }
    public void delete()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_CART);
        db.execSQL("delete from sqlite_sequence where name ='"+TABLE_CART+"'");
        db.close();
    }
}
