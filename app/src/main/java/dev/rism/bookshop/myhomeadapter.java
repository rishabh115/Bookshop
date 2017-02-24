package dev.rism.bookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by risha on 3/16/2016.
 */
public class myhomeadapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
   public myhomeadapter(Context context)
   {
       this.context=context;
       inflater = LayoutInflater.from(context);
   }
    @Override
    public int getCount() {
        return Home.itemNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;View view;
        view=convertView;
        if(view==null) {
            holder=new ViewHolder();
            inflater=LayoutInflater.from(context);
            view = inflater.inflate(R.layout.cartlist, parent, false);
            holder.itemName = (TextView) view.findViewById(R.id.tvbook);
            holder.itemAuthor = (TextView) view.findViewById(R.id.tvauthor);
            holder.itemPrice = (TextView) view.findViewById(R.id.tvprice);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();}
        holder.itemName.setText(String.valueOf(Home.itemNameList.get(position)));
        holder.itemAuthor.setText(String.valueOf(Home.itemAuthorList.get(position)));
        holder.itemPrice.setText("Rs."+String.valueOf(Home.itemPriceList.get(position)));
        return view;
    }
    static class ViewHolder {
        TextView itemName, itemAuthor, itemPrice;
    }
}
