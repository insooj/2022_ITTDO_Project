package com.example.patientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sub_activity extends AppCompatActivity {
    private Button btn_mypage;
    private Button btn_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_mypage = findViewById(R.id.btn_mypage);
        btn_list=findViewById(R.id.btn_list);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sub_activity.this ,mypage_activity.class);
                startActivity(intent);
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_activity.this ,list_activity.class);
                startActivity(intent);
            }
        });
    }

}