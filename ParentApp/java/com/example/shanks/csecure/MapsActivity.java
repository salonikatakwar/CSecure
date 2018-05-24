package com.example.shanks.csecure;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FirebaseAuth mAuth;
    private String rfid,name;
    ValueEventListener listener;

    private GoogleMap mMap;
    LatLng lng=new LatLng(21.1458,79.0882);
    TextToSpeech textToSpeech;
    Address address;
    Location loc2 = new Location("");
    int i=1;
    String bus;
    String g;
    Button button;
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Marker mDriverMarker;
    LatLng pickupLocation = new LatLng(21.1633,79.0744);
    LatLng dropLocation = new LatLng(21.1760,79.0610);
    float distance,distance2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        button=(Button) findViewById(R.id.b1);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        FirebaseDatabase.getInstance().getReference().child("rcoem").child("parent").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rfid=dataSnapshot.child("Rfid").getValue().toString();
                bus=dataSnapshot.child("bus").getValue().toString();
                name=dataSnapshot.child("Name").getValue().toString();
                FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(rfid)) {
                            FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("Status").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if ("1".equals(dataSnapshot.getValue().toString())){
                                        FirebaseDatabase.getInstance().getReference().child("rcoem").child("bus").child(bus).child("students").child(rfid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                int mor= Integer.parseInt(dataSnapshot.child("morning").getValue().toString()) ;
                                                int eve= Integer.parseInt(dataSnapshot.child("evening").getValue().toString());
                                                if(mor==1 && eve==0){
                                                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("drivers").child(name).child("location").child("l").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                Toast.makeText(getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_SHORT).show();
                                                                List<Object> map = (List<Object>) dataSnapshot.getValue();
                                                                double locationLat = 0;
                                                                double locationLng = 0;
                                                                if(map.get(0) != null){
                                                                    locationLat = Double.parseDouble(map.get(0).toString());
                                                                }
                                                                if(map.get(1) != null){
                                                                    locationLng = Double.parseDouble(map.get(1).toString());
                                                                }
                                                                LatLng driverLatLng = new LatLng(locationLat,locationLng);
                                                                if(mDriverMarker != null){
                                                                    mDriverMarker.remove();
                                                                }
                                                                 dropLocation = new LatLng(21.0590,79.0306);
                                                                Location loc3 = new Location("");
                                                                loc3.setLatitude(dropLocation.latitude);
                                                                loc3.setLongitude(dropLocation.longitude);


                                                                Location loc2 = new Location("");
                                                                loc2.setLatitude(driverLatLng.latitude);
                                                                loc2.setLongitude(driverLatLng.longitude);

                                                                distance = loc3.distanceTo(loc2);

                                                                if (distance<100){
                                                                    Notification.Builder notification = new Notification.Builder(getApplicationContext())
                                                                            .setContentTitle("Arriving")
                                                                            .setContentText("in schooll")
                                                                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                                                            .setSound(Uri.parse("uri://sadfasdfasdf.mp3"))
                                                                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
                                                                    Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
                                                                    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                                                                            PendingIntent.FLAG_UPDATE_CURRENT);
                                                                    notification.setContentIntent(contentIntent);

                                                                    // Add as notification
                                                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                                    manager.notify(0, notification.build());
                                                                    textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                                        @Override
                                                                        public void onInit(int i) {
                                                                            textToSpeech.setLanguage(Locale.US);
                                                                            String utteranceId = this.hashCode() + "";
                                                                            textToSpeech.speak("child is reaching school", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                                                        }
                                                                    });

                                                                }else{
                                                                    fid();
                                                                }

                                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLatLng));
                                                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                                                                mMap.addMarker(new MarkerOptions().position(dropLocation).title("pickup").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_home)));
                                                                mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng).title("your driver").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus)));
                                                                textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                                    @Override
                                                                    public void onInit(int i) {
                                                                        textToSpeech.setLanguage(Locale.US);
                                                                        String utteranceId = this.hashCode() + "";
                                                                        textToSpeech.speak("child is reaching school", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                                                    }
                                                                });
                                                                button.setText("child reaching scholl");
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                }
                                                else if((mor==0 && eve==1)||(mor==1 && eve==1)){
                                                    FirebaseDatabase.getInstance().getReference().child("rcoem").child("drivers").child(name).child("location").child("l").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                Toast.makeText(getApplicationContext(),"there",Toast.LENGTH_SHORT).show();
                                                                List<Object> map = (List<Object>) dataSnapshot.getValue();
                                                                double locationLat = 0;
                                                                double locationLng = 0;
                                                                if(map.get(0) != null){
                                                                    locationLat = Double.parseDouble(map.get(0).toString());
                                                                }
                                                                if(map.get(1) != null){
                                                                    locationLng = Double.parseDouble(map.get(1).toString());
                                                                }
                                                                LatLng driverLatLng = new LatLng(locationLat,locationLng);
                                                                if(mDriverMarker != null){
                                                                    mDriverMarker.remove();
                                                                }

                                                                Location loc1 = new Location("");
                                                                loc1.setLatitude(pickupLocation.latitude);
                                                                loc1.setLongitude(pickupLocation.longitude);


                                                                loc2.setLatitude(driverLatLng.latitude);
                                                                loc2.setLongitude(driverLatLng.longitude);
                                                                distance2 = loc1.distanceTo(loc2);

                                                                if (distance2<100){
                                                                    Notification.Builder notification = new Notification.Builder(getApplicationContext())
                                                                            .setContentTitle("Arriving")
                                                                            .setContentText("pick the child up")
                                                                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                                                            .setSound(Uri.parse("uri://sadfasdfasdf.mp3"))
                                                                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
                                                                    Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
                                                                    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                                                                            PendingIntent.FLAG_UPDATE_CURRENT);
                                                                    notification.setContentIntent(contentIntent);

                                                                    // Add as notification
                                                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                                    manager.notify(0, notification.build());
                                                                    button.setText("child is on the bus");
                                                                    textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                                        @Override
                                                                        public void onInit(int i) {
                                                                            textToSpeech.setLanguage(Locale.US);
                                                                            String utteranceId = this.hashCode() + "";
                                                                            textToSpeech.speak("child is reaching stop", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                                                        }
                                                                    });

                                                                }else{
                                                                    fid();
                                                                }

                                                                mMap.addMarker(new MarkerOptions().position(pickupLocation).title("school").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_home)));
                                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLatLng));
                                                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                                                                mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng).title("your driver").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus)));
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(),"NOt",Toast.LENGTH_SHORT).show();
                                                }


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"NOt there",Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"NOt there",Toast.LENGTH_SHORT).show();

                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fid() {

        Notification.Builder notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Started")
                .setContentText("Child is there on the bus")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Uri.parse("uri://sadfasdfasdf.mp3"))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(loc2.getLatitude(), loc2.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
                textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int i) {
                        if (i != TextToSpeech.ERROR) {
                            textToSpeech.setLanguage(Locale.US);
                            String utteranceId = this.hashCode() + "";
                            textToSpeech.speak(address.getSubLocality(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                            button.setText(address.getSubLocality().toString()+"\n Diatance: "+String.valueOf(distance));

                        }
                    }
                });
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void onclick(View view) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }


}




