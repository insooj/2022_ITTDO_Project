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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class legister_activity extends AppCompatActivity {

    private Button mBtnLoginBack;
    private Button mBtnRegister;
    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증
    private DatabaseReference mDatabaseRef;     //실시간 데이터베이스
    private EditText mEtId, mETPwd, mETName,mETuserEmail ,mETuserPhone; // 회원가입 입력필드


    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_legister));

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ittdo");


        mEtId = findViewById(R.id.newid);
        mETPwd = findViewById(R.id.newPwd);
        mETName = findViewById(R.id.username);
//
        mETuserPhone = findViewById(R.id.userPhone);
        //회원가입 버튼
        mBtnRegister = findViewById(R.id.btn_legister);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String strId = mEtId.getText().toString();
                String strPwd = mETPwd.getText().toString();
                String strName = mETName.getText().toString();
//
                String strPhone = mETuserPhone.getText().toString();


                //FirebaseAuth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strId, strPwd).addOnCompleteListener(legister_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //회원가입한 유저를 가져옴
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setUserId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setUserName(strName);
//                            account.setUserEmail(strEmail);
                            account.setUserPhone(strPhone);

                            // setValue : database에 insert 삽입 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(legister_activity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(legister_activity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // 로그인 페이지로 다시 돌아가는 Intetn 처리
        mBtnLoginBack = findViewById(R.id.btn_legisterback);
        mBtnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(legister_activity.this,login_activity.class);
                startActivity(intent);
            }
        });


    }
}