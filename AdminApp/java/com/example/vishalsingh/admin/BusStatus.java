package com.example.vishalsingh.admin;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusStatus extends AppCompatActivity implements RecyclerViewAdapter.OnItemClicked ,SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listData = new ArrayList<>();
    private List<String> latitude = new ArrayList<>();
    private List<String> longitude = new ArrayList<>();
    //firebase addition
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerViewAdapter adapter;
    SwipeRefreshLayout swl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_status);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        firebaseDatabase = FirebaseDatabase.getInstance();
        swl = (SwipeRefreshLayout) findViewById(R.id.lis);

        databaseReference = firebaseDatabase.getReference().child("rcoem").child("drivers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    latitude.add(s.child("location").child("l").child("0").getValue().toString());
                    longitude.add(s.child("location").child("l").child("1").getValue().toString());
                    listData.add(new Data(s.getKey(), s.child("busno").getValue().toString(), s.child("location").child("l").child("0").getValue().toString() + "     " + s.child("location").child("l").child("1").getValue().toString(), s.child("studentcount").getValue().toString(), s.child("speed").getValue().toString(), Integer.parseInt(s.child("status").getValue().toString())));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("recycler", "Failed to read value.", error.toException());
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(
        new Runnable() {
            @Override
            public void run() {
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(BusStatus.this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerViewAdapter(listData);
                recyclerView.setAdapter(adapter);
                adapter.setOnClick(BusStatus.this);
            }
        },500);

        swl.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(int position) {
        //display the map view of the current posotion of the bus!!1
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("latitude", latitude.get(position));
        i.putExtra("longitude", longitude.get(position));
        Toast.makeText(this, "lat" + latitude.get(position) + "long:" + longitude.get(position), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    @Override
    public void onRefresh() {


        swl.setRefreshing(true);
        Intent i = getIntent();
        finish();
        startActivity(i);
        swl.setRefreshing(false);

    }

    private String getAddressFromLocation(String latitude, String longitude) throws IOException {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);


        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);

        if (addresses.size() > 0) {
            Address fetchedAddress = addresses.get(0);
            StringBuilder strAddress = new StringBuilder();
            for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
            }
            return strAddress.toString();
        }
        return "Location not found";
    }
}




