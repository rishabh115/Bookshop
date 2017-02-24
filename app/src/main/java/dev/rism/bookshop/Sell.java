package dev.rism.bookshop;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class Sell extends drawer{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    SearchView search;
    HashMap<String, List<String>> listDataChild;
    String Cat[]={"PHYSICS","CHEMISTRY","MATHS","COMPUTER SCIENCE","ELECTRICAL ENGG","GRAPHICS","OTHERS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sell, frame);
        setTitle("Sell");
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    private void prepareListData() {
        int i,row;
        mybookdbhelper db=new mybookdbhelper(getApplicationContext());
        Cursor cursor;
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("PHYSICS");
        listDataHeader.add("CHEMISTRY");
        listDataHeader.add("MATHS");
        listDataHeader.add("COMPUTER SCIENCE");
        listDataHeader.add("ELECTRICAL ENGG");

        listDataHeader.add("GRAPHICS");
        listDataHeader.add("OTHERS");

        List<String> phy = new ArrayList<String>();
        List<String> chem = new ArrayList<String>();
        List<String> math = new ArrayList<String>();
        List<String> comp = new ArrayList<String>();
        List<String> eee = new ArrayList<String>();
        List<String> grp = new ArrayList<String>();
        List<String> oth = new ArrayList<String>();
        List<String>[] test = new List[]{phy,chem,math,comp,eee,grp,oth};
       /* cursor =db.getChildLabels(Cat[0]);
        row=cursor.getCount();
        initphy(cursor, row, phy);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[1]);
        row=cursor.getCount();
        initphy(cursor,row,chem);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[2]);
        row=cursor.getCount();
        initphy(cursor, row, math);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[3]);
        row=cursor.getCount();
        initphy(cursor, row, comp);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[4]);
        row=cursor.getCount();
        initphy(cursor, row, eee);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[5]);
        row=cursor.getCount();
        initphy(cursor, row, grp);
        cursor.close();
        db.close();
        cursor =db.getChildLabels(Cat[6]);
        row=cursor.getCount();
        initphy(cursor,row,oth);
        cursor.close();
        db.close();
        listDataChild.put(listDataHeader.get(0), phy);
        listDataChild.put(listDataHeader.get(1), chem);
        listDataChild.put(listDataHeader.get(2), math);
        listDataChild.put(listDataHeader.get(3), comp);
        listDataChild.put(listDataHeader.get(4), eee);
        listDataChild.put(listDataHeader.get(5), grp);
        listDataChild.put(listDataHeader.get(6), oth);*/
        int j=0;
        for (j=0;j<7;j++)
        {
            cursor =db.getChildLabels(Cat[j]);
            row=cursor.getCount();
            initphy(cursor, row, test[j]);
            cursor.close();
            db.close();
            listDataChild.put(listDataHeader.get(j), test[j]);
        }

    }
//testing purpose adb shell monkey -p dev.rism.bookshop -v 500
    @Override
    protected void onStart() {
        super.onStart();
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String GS, CS;
                Cursor cursor;
                String author, price;
                mybookdbhelper db = new mybookdbhelper(getApplicationContext());
                GS = listDataHeader.get(groupPosition);
                Bundle bundle = new Bundle();
                CS = listAdapter.getChild(groupPosition, childPosition).toString();
                cursor = db.getChildDetails(CS);
                if (cursor.moveToFirst()) {
                    author = cursor.getString(3);
                    price = cursor.getString(4);

                    bundle.putString("author", author);
                    bundle.putString("price", price);
                }
                cursor.close();
                db.close();
                bundle.putString("category",GS);
                bundle.putString("name", CS);
                Intent intent = new Intent(Sell.this, Book.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
    }

    public void initphy(Cursor cursor,int r,List<String> phy)
    {
        if (cursor.moveToFirst()) {
            do {
               phy.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(phy);
    }
    public void initche(Cursor cursor,int r,List<String> che)
    {
        if (cursor.moveToFirst()) {
            do {
                che.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(che);
    }
    public void initmat(Cursor cursor,int r,List<String> mat)
    {
        if (cursor.moveToFirst()) {
            do {
                mat.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(mat);
    }
    public void initcs(Cursor cursor,int r,List<String> cs)
    {
        if (cursor.moveToFirst()) {
            do {
                cs.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(cs);
    }
    public void initee(Cursor cursor,int r,List<String> ee)
    {
        if (cursor.moveToFirst()) {
            do {
                ee.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(ee);
    }
    public void initgrp(Cursor cursor,int r,List<String> grp)
    {
        if (cursor.moveToFirst()) {
            do {
                grp.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(grp);
    }
    public void initoth(Cursor cursor,int r,List<String> oth)
    {
        if (cursor.moveToFirst()) {
            do {
                oth.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Collections.sort(oth);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);


        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        int i;
        for(i=0;i<=6;i++){
        expListView.collapseGroup(i);

    }


}

    @Override
    public void onBackPressed() {
        finish();
    }
}