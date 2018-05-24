package com.example.vishalsingh.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegister extends AppCompatActivity {

    EditText name,route,vehicleno,contact;
    Button register;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.driverphn);
        route=(EditText)findViewById(R.id.vehicle);
        vehicleno=(EditText)findViewById(R.id.busno);
        register=(Button)findViewById(R.id.regdriver);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(name.getText().toString().length() > 0 && contact.getText().toString().length() > 0 && route.getText().toString().length() > 0 && vehicleno.getText().toString().length() > 0 )
                mdatabase= FirebaseDatabase.getInstance().getReference().child("rcoem");
                mdatabase.child("drivers").child(name.getText().toString()).child("busno").setValue(vehicleno.getText().toString());
                mdatabase.child("drivers").child(name.getText().toString()).child("busname").setValue(route.getText().toString());
                mdatabase.child("drivers").child(name.getText().toString()).child("Phno").setValue(contact.getText().toString());
                mdatabase.child("drivers").child(name.getText().toString()).child("studentcount").setValue(0);
                mdatabase.child("drivers").child(name.getText().toString()).child("location").child("l").child("0").setValue(21);
                mdatabase.child("drivers").child(name.getText().toString()).child("location").child("l").child("1").setValue(79);
                mdatabase.child("drivers").child(name.getText().toString()).child("speed").setValue(0);
                mdatabase.child("drivers").child(name.getText().toString()).child("status").setValue(0);
                Toast.makeText(DriverRegister.this, "Driver Registered", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
