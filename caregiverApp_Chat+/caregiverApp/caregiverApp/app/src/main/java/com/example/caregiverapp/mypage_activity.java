package com.example.caregiverapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class mypage_activity extends AppCompatActivity {


    private Button mypageregister;
    private Button mypageregister1;
    private Button back;
    private ImageView iv_profile;
    private Uri imageUri;

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



    private FirebaseAuth mFirebaseAuth;         //파이어베이스 인증
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ittdo");

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

        back = findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mypage_activity.this,sub_activity.class);
                startActivity(intent);
            }
        });


        iv_profile=findViewById(R.id.iv_profile);

        //프로필 이미지 업로드
        iv_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                activityResult.launch(galleryIntent);
            }
        });

        mypageregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAccount account = new UserAccount();

                //프로필 이미지 코드

                if(imageUri!=null){
                    StorageReference fileRef=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
                    fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //문제다..
                                    account.setProfile(uri.toString());

                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                    iv_profile.setImageResource(R.drawable.ic_launcher_background);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mypage_activity.this,"업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else { }


                iv_profile.setVisibility(View.VISIBLE);

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
                Double dpay =Double.valueOf(mypay);
                account.setUserpay(dpay);

                String disease = editText_disease.getText().toString().trim();
                editText_disease.setVisibility((View.INVISIBLE));
                textView_disease.setText((disease));
                textView_disease.setVisibility(View.VISIBLE);
                account.setUserdisease(disease);

                // 텍스트뷰 스크롤바 설정
                textView_disease.setMovementMethod(new ScrollingMovementMethod());

                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(myaddress);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(myday);
//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).push().setValue(mytime);


            }
        });
        mypageregister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });

    }


    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK&&result.getData()!=null){
                        imageUri=result.getData().getData();
                        iv_profile.setImageURI(imageUri);
                    }
                }
            }
    );

    private void uploadToFirebase(Uri uri){

    }


    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}