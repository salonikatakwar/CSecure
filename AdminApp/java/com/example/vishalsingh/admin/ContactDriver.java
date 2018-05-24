package com.example.vishalsingh.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactDriver extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    ListView lv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> listphone=new ArrayList<>();
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_driver);
        lv=(ListView)findViewById(R.id.lvdriver);
        lv.setOnItemClickListener(this);
        swl = (SwipeRefreshLayout)findViewById(R.id.swldriver);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("rcoem").child("drivers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    list.add(s.getKey()+"\n"+s.child("Phno").getValue().toString());
                    listphone.add(s.child("Phno").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("recycler", "Failed to read value.", error.toException());
            }
        });
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter=new ArrayAdapter<String>(ContactDriver.this,android.R.layout.simple_expandable_list_item_1,list);
                lv.setAdapter(adapter);
            }
        },500);

        swl.setOnRefreshListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+listphone.get(i)));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRefresh() {

        swl.setRefreshing(true);
        Intent i = getIntent();
        finish();
        startActivity(i);
        swl.setRefreshing(false);
    }
}
