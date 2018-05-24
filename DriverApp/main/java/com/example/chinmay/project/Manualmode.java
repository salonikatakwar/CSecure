package com.example.chinmay.project;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Manualmode extends Activity
{
    RadioButton r1,r2,r3,r4;
    ListView lv;
    ArrayList<String> array,array2;
    DatabaseReference databaseReference,d2;
    int flag=0;
    ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manualmode);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.my1t));
        lv=(ListView)findViewById(R.id.list2) ;

        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton)findViewById(R.id.r2);
        r3 = (RadioButton)findViewById(R.id.r3);
        r4 = (RadioButton)findViewById(R.id.r4);
        lv=(ListView)findViewById(R.id.list2);
        array=new ArrayList<>();
        array2=new ArrayList<>();



    }
    public void Select(View view)
    {
        array.clear();
        array2.clear();
         databaseReference = FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students");
        if(r1.isChecked())
        {

            flag=1;



            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot myitem:dataSnapshot.getChildren())

                    {

                        if(myitem.child("morning").getValue().toString().equals("0")) {
                            array.add(myitem.child("name").getValue().toString());
                            array2.add(myitem.getKey());
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else if(r2.isChecked())
        {

            flag=2;
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot myitem:dataSnapshot.getChildren())

                    {
                        if(myitem.child("coming morning").getValue().toString().equals("1"))
                        {

                        array.add(myitem.child("name").getValue().toString());
                            array2.add(myitem.getKey());
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if(r3.isChecked())
        {

            flag=3;

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot myitem:dataSnapshot.getChildren())

                    {
                        if((myitem.child("evening").getValue().toString().equals("0"))) {
                            array.add(myitem.child("name").getValue().toString());
                            array2.add(myitem.getKey());
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if (r4.isChecked())
        {

            flag=4;
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot myitem:dataSnapshot.getChildren())

                    {
                        if((myitem.child("evening").getValue().toString().equals("1"))) {
                            array.add(myitem.child("name").getValue().toString());
                            array2.add(myitem.getKey());
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else
        {
            flag=5;
            Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
      final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(flag<5)
                {
                    ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,array);
                    lv.setAdapter(ad);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            switch(flag)
                            {
                                case 1: d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("morning");
                                    d2.setValue(1);
                                    d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("coming morning");
                                    d2.setValue(1);
                                    d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("sendSMS");
                                    d2.setValue(1);
                                    finish();
                                    break;

                                case 2: d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("coming morning");
                                    d2.setValue(0);
                                    d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("morning");
                                    d2.setValue(0);
                                    d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("sendSMS");
                                    d2.setValue(0);
                                    finish();
                                    break;


                                case 3:d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("sendSMS");
                                    d2.setValue(1);
                                    d2=FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("coming evening");
                                    d2.setValue(1);
                                    d2=FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("evening");
                                    d2.setValue(1);
                                    finish();
                                    break;

                                case 4:d2= FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("sendSMS");
                                    d2.setValue(0);
                                    FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("coming evening");
                                    d2.setValue(0);
                                    FirebaseDatabase.getInstance().getReference("rcoem").child("bus").child("bus1").child("students").child(array2.get(position)).child("evening");
                                    d2.setValue(0);
                                    finish();
                                    break;


                            }


                        }
                    });
                }

            }
        },500);


    }
}
