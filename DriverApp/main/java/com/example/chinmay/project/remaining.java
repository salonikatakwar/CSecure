package com.example.chinmay.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class remaining extends Activity implements SwipeRefreshLayout.OnRefreshListener,NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    ArrayList<String> list1,list2,list3,list4,list5,list6,add;

    DatabaseReference databaseReference,datacount;
    SwipeRefreshLayout mySwipeRefreshLayout;
    MyAdapter adapter;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_remaining);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.my1t));
     //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        lv=(ListView)findViewById(R.id.listrem);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        add = new ArrayList<>();
        mySwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Students yet to come");
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //  mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(remaining.this, MapsActivity.class);
                startActivity(i);
            }
        });
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        databaseReference= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students");

        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list1.clear();
                list1.clear();
                list2.clear();
                list3.clear();
                list4.clear();
                list5.clear();
                list6.clear();
                add.clear();

                for (DataSnapshot myitem:dataSnapshot.getChildren())

                {
                    if((myitem.child("sendSMS").getValue().toString().equals("1"))&&(myitem.child("coming evening").getValue().toString().equals("1"))&&(myitem.child("evening").getValue().toString().equals("0"))){
                        list1.add(myitem.child("name").getValue().toString());
                        list2.add(myitem.child("latitude").getValue().toString());
                        list3.add(myitem.child("longitude").getValue().toString());
                        list5.add(myitem.child("class").getValue().toString());
                        list6.add(myitem.child("gender").getValue().toString());
                        add.add(myitem.child("address").getValue().toString());
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new MyAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,  list1,add,list5,list6);

                lv.setAdapter(adapter);

            }

        },500);
        mySwipeRefreshLayout.setOnRefreshListener(this);


    }

    @Override
    public void onRefresh() {
        mySwipeRefreshLayout.setRefreshing(true);

        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.morning) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent i = new Intent(remaining.this, Morn.class);

            finish();
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.evening) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent i = new Intent(remaining.this, even.class);

            finish();
            startActivity(i);

        } else if (id == R.id.remaining) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent i = new Intent(remaining.this, remaining.class);

            finish();
            startActivity(i);

        } else if (id == R.id.Add) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent i = new Intent(remaining.this, Manualmode.class);


            startActivity(i);

        } else if (id == R.id.developer) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

        } else if (id == R.id.contact) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

        }


        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
