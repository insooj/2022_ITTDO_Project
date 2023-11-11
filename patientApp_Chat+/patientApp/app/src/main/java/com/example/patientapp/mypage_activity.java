package com.example.patientapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class mypage_activity extends AppCompatActivity {

    private Button back;
    private Button mypageregister;
    private Button mypageregister1;

    private CheckBox manCheck;
    private CheckBox womanCheck;

    private EditText editText_name;
    private TextView textView_name;
    private EditText editText_old;
    private TextView textView_old;
    private TextView textView_phonenumber;
    private EditText editText_phonenumber;
    private EditText editText_day;
    private TextView textView_day;
    private EditText editText_time;
    private TextView textView_time;
    private EditText editText_address;
    private TextView textView_address;
    private EditText editText_pay;
    private TextView textView_pay;
    private EditText editText_disease;
    private TextView textView_disease;
    private EditText editText_etc;
    private TextView textView_etc;

    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증
    private DatabaseReference mDatabaseRef;
    FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mypage_activity.this,sub_activity.class);
                startActivity(intent);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ittdo");

        manCheck=findViewById(R.id.manCheck);
        womanCheck=findViewById(R.id.womanCheck);

        TableLayout sexCheck=(TableLayout)findViewById(R.id.sexCheck);
        TextView sexChecked=(TextView)findViewById(R.id.sexChecked);

        mypageregister = findViewById(R.id.btn_my_register);
        mypageregister1 = findViewById(R.id.btn_my_register1);

        editText_name=findViewById(R.id.tv_name);
        textView_name=findViewById(R.id.view_tv_name);

        editText_old = findViewById(R.id.et_old);
        textView_old = findViewById(R.id.view_tv_old);

        editText_phonenumber= findViewById(R.id.et_phonenumber);
        textView_phonenumber = findViewById(R.id.view_tv_phonenumber);

        editText_address = findViewById(R.id.et_address);
        textView_address = findViewById(R.id.view_tv_address);

        editText_day = findViewById(R.id.et_day);
        textView_day = findViewById(R.id.view_tv_day);

        editText_time = findViewById(R.id.et_time);
        textView_time = findViewById(R.id.view_tv_time);

        editText_disease = findViewById(R.id.et_disease);
        textView_disease = findViewById(R.id.view_tv_disease);

        editText_pay = findViewById(R.id.et_pay);
        textView_pay = findViewById(R.id.view_tv_pay);

        editText_etc=findViewById(R.id.et_etc);
        textView_etc=findViewById(R.id.view_tv_etc);

        //프로필 이미지 업로드

        mypageregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patientAccount account = new patientAccount();

                //성별 체크박스
                String checked=Check(manCheck,womanCheck);

                sexCheck.setVisibility(View.INVISIBLE);
                sexChecked.setText(checked);
                sexChecked.setVisibility(View.VISIBLE);
                account.setSex(checked);

                String username = editText_name.getText().toString().trim();
                editText_name.setVisibility(View.INVISIBLE);
                textView_name.setText(username);
                textView_name.setVisibility(View.VISIBLE);
                account.setUserName(username);

                String myold = editText_old.getText().toString().trim();
                editText_old.setVisibility(View.INVISIBLE);
                textView_old.setText(myold);
                textView_old.setVisibility(View.VISIBLE);
                account.setUserold(myold);

                String myphonenumber = editText_phonenumber.getText().toString().trim();
                editText_phonenumber.setVisibility(View.INVISIBLE);
                textView_phonenumber.setText(myphonenumber);
                textView_phonenumber.setVisibility(View.VISIBLE);
                account.setUserPhone(myphonenumber);

                String myaddress = editText_address.getText().toString().trim();
                editText_address.setVisibility((View.INVISIBLE));
                textView_address.setText(myaddress);
                textView_address.setVisibility(View.VISIBLE);
                account.setUserRegion(myaddress);

                String myday = editText_day.getText().toString().trim();
                editText_day.setVisibility((View.INVISIBLE));
                textView_day.setText(myday);
                textView_day.setVisibility(View.VISIBLE);
                account.setUserdate(myday);

                String mytime = editText_time.getText().toString().trim();
                editText_time.setVisibility((View.INVISIBLE));
                textView_time.setText(mytime);
                textView_time.setVisibility(View.VISIBLE);
                account.setUsertime(mytime);

                String mypay = editText_pay.getText().toString().trim();
                editText_pay.setVisibility(View.INVISIBLE);
                textView_pay.setText(mypay);
                textView_pay.setVisibility(View.VISIBLE);
                Double dpay = Double.valueOf(mypay);
                account.setUserpay(dpay);

                String disease = editText_disease.getText().toString().trim();
                editText_disease.setVisibility((View.INVISIBLE));
                textView_disease.setText((disease));
                textView_disease.setVisibility(View.VISIBLE);
                account.setUserdisease(disease);

                String etc = editText_etc.getText().toString().trim();
                editText_etc.setVisibility((View.INVISIBLE));
                textView_etc.setText((etc));
                textView_etc.setVisibility(View.VISIBLE);
                account.setEtc(etc);

                mDatabaseRef.child("PatientAccount").child(firebaseUser.getUid()).setValue(account);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(myaddress);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(myday);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(mytime);


            }
        });
        mypageregister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sexChecked.setVisibility(View.INVISIBLE);
                sexCheck.setVisibility(View.VISIBLE);

                textView_name.setVisibility(View.INVISIBLE);
                editText_name.setVisibility(View.VISIBLE);

                textView_address.setVisibility(View.INVISIBLE);
                editText_address.setVisibility(View.VISIBLE);

                textView_old.setVisibility(View.INVISIBLE);
                editText_old.setVisibility(View.VISIBLE);

                textView_phonenumber.setVisibility(View.INVISIBLE);
                editText_phonenumber.setVisibility(View.VISIBLE);

                textView_day.setVisibility(View.INVISIBLE);
                editText_day.setVisibility((View.VISIBLE));

                textView_time.setVisibility((View.INVISIBLE));
                editText_time.setVisibility(View.VISIBLE);

                textView_pay.setVisibility(View.INVISIBLE);
                editText_pay.setVisibility(View.VISIBLE);

                textView_disease.setVisibility(View.INVISIBLE);
                editText_disease.setVisibility(View.VISIBLE);

                textView_etc.setVisibility(View.INVISIBLE);
                editText_etc.setVisibility(View.VISIBLE);

            }
        });

    }

    private String Check(CheckBox manCheck, CheckBox womanCheck){
        String checked="";
        if(manCheck.isChecked()){
            checked+=(manCheck.getText().toString());
        }
        if(womanCheck.isChecked()){
            checked+=(womanCheck.getText().toString());
        }
        return checked;
    }
}