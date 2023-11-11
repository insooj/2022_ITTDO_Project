package com.example.caregiverapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<Chat> chatList;
    private String name;


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(linearLayout);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position){
        Chat chat = chatList.get(position);

        holder.nameText.setText(chat.getName());
        holder.msgText.setText(chat.getMsg());
    }

    @Override
    public int getItemCount() {
        return chatList == null ? 0: chatList.size();
    }

    public void addChat(Chat chat){
        chatList.add(chat);
        notifyItemInserted(chatList.size()-1);
    }

    public ChatAdapter(List<Chat> chatData, String name){
        chatList = chatData;
        this.name = name;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView msgText;
        public View rootView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            msgText = itemView.findViewById(R.id.msgText);
            rootView = itemView;

            itemView.setEnabled(true);
            itemView.setClickable(true);

        }
    }
}
