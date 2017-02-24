package dev.rism.bookshop;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by risha on 3/20/2016.
 */
public class home_fragment extends android.support.v4.app.Fragment {


    public String category[]={"PHYSICS","CHEMISTRY","MATHS","COMPUTER SCIENCE","ELECTRICAL ENGG","GRAPHICS","OTHERS"};
    Home ob=null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_home,container,false);


        return rootview;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ob =new Home();
        final ArrayAdapter adapter=new ArrayAdapter(ob,android.R.layout.simple_spinner_dropdown_item,category);
        ob.spin.setAdapter(adapter);
        ob.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                Home.itemPriceList.clear();
                Home.itemCatList.clear();
                Home.itemAuthorList.clear();
                Home.itemNameList.clear();
                s = ob.spin.getSelectedItem().toString();
                ob.getItemsFromDB(s);
                myhomeadapter adapter =new myhomeadapter(ob);
                ob.lvhome.setAdapter(adapter);
                int n = 0,m=0;
                mybookdbhelper db = new mybookdbhelper(ob);
                Cursor cursor = db.getChildLabels(s);
                n = cursor.getCount();
                cursor.close();
                db.close();
                myhomedb homedb=new myhomedb(ob);
                Cursor cursor1=homedb.getBook(s);
                m=cursor1.getCount();
                cursor1.close();
                homedb.close();
                ob.tvtotal.setText("The total " + s + " book : " + n);
                ob.tvsold.setText("The total " + s + " book sold : "+m);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
