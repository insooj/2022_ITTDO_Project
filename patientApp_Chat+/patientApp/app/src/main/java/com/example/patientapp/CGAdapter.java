package com.example.patientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class CGAdapter extends RecyclerView.Adapter<CGAdapter.CustomViewHolder>  {

    private ArrayList<UserAccount> arrayList;
    private Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public CGAdapter(ArrayList<UserAccount> arrayList, Context context,RecyclerViewInterface recyclerViewInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view,recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_name.setText(arrayList.get(position).getUserName());
        holder.tv_old.setText(arrayList.get(position).getUserold());
        holder.tv_address.setText(arrayList.get(position).getUserRegion());
        holder.tv_day.setText(arrayList.get(position).getUserdate());
        holder.tv_time.setText(arrayList.get(position).getUsertime());
        holder.tv_salary.setText(String.valueOf(arrayList.get(position).getUserpay()));
        holder.tv_phonenumber.setText(arrayList.get(position).getUserPhone());
        holder.tv_disease.setText(arrayList.get(position).getUserdisease());
        holder.tv_sex.setText(arrayList.get(position).getSex());

    }

    @Override
    public int getItemCount() {
        return (arrayList!=null?arrayList.size():0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_name;
        TextView tv_old;
        TextView tv_address;
        TextView tv_day;
        TextView tv_time;
        TextView tv_salary;
        TextView tv_phonenumber;
        TextView tv_disease;
        TextView tv_sex;

        public CustomViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.iv_profile=itemView.findViewById(R.id.iv_profile); this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_old=itemView.findViewById(R.id.tv_old); this.tv_address=itemView.findViewById(R.id.tv_address);
            this.tv_day=itemView.findViewById(R.id.tv_day); this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_salary=itemView.findViewById(R.id.tv_salary); this.tv_phonenumber=itemView.findViewById(R.id.tv_phonenumber);
            this.tv_disease=itemView.findViewById(R.id.tv_disease); this.tv_sex=itemView.findViewById(R.id.tv_sex);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CGAdapter.this.recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            CGAdapter.this.recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }
}
