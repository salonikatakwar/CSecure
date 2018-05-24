package com.example.chinmay.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chinmay on 11-09-2017.
 */

class MyAdapter extends ArrayAdapter<String> {
    final Context context;
    ArrayList<String> list1;
    ArrayList<String> list4;
    ArrayList<String> list5;
    ArrayList<String> list6;
    ArrayList<String> add;
    String[] list;
    int lenght;
    public MyAdapter(@NonNull Context context,int y, ArrayList l1) {
        super(context,y,l1);
        this.context=context;
        this.list=list;

        list1=l1;


    }
    public MyAdapter(@NonNull Context context,int y, ArrayList l1,ArrayList l2,ArrayList l3,ArrayList l4) {
        super(context,y,l1);
        this.context=context;
        this.list=list;

        list1=l1;
        add=l2;
        list5=l3;
        list6=l4;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View rowView = inflater.inflate(R.layout.activity_custom_adapter, parent, false);
         // ImageView i = (ImageView) rowView.findViewById(R.id.i1);
          TextView t = (TextView) rowView.findViewById(R.id.t2);
        TextView addr=(TextView)rowView.findViewById(R.id.address);
        TextView clas=(TextView)rowView.findViewById(R.id.clas);
        TextView gend=(TextView)rowView.findViewById(R.id.gender) ;
         // i.setImageResource(R.mipmap.ic_launcher);
          t.setText(list1.get(position));
        gend.setText(list6.get(position));
        if(add.get(position).contains("null")) {
            addr.setText("Address: Nagpur");

        }
        else
        {
            addr.setText("Address: "+add.get(position));
        }
        clas.setText("Class: "+list5.get(position));


          return rowView;

    }

}