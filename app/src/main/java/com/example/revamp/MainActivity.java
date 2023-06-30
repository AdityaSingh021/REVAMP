package com.example.revamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private EditText et1;
    private EditText et2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView tv;
    private View login;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        mAuth = FirebaseAuth.getInstance();
        login=findViewById(R.id.login);
        mUser=mAuth.getCurrentUser();
        tv=findViewById(R.id.signUp);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
                finish();
            }
        });
;
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "clicked!", Toast.LENGTH_SHORT).show();
                        String t1 = et1.getText().toString();
                        String t2 = et2.getText().toString();
                        if (!(t1.isEmpty()) && !(t2.isEmpty())) {

                            mAuth.signInWithEmailAndPassword(t1, t2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userId = user.getUid();
                                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String name= (String) snapshot.child("users").child(userId).child("username").getValue();
                                                SharedPreferences details=getSharedPreferences("Details",MODE_PRIVATE);
                                                SharedPreferences.Editor details_edit=details.edit();
                                                details_edit.putString("Name",name);
                                                details_edit.putString("Email",t1);
                                                details_edit.commit();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        SharedPreferences sp= getSharedPreferences("Login",MODE_PRIVATE);
                                        SharedPreferences.Editor edit= sp.edit();
                                        edit.putInt("Status",1);
                                        edit.putString("uId",userId);
                                        edit.commit();
                                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }



        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Login");
//
//
//        myRef.setValue("1");
    }