package dev.rism.bookshop;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.drakeet.materialdialog.MaterialDialog;


public class drawer extends AppCompatActivity {
    String stitles[];
    protected DrawerLayout sDrawerLayout; private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ListView sListView;
    FrameLayout frame;
    TextView tvlist;
    Typeface typeface;
    int count=0;
    String classes[]={"Home","Sell","Add","Cart"};
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
      // tvlist=(TextView)findViewById(R.id.text1);
      // typeface=Typeface.createFromAsset(getAssets(),"gotham.otf");
//       tvlist.setTypeface(typeface);
       mybookdbhelper db=new mybookdbhelper(this);
        count=db.getCount();
        frame=(FrameLayout)findViewById(R.id.content_frame);
        mTitle=mDrawerTitle=getTitle();
        stitles=getResources().getStringArray(R.array.operations);
        stitles[1]="Sell ("+count+")";
        sDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        sListView=(ListView)findViewById(R.id.left_drawer);

        sListView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, stitles));
        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position==0)
                {
                    home_fragment home=new home_fragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,home).commit();
                    sListView.setSelected(true);
                    sDrawerLayout.closeDrawer(sListView);
                }
               else{*/
                sListView.setSelected(true);
                sListView.setSelection(position);
                String cheese = classes[position];
                try {
                    Class ourClass = Class.forName("dev.rism.bookshop." + cheese);
                    Intent ourIntent = new Intent(drawer.this, ourClass);
                    startActivity(ourIntent);
                    finish();
                    overridePendingTransition(R.anim.custom_right,R.anim.custom_left);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                //}
            }}
        });
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this,sDrawerLayout,R.string.drawer_open,R.string.drawer_close){

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
                        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(drawerView.getWindowToken(),0);

                // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        sDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {

        final MaterialDialog alertDialogBuilder = new MaterialDialog(drawer.this);
        alertDialogBuilder.setTitle("Close the app");
        alertDialogBuilder.setMessage("Click OK to close the app");
        alertDialogBuilder.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                drawer.this.finish();
            }
        });
                alertDialogBuilder.setNegativeButton("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialogBuilder.dismiss();
                            }
                        });

        alertDialogBuilder.show();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        switch (item.getItemId())
        {
            case R.id.about:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("About");
                builder.setMessage(R.string.about);
                builder.create();
                builder.show();

                return true;
            case R.id.setting:
              /*  ConnectionDetector check=new ConnectionDetector(this);
                if(check.isConnecting())
                {
                   Toast toast= Toast.makeText(this,"Internet Connection Available",Toast.LENGTH_SHORT);
                    TextView v=(TextView)toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.WHITE);
                    toast.show();
                }
                else
                { int count=0;
                    mybookdbhelper db=new mybookdbhelper(this);
                    count=db.getCount();
                   Toast toast= Toast.makeText(this,"No Internet Connection "+count,Toast.LENGTH_SHORT);
                    TextView v=(TextView)toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.WHITE);
                    toast.show();
                }*/
                FragmentManager manager=getSupportFragmentManager();
                Login login=new Login();
                login.show(manager,"Login");

           return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }
}
