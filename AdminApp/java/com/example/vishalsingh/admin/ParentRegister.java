package com.example.vishalsingh.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentRegister extends AppCompatActivity {

    EditText parentname,contact,email,password;
    Button register;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        parentname=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        register=(Button)findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=getIntent();
                String nameparent=parentname.getText().toString();
                String rf=i.getStringExtra("rfid");
                mdatabase= FirebaseDatabase.getInstance().getReference().child("rcoem");
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("count").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("longitude").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("latitude").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("evening").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("morning").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("pickup").setValue(i.getStringExtra("stop"));
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("sendSMS").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("parent").setValue(parentname.getText().toString());
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("coming morning").setValue(0);
                mdatabase.child("bus").child("bus2").child("students").child(rf).child("coming evening").setValue(0);

                mdatabase.child("bus").child("bus1").child("students").child(rf).child("name").setValue(i.getStringExtra("studentname"));
                mdatabase.child("parent").child(nameparent).child("Phno").setValue(contact.getText().toString());

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(ParentRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("parent", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("parentsignup", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(ParentRegister.this, "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}
