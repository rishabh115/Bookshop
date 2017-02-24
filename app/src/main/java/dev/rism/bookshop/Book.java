package dev.rism.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by risha on 2/10/2016.
 */
public class Book extends AppCompatActivity {
    TextView tbname,tauthor,tprice;Button addCart;
    TextView amt;
    Toolbar toolbar;
    int i=0;
  private String name,author,price,category;
    CoordinatorLayout layout;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
        layout=(CoordinatorLayout) findViewById(R.id.Clayout);
        amt=(TextView) findViewById(R.id.textView5);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setTitle("Book");
        tbname=(TextView)findViewById(R.id.textView);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "gotham.otf");
        tbname.setTypeface(typeface);
        tauthor=(TextView)findViewById(R.id.textView2);
        tprice=(TextView)findViewById(R.id.textView3);
        addCart=(Button)findViewById(R.id.button);
        mycartdb db=new mycartdb(this);
        i=db.getBook().getCount();
        db.close();
        amt.setText(i+"");
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        name=b.getString("name");
        author=b.getString("author");
        price=b.getString("price");
        category=b.getString("category");
        tbname.setText(b.getString("name"));
        tauthor.setText(b.getString("author"));
        tprice.setText("Rs. " + b.getString("price"));
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycartdb db = new mycartdb(getApplicationContext());
                db.addBook(new Bookdb(name, category, author, price));
                db.close();
                amt.setText(++i+"");
                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Book.this,Cart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        amt.setText(i+"");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(getApplicationContext(),Sell.class);
                startActivity(intent);
                break;
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }
}
