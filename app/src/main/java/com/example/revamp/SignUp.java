package com.example.revamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    android.support.v7.widget.CardView signUp;
    TextView Name;
    TextView Email;
    TextView Password;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://revamp1-7b80b-default-rtdb.firebaseio.com");
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp=findViewById(R.id.signUp);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Toast.makeText(getApplicationContext(), "clicked!", Toast.LENGTH_SHORT).show();
                String name=Name.getText().toString();
                String t1 = Email.getText().toString();
                String t2 = Password.getText().toString();
                Toast.makeText(getApplicationContext(), t1+"   "+t2, Toast.LENGTH_SHORT).show();
                if (!(t1.isEmpty()) && !(t2.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(t1, t2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                mDatabase.child("users").child(userId).child("username").setValue(Name.getText().toString());
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                if (task.isSuccessful()) {
                                    SharedPreferences sp= getSharedPreferences("Login",MODE_PRIVATE);
                                    SharedPreferences details=getSharedPreferences("Details",MODE_PRIVATE);
                                    SharedPreferences.Editor details_edit=details.edit();
                                    details_edit.putString("Name",name);
                                    details_edit.putString("Email",name);
                                    SharedPreferences.Editor edit= sp.edit();
                                    edit.putInt("Status",1);
                                    edit.putString("uId",userId);
                                    edit.commit();
                                    details_edit.commit();
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Email or Password is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}