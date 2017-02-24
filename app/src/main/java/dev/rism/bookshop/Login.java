package dev.rism.bookshop;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by risha on 3/25/2016.
 */
public class Login extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    RelativeLayout relativeLayout;
    Button bok ;
    Button bcancel;
    TextView tvemployee;
    EditText tvuser,tvpassword ;
    TextView tvowner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_login,null);
        bok = (Button) view.findViewById(R.id.btok);
        bcancel = (Button)view.findViewById(R.id.btcancel);
        tvuser=(EditText)view.findViewById(R.id.etuser);
        tvpassword=(EditText)view.findViewById(R.id.etpassword);
        tvemployee = (TextView)view.findViewById(R.id.tvEmployee);
        tvowner=(TextView) view.findViewById(R.id.tvOwner);
        relativeLayout=(RelativeLayout) view.findViewById(R.id.rlayout);
        tvemployee.setOnClickListener(this);
        tvowner.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tvEmployee:
                relativeLayout.setBackgroundColor(Color.parseColor("#d130fe"));
                bok.setBackgroundColor(Color.parseColor("#d130fe"));
                bcancel.setBackgroundColor(Color.parseColor("#d130fe"));
                bok.setTextColor(Color.YELLOW);
                bcancel.setTextColor(Color.YELLOW);
                tvuser.setTextColor(Color.WHITE);
                tvpassword.setTextColor(Color.WHITE);
                tvemployee.setTextColor(Color.YELLOW);
                tvpassword.getText().clear();
                tvuser.getText().clear();
                break;
            case R.id.tvOwner:
                relativeLayout.setBackgroundColor(Color.WHITE);
                bok.setBackgroundColor(Color.WHITE);
                tvemployee.setTextColor(Color.parseColor("#3b1754"));
                tvuser.setTextColor(Color.BLACK);
                tvpassword.setTextColor(Color.BLACK);
                bcancel.setBackgroundColor(Color.WHITE);
                bok.setTextColor(Color.parseColor("#d130fe"));
                tvuser.getText().clear();
                tvpassword.getText().clear();
                bcancel.setTextColor(Color.parseColor("#d130fe"));
                break;
        }

    }
}
