package com.example.vishalsingh.admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishalsingh on 12/11/17.
 */



class RecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView drivername, busno, location,studentcount,busstatus;
    public ImageView img;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        drivername = (TextView)itemView.findViewById(R.id.drivername);
        busno = (TextView)itemView.findViewById(R.id.busno);
        location = (TextView)itemView.findViewById(R.id.location);
        studentcount = (TextView)itemView.findViewById(R.id.studentcount);
        busstatus = (TextView)itemView.findViewById(R.id.busstatus);
        img = (ImageView)itemView.findViewById(R.id.imagestatus);
    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    //for adding listener on the on the recyclerview

    private List<Data> listdata = new ArrayList<>();
    private OnItemClicked onClick;

    RecyclerViewAdapter(List<Data> listdata){
        this.listdata = listdata;

    }

    public interface OnItemClicked{
        void onItemClick(int position);
    }




    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick = onClick;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            holder.img.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onClick.onItemClick(position);
                }
            });

       // drivername, busno, location,studentcount,busstatus;
        holder.drivername.setText(listdata.get(position).getDrivername());
        holder.busno.setText(listdata.get(position).getBusno());
        holder.location.setText(listdata.get(position).getLocation());
        holder.studentcount.setText(listdata.get(position).getStudentcount());
        holder.busstatus.setText(listdata.get(position).getBusstatus());
        if(listdata.get(position).getImgId() == 0)
            holder.img.setImageResource(R.drawable.redcircle);
        else
            holder.img.setImageResource(R.drawable.greenbullet);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
