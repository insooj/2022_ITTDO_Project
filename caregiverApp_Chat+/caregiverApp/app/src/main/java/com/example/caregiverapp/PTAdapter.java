package com.example.caregiverapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class PTAdapter extends RecyclerView.Adapter<PTAdapter.CustomViewHolder>  {

    private ArrayList<PatientAccount> arrayList;
    private Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public PTAdapter(ArrayList<PatientAccount> arrayList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view,recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.tv_name.setText(arrayList.get(position).getUserName());
        holder.tv_old.setText(arrayList.get(position).getUserold());
        holder.tv_address.setText(arrayList.get(position).getUserRegion());
        holder.tv_day.setText(arrayList.get(position).getUserdate());
        holder.tv_time.setText(arrayList.get(position).getUsertime());
        holder.tv_salary.setText(String.valueOf(arrayList.get(position).getUserpay()));
        holder.tv_phonenumber.setText(arrayList.get(position).getUserPhone());
        holder.tv_disease.setText(arrayList.get(position).getUserdisease());
        holder.tv_etc.setText(arrayList.get(position).getEtc());
        holder.tv_sex.setText(arrayList.get(position).getSex());

    }

    @Override
    public int getItemCount() {
        return (arrayList!=null?arrayList.size():0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_old;
        TextView tv_address;
        TextView tv_day;
        TextView tv_time;
        TextView tv_salary;
        TextView tv_phonenumber;
        TextView tv_disease;
        TextView tv_etc;
        TextView tv_sex;
        public CustomViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_old=itemView.findViewById(R.id.tv_old); this.tv_address=itemView.findViewById(R.id.tv_address);
            this.tv_day=itemView.findViewById(R.id.tv_day); this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_salary=itemView.findViewById(R.id.tv_salary); this.tv_phonenumber=itemView.findViewById(R.id.tv_phonenumber);
            this.tv_disease=itemView.findViewById(R.id.tv_disease); this.tv_etc=itemView.findViewById(R.id.tv_etc);
            this.tv_sex=itemView.findViewById(R.id.tv_sex);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface !=null){
                        int pos=getAdapterPosition();

                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }
}
