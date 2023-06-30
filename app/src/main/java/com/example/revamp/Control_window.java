package com.example.revamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Control_window extends AppCompatActivity {
    ImageView on;
    ImageView off;
    ImageView on_led2;
    ImageView off_led2;
    ImageView bulb_1_on;
    ImageView bulb_1_off;
    TextView temperature;
    TextView humidity;
    ImageView back;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_window);

        on=findViewById(R.id.bulb1_on_button);
        off=findViewById(R.id.bulb1_off);
        on_led2=findViewById(R.id.bulb2);
//        off_led2=findViewById(R.id.bulb4);
        bulb_1_on=findViewById(R.id.bulb1_on);
        bulb_1_off=findViewById(R.id.bulb1);
        temperature=findViewById(R.id.celsius);
        humidity=findViewById(R.id.humidity_value);
        back=findViewById(R.id.back_button);
        ImageView bulb_2_on=findViewById(R.id.bulb2);
        ImageView bulb2_off=findViewById(R.id.bulb2_off);
        ImageView fan1=findViewById(R.id.fan1_on);
        ImageView fan1_off=findViewById(R.id.fan1_off);
        ImageView fan2_on=findViewById(R.id.fan2_on);
        ImageView fan2_off=findViewById(R.id.fan2_off);
        ImageView bulb2_on_button=findViewById(R.id.bulb2_on_button);
        ImageView bulb2_off_button=findViewById(R.id.bulb2_off_button);
        ImageView fan1_on_button=findViewById(R.id.fan1_on_button);
        ImageView fan1_off_button=findViewById(R.id.fan1_off_button);
        ImageView fan2_on_button=findViewById(R.id.fan2_on_button);
        ImageView fan2_off_button=findViewById(R.id.fan2_off_button);
        fan2_on.setVisibility(View.INVISIBLE);
        fan1.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
//                finish();
            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://revamp1-7b80b-default-rtdb.firebaseio.com");
        databaseReference.child("FirebaseIOT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                humidity.setText(snapshot.child("humidity").getValue(Integer.class).toString());
                temperature.setText(snapshot.child("temperature").getValue(Integer.class).toString()+"°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences sp= getApplicationContext().getSharedPreferences("Button_status",MODE_PRIVATE);
        int check=sp.getInt("led1",0);
        int check1=sp.getInt("led2",0);
        if(check==0){
            on.setVisibility(View.INVISIBLE);
            bulb_1_off.setVisibility(View.VISIBLE);
            bulb_1_on.setVisibility(View.INVISIBLE);
        }
        else{
            off.setVisibility(View.INVISIBLE);
            bulb_1_off.setVisibility(View.INVISIBLE);
            bulb_1_on.setVisibility(View.VISIBLE);
        }
        if(check1==0){
//            on_led2.setVisibility(View.INVISIBLE);
        }
        else{
//            off_led2.setVisibility(View.INVISIBLE);
        }


        fan1_on_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fan1_on_button.setVisibility(View.INVISIBLE);
                fan1_off_button.setVisibility(View.VISIBLE);
                fan1_off.setVisibility(View.VISIBLE);
                fan1.setVisibility(View.INVISIBLE);
            }
        });
        fan1_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fan1_on_button.setVisibility(View.VISIBLE);
                fan1_off_button.setVisibility(View.INVISIBLE);
                fan1_off.setVisibility(View.INVISIBLE);
                fan1.setVisibility(View.VISIBLE);
            }
        });
        fan2_on_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fan2_on_button.setVisibility(View.INVISIBLE);
                fan2_off_button.setVisibility(View.VISIBLE);
                fan2_off.setVisibility(View.VISIBLE);
                fan2_on.setVisibility(View.INVISIBLE);
            }
        });
        fan2_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fan2_on_button.setVisibility(View.VISIBLE);
                fan2_off_button.setVisibility(View.INVISIBLE);
                fan2_off.setVisibility(View.INVISIBLE);
                fan2_on.setVisibility(View.VISIBLE);
            }
        });
        bulb2_on_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp1= getApplicationContext().getSharedPreferences("Button_status",MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit= sp1.edit();
                edit.putInt("led2",0);
                edit.apply();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("FirebaseIOT");
                databaseReference.child("led2").setValue(0);
                bulb2_on_button.setVisibility(View.INVISIBLE);
                bulb2_off_button.setVisibility(View.VISIBLE);
                bulb_2_on.setVisibility(View.INVISIBLE);
                bulb2_off.setVisibility(View.VISIBLE);
            }
        });
        bulb2_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp1= getApplicationContext().getSharedPreferences("Button_status",MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit= sp1.edit();
                edit.putInt("led2",1);
                edit.apply();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("FirebaseIOT");
                databaseReference.child("led2").setValue(1);
                bulb2_on_button.setVisibility(View.VISIBLE);
                bulb2_off_button.setVisibility(View.INVISIBLE);
                bulb_2_on.setVisibility(View.VISIBLE);
                bulb2_off.setVisibility(View.INVISIBLE);
            }
        });

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp1=getApplicationContext().getSharedPreferences("Button_status",MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit= sp1.edit();
                edit.putInt("led1",0);
                edit.apply();
                int check1=sp1.getInt("led1",0);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("FirebaseIOT");
                databaseReference.child("led1").setValue(0);

                off.setVisibility(View.VISIBLE);
                on.setVisibility(View.INVISIBLE);
                bulb_1_off.setVisibility(View.VISIBLE);
                bulb_1_on.setVisibility(View.INVISIBLE);
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp1= getApplicationContext().getSharedPreferences("Button_status",MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit= sp1.edit();
                edit.putInt("led1",1);
                edit.apply();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("FirebaseIOT");
                databaseReference.child("led1").setValue(1);
                on.setVisibility(View.VISIBLE);
                off.setVisibility(View.INVISIBLE);
                bulb_1_off.setVisibility(View.INVISIBLE);
                bulb_1_on.setVisibility(View.VISIBLE);
            }
        });
//        bulb_2_on.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                on_led2.setVisibility(View.INVISIBLE);
////                off_led2.setVisibility(View.VISIBLE);
//            }
//        });
//        bulb2_off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                on_led2.setVisibility(View.VISIBLE);
////                off_led2.setVisibility(View.INVISIBLE);
//            }
//        });
//        off_led2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}