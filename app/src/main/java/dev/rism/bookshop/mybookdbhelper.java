package dev.rism.bookshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class mybookdbhelper extends SQLiteOpenHelper {
    public static final String TABLE_ADD="addbook";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_BOOKNAME="bname";
    public static final String COLUMN_CATEGORY="bcat";
    public static final String COLUMN_AUTHOR="bauthor";
    public static final String COLUMN_PRICE="bprice";
    private static final String DATABASE_NAME="bookshop.db";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_CREATE="create table "+TABLE_ADD+"( "+COLUMN_ID+" integer primary key autoincrement,"+COLUMN_BOOKNAME+" text not null," +
            COLUMN_CATEGORY+" text not null,"+COLUMN_AUTHOR+" text not null,"+COLUMN_PRICE+" text not null);";
    public mybookdbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADD);
        onCreate(db);

    }
    //inserting new book
    public void addBook(Bookdb bookdb)
    {
SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_BOOKNAME,bookdb.get_bname());
        values.put(COLUMN_CATEGORY,bookdb.get_bcat());
        values.put(COLUMN_AUTHOR,bookdb.get_bauthor());
        values.put(COLUMN_PRICE,bookdb.get_bprice());
        db.insert(TABLE_ADD, null, values);
        db.close();
    }
    //getting book
    public Bookdb getBook(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_ADD,new String[]{COLUMN_ID,COLUMN_BOOKNAME,COLUMN_CATEGORY,
        COLUMN_AUTHOR,COLUMN_PRICE},COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null)
        {
            cursor.moveToFirst();
        }
    Bookdb bookdb=new Bookdb(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
            cursor.getString(3),cursor.getString(4));
        return bookdb;
    }
public List<Bookdb> getAllBooks()
{
    List<Bookdb> bookdbs=new ArrayList<Bookdb>();
    String selectQuery="SELECT * FROM "+TABLE_ADD;
    SQLiteDatabase db=this.getWritableDatabase();
    Cursor cursor=db.rawQuery(selectQuery,null);
    if(cursor.moveToFirst())
    {
        do {
            Bookdb bookdb=new Bookdb();
            bookdb.set_id(Integer.parseInt(cursor.getString(0)));
            bookdb.set_bname(cursor.getString(1));
            bookdb.set_bcat(cursor.getString(2));
            bookdb.set_bauthor(cursor.getString(3));
            bookdb.set_bprice(cursor.getString(4));
bookdbs.add(bookdb);
        }
        while (cursor.moveToNext());
    }
return bookdbs;
}
    public void deleteBook(Bookdb bookdb)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_ADD,COLUMN_ID+" =?",new String[]{String.valueOf(bookdb.get_id())});
        db.close();
    }
    public int getCount()
    { int count=0;
        String countQuery="SELECT * FROM "+TABLE_ADD;
        SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor= db.rawQuery(countQuery, null);
        count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
 public Cursor getChildLabels(String column)
 {
     String query;
     SQLiteDatabase db=this.getReadableDatabase();
     query="select * from "+TABLE_ADD +" where bcat = '"+column.trim()+"'";
     Cursor cursor=db.rawQuery(query,null);
     return cursor;

 }
    public Cursor getChildDetails(String column)
    {
        String query;
        SQLiteDatabase db=this.getReadableDatabase();
        query="select * from "+TABLE_ADD +" where bname = '"+column.trim()+"'";
        Cursor cursor=db.rawQuery(query, null);
        return cursor;

    }
}
