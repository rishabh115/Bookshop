package dev.rism.bookshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {

    private ImageView ivm;
    TextView TV;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TV = (TextView) findViewById(R.id.tvma);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
                    ivm = (ImageView) findViewById(R.id.iv);
                    ivm.startAnimation(rotate);
                    TV.setVisibility(View.VISIBLE);
                    Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(), "gotham.otf");
                    TV.setTypeface(tf);
                    sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                     prefs=getPreferences(Context.MODE_PRIVATE);
                    count=prefs.getInt("Counter",0);
                    count++;
                    editor=prefs.edit();
                    editor.putInt("Counter",count);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
            }

        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
