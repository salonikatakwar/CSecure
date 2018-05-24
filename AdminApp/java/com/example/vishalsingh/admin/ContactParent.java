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

public class ContactParent extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    ListView parentlv;
    ArrayAdapter<String> adap;
    ArrayList<String> listparents = new ArrayList<>();
    ArrayList<String> listphone;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SwipeRefreshLayout swl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_parent);
        parentlv = (ListView) findViewById(R.id.lvparent);
        parentlv.setOnItemClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listphone = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference().child("rcoem").child("parent");
        swl = (SwipeRefreshLayout) findViewById(R.id.swlparent);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    listphone.add(s.child("Phno").getValue().toString());
                    listparents.add(s.getKey() + "\n" + s.child("Phno").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("recycler", "Failed to read value.", error.toException());
            }
        });
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                adap = new ArrayAdapter<String>(ContactParent.this, android.R.layout.simple_expandable_list_item_1, listparents);
                parentlv.setAdapter(adap);

            }
        }, 500);
        swl.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {

        swl.setRefreshing(true);
        Intent i = getIntent();
        finish();
        startActivity(i);
        swl.setRefreshing(false);

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
}
