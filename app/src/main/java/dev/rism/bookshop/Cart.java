package dev.rism.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by risha on 2/10/2016.
 */
public class Cart extends AppCompatActivity {
    Button Clear,Send;
   static ListView lv;
    TextView tvtot;
    static int check=0;
   static int sum=0;
    String finalSt="";String st ="";
    public static ArrayList<String> itemNameList=new ArrayList<String>();
    public static ArrayList<String> itemCatList=new ArrayList<String>();
    public static ArrayList<String> itemAuthorList=new ArrayList<String>();
    public static ArrayList<Integer> itemPriceList=new ArrayList<Integer>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Cart","onCreate");
        setTitle("Cart");
        setContentView(R.layout.activity_cart);
        //getLayoutInflater().inflate(R.layout.activity_cart, drawer.frame);
                tvtot=(TextView)findViewById(R.id.textView4);
        sum=0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getItemsFromDB();
        initialise();
        Clear=(Button)findViewById(R.id.bClear);
        Send=(Button)findViewById(R.id.bSend);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mycartdb db=new mycartdb(getApplicationContext());
                itemPriceList.clear();
                itemCatList.clear();
                itemAuthorList.clear();
                itemNameList.clear();
                sum=0;
                db.delete();
                db.close();
               initialise();
                Toast.makeText(getApplicationContext(), "Cart Cleared", Toast.LENGTH_SHORT).show();
                tvtot.setText("The total is :Rs.0");

            }
        });

       mycartdb db=new mycartdb(this);
        sum=db.totalprice();
        tvtot.setText("The total is :Rs." + sum);
        db.close();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st =Message(itemNameList,itemPriceList);
                st = st +"\n"+"The total price is Rs."+sum;
                int i,size;size=itemNameList.size();
                myhomedb homedb=new myhomedb(getApplicationContext());
                for (i=0;i<size;i++)
                {
                    homedb.addBook(new Bookdb(itemNameList.get(i),itemCatList.get(i),itemAuthorList.get(i),String.valueOf(itemPriceList.get(i))));
                }
                homedb.close();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invoice");
                emailIntent.putExtra(Intent.EXTRA_TEXT, st);
                startActivityForResult(emailIntent,check);


            }
        });
    }


    public void getItemsFromDB()
   {
       Log.d("Cart","getItemsFromDB");
       Cursor cursor=null;
       itemPriceList.clear();
       itemAuthorList.clear();
       itemCatList.clear();
       itemNameList.clear();
       mycartdb db=new mycartdb(this);
       cursor=db.getBook();
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
 public void populate()
 {

 }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Cart","onPause");


    }


    public String Message(ArrayList<String> name,ArrayList<Integer> price)
    {
        int i,size;
        String s="";
        size=name.size();
        for(i=0;i<size;i++)
        {
            s=s+(i+1)+"."+name.get(i)+"  Rs."+String.valueOf(price.get(i))+"\n";
        }

        return s;
    }
  public void  initialise()
  {
      Log.d("Cart","initialise");
      lv=(ListView)findViewById(R.id.lvcart);
      mycartadapter adapter=new mycartadapter(this);
      lv.setAdapter(adapter);
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Cart","onActivityResult");
        getItemsFromDB();
        initialise();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(Cart.this,Sell.class);
                startActivity(intent);
                finish();
                break;
        }


        return true;
    }
}
