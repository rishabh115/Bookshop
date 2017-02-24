package dev.rism.bookshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AccessibleObject;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by risha on 2/10/2016.
 */
public class Add extends drawer {
   private TextView textView;
   private EditText etbookname,etauthor,etmrp;
    Button badd;
    Spinner sp;
    CoordinatorLayout layout;
    FloatingActionButton floatingActionButton;
    View view;
    String catArray[]={"PHYSICS","CHEMISTRY","MATHS","COMPUTER SCIENCE","ELECTRICAL ENGG","GRAPHICS","OTHERS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity=this;
        getLayoutInflater().inflate(R.layout.activity_add, frame);
        layout=(CoordinatorLayout)findViewById(R.id.coordinatorlayout);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab2);
       final mybookdbhelper db=new mybookdbhelper(this);
        textView=(TextView)findViewById(R.id.tvCategory);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"gotham.otf");
        textView.setTypeface(typeface);
        etbookname=(EditText)findViewById(R.id.etBookName);
        etauthor=(EditText)findViewById(R.id.etAuthor);
        etmrp=(EditText)findViewById(R.id.etMRP);
        setTitle("Add");
        sp=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,catArray);
        sp.setAdapter(adapter);
        badd=(Button)findViewById(R.id.bADD);
        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etbookname.getText().toString().toUpperCase().trim();
                String author=etauthor.getText().toString().toUpperCase().trim();
                String cat=sp.getSelectedItem().toString();
                String price=etmrp.getText().toString().toUpperCase().trim();
                Boolean flag=(name.equals(""))||(author.equals(""))||(price.equals(""));

                if(flag)
                {
                    Snackbar snackbar=Snackbar.make(layout,"Enter Valid Details",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{

                    db.addBook(new Bookdb(name,cat,author,price));
                    db.close();
                    Toast.makeText(getApplicationContext(),"Book added ",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Add.this,Home.class);
                    startActivity(intent);
                    finish();
                }

        }});

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                ConnectionDetector ob=new ConnectionDetector(activity);
                if (ob.isConnecting()){
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();
                integrator.setOrientationLocked(false);}
                else
                {
                    Toast.makeText(getApplicationContext(),"Plz connect to net",Toast.LENGTH_LONG).show();
                }
            }
        });

                // creates call to onPrepareOptionsMenu()
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String s;
        IntentResult integrator=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(integrator.getContents()!=null)
        {
         if (integrator.getFormatName().equalsIgnoreCase(BarcodeFormat.EAN_13.toString()))
            {
                s=integrator.getContents();
                if(s.length()==13)
                {s=converter(s);}

                new BookTask().execute("https://www.googleapis.com/books/v1/volumes?q=isbn:"+s);
                Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public String converter(String s)
    {
        int i,k,j=0;
        int count=10;
        String st=s.trim().substring(3,12);
        for(i=0;i<st.length();i++)
        {
            k=Integer.parseInt(st.charAt(i)+"");
            j=j+k*count;count--;}
        j=(11-(j%11))%11;
        return st+""+j;
    }
    public class BookTask extends AsyncTask<String,Boolean,String>
    {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            String data="";
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                data=readStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            parseJson(s);
        }

        private String readStream(InputStream in) throws IOException {
            String s;
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            StringBuilder builder=new StringBuilder();
            try {
                while ((s=bufferedReader.readLine())!=null)
                {
                    builder.append(s);
                }}
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return builder.toString();
        }
        public void parseJson(String s)
        {
            String st="";
            try
            {
                String author,title;
                JSONObject jsonObject= new JSONObject(s);
                JSONArray jArray = jsonObject.getJSONArray("items");
                JSONObject volumeInfo = jArray.getJSONObject(0).getJSONObject("volumeInfo");
                title = volumeInfo.getString("title");

                JSONArray authors = volumeInfo.getJSONArray("authors");
                author = authors.getString(0);
                if(title!=null && author!=null) {
                    etauthor.setText(author);
                    etbookname.setText(title);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
