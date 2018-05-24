package com.example.shanks.csecure;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup,radioGroup2;
    String rfid,bus,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.r1);
        radioGroup2=(RadioGroup) findViewById(R.id.r2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton2) {
                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("parent").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rfid = dataSnapshot.child("Rfid").getValue().toString();
                            bus = dataSnapshot.child("bus").getValue().toString();

                            FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").child(rfid).child("coming morning").setValue(0);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("parent").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rfid = dataSnapshot.child("Rfid").getValue().toString();
                            bus = dataSnapshot.child("bus").getValue().toString();

                            FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").child(rfid).child("coming morning").setValue(1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton4) {
                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("parent").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rfid = dataSnapshot.child("Rfid").getValue().toString();
                            bus = dataSnapshot.child("bus").getValue().toString();

                            FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").child(rfid).child("coming evening").setValue(0);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("parent").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rfid = dataSnapshot.child("Rfid").getValue().toString();
                            bus = dataSnapshot.child("bus").getValue().toString();

                            FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").child(rfid).child("coming evening").setValue(1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(notificationIntent);
    }
}
