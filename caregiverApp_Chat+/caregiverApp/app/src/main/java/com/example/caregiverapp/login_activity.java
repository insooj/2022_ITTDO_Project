package com.example.caregiverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class login_activity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증
    private DatabaseReference mDatabaseRef;     //실시간 데이터베이스
    private EditText mEtId, mETPwd, mETName,mETuserEmail ,mETuserPhone; // 회원가입 입력필드
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_login));

        // 파이어 베이스 내용
        mFirebaseAuth = FirebaseAuth.getInstance();


        //login화면에 있는 id,Pwd 값들
        mEtId = findViewById(R.id.userid);
        mETPwd = findViewById(R.id.userPwd);

        Button btn_login = findViewById(R.id.btn_login);
        //login 버튼 클릭시 발생하는 이벤트 메서드
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청, 입력값들 변수로 넣어서
                String strId = mEtId.getText().toString().trim();
                String strPwd = mETPwd.getText().toString().trim();
                // singInWithEmailAndPassword => 파이어베이스에서 로그인정보 일치하는지 확인하는 메서드 ?

                mFirebaseAuth.signInWithEmailAndPassword(strId, strPwd).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //로그인 성공
                            Intent intent = new Intent(login_activity.this, sub_activity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        }
                        else{
                            Toast.makeText(login_activity.this,"로그인 실패하였습니다",Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }
        });



        // 회원가입 페이지로 넘어가는 Intent 처리
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this ,legister_activity.class);
                startActivity(intent);
            }
        });
    }
}