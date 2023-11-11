package com.example.patientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class list_activity extends AppCompatActivity implements RecyclerViewInterface {

    private TextView salary_avg;
    private ArrayList<Double> salaryList=new ArrayList<>();

    private TextView SearchAvg;
    private ArrayList<Double> SearchAVGList=new ArrayList<>();

    private RecyclerView recyclerView;
    private SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserAccount> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ittdo").child("UserAccount");

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        salary_avg=findViewById(R.id.salary_avg);
        SearchAvg=findViewById(R.id.salary_avg);

        searchView=findViewById(R.id.searchView);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        database=FirebaseDatabase.getInstance();

        databaseReference=database.getReference("ittdo").child("UserAccount");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserAccount userAccount=snapshot1.getValue(UserAccount.class);
                    arrayList.add(userAccount);

                    double salaryobj=userAccount.getUserpay();
                    salaryList.addAll(Collections.singleton(salaryobj));

                    double sum=0.0;
                    for(Double i:salaryList){
                        sum+=i;
                    }
                    double average=sum/salaryList.size();

                    String averageStr=String.valueOf(average);
                    salary_avg.setText(averageStr);
                }

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
        adapter=new CGAdapter(arrayList,this,this);

        recyclerView.setAdapter(adapter);


    }

    private void search(String str) {
        ArrayList<UserAccount> searchList = new ArrayList<>();
        searchList.clear();
        SearchAVGList.clear();
        for (UserAccount object : arrayList) {
            if ((object.getUserRegion().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUserdate().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUsertime().toLowerCase().contains(str.toLowerCase()))
                    || (object.getUserdisease().toLowerCase().contains(str.toLowerCase()))) {
                searchList.add(object);

                double SearchSalary=object.getUserpay();
                SearchAVGList.addAll(Collections.singleton(SearchSalary));
            }
            double sum=0.0;
            for(Double i:SearchAVGList){
                sum+=i;
            }
            double average=sum/SearchAVGList.size();

            String averageStr=String.valueOf(average);
            salary_avg.setText(averageStr);

        }
        CGAdapter cgAdapter = new CGAdapter(searchList, this, this);
        recyclerView.setAdapter(cgAdapter);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent= new Intent(this,Chat_Activity.class);
        startActivity(intent);
    }

}