package dev.rism.bookshop;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by risha on 2/10/2016.
 */
public class Home extends drawer {
   public ScrollView scroll;
   public Spinner spin;
   public String category[]={"PHYSICS","CHEMISTRY","MATHS","COMPUTER SCIENCE","ELECTRICAL ENGG","GRAPHICS","OTHERS"};
   public TextView tvtotal,tvsold;
  public   ListView lvhome;
  public   Button go;
    public static ArrayList<String> itemNameList=new ArrayList<String>();
    public static ArrayList<String> itemCatList=new ArrayList<String>();
    public static ArrayList<String> itemAuthorList=new ArrayList<String>();
    public static ArrayList<Integer> itemPriceList=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home,frame);
        setTitle("Home");
        getSupportActionBar().setTitle(getTitle());
        scroll=(ScrollView)findViewById(R.id.scrollView);
        spin=(Spinner)findViewById(R.id.spinner);
        tvtotal=(TextView)findViewById(R.id.tvTotal);
        tvsold=(TextView)findViewById(R.id.tvSold);
        go=(Button)findViewById(R.id.bGo);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,category);
        spin.setAdapter(adapter);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                itemPriceList.clear();
                itemCatList.clear();
                itemAuthorList.clear();
                itemNameList.clear();
                s = spin.getSelectedItem().toString();
                getItemsFromDB(s);
                lvhome=(ListView)findViewById(R.id.lvhome);
                myhomeadapter adapter=new myhomeadapter(Home.this);
                lvhome.setAdapter(adapter);
                int n = 0,m=0;
                mybookdbhelper db = new mybookdbhelper(getApplicationContext());
                Cursor cursor = db.getChildLabels(s);
                n = cursor.getCount();
                cursor.close();
                db.close();
                myhomedb homedb=new myhomedb(getApplicationContext());
                Cursor cursor1=homedb.getBook(s);
                m=cursor1.getCount();
                cursor1.close();
                homedb.close();
                tvtotal.setTypeface(Typeface.createFromAsset(getAssets(),"Robot.ttf"),Typeface.BOLD);

                tvsold.setTypeface(Typeface.createFromAsset(getAssets(),"Robot.ttf"),Typeface.BOLD);
                tvtotal.setText("Total " + s + " books in stock : " + n);
                tvsold.setText("Total " + s + " book sold : "+m);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void getItemsFromDB(String s)
    {
        Cursor cursor=null;
        myhomedb db=new myhomedb(this);
        cursor=db.getBook(s);
        if (cursor.moveToFirst()) {
            do {
                itemNameList.add(cursor.getString(1));
                itemCatList.add(cursor.getString(2));
                itemAuthorList.add(cursor.getString(3));
                itemPriceList.add(cursor.getInt(4));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

}
