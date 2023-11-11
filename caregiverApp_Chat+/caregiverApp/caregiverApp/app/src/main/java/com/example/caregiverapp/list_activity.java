package com.example.caregiverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class list_activity extends AppCompatActivity implements RecyclerViewInterface {

    private TextView salary_avg;
    private ArrayList<Double> salaryList=new ArrayList<>();

    private RecyclerView recyclerView;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PatientAccount> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ittdo").child("PatientAccount");

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        salary_avg=findViewById(R.id.salary_avg);
        searchView=findViewById(R.id.searchView);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        database=FirebaseDatabase.getInstance();

        databaseReference=database.getReference("ittdo").child("PatientAccount");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    PatientAccount patientAccount =snapshot1.getValue(PatientAccount.class);
                    arrayList.add(patientAccount);
                    double salaryobj=patientAccount.getUserpay();

                    salaryList.addAll(Collections.singleton(salaryobj));
                }
                double sum=0.0;
                for(Double i:salaryList){
                    sum+=i;
                }
                double average=sum/salaryList.size();
                String averageStr=String.valueOf(average);
                salary_avg.setText(averageStr);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("list_activity",String.valueOf(error.toException()));
            }
        });

        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }


        adapter=new PTAdapter(arrayList,this,this);

        recyclerView.setAdapter(adapter);


    }

    private void search(String str){
        ArrayList<PatientAccount> searchList=new ArrayList<>();

        // 리사이클러뷰 중복 현상 해결!
        for(PatientAccount object:arrayList){
            if ((object.getUserRegion().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUserdate().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUsertime().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUserdisease().toLowerCase().contains(str.toLowerCase()))) {
                searchList.add(object);
            }

        }

        PTAdapter ptAdapter =new PTAdapter(searchList,this,this);
        recyclerView.setAdapter(ptAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent= new Intent(this,Chat_Activity.class);
        startActivity(intent);
    }
}