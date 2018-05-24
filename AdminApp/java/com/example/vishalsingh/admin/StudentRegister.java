package com.example.vishalsingh.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentRegister extends AppCompatActivity {

    EditText name,street,stop,rfid;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        name=(EditText)findViewById(R.id.name);
        street=(EditText)findViewById(R.id.street);
        stop=(EditText)findViewById(R.id.stop);
        rfid=(EditText) findViewById(R.id.rfid);
        next=(Button)findViewById(R.id.email_sign_in_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentRegister.this,ParentRegister.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("street",street.getText().toString());
                intent.putExtra("stop",stop.getText().toString());
                intent.putExtra("rfid",rfid.getText().toString());
                startActivity(intent);
            }
        });


    }
}
