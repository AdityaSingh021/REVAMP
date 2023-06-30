package com.example.revamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Starting_window extends AppCompatActivity {
    ImageView top;
    ImageView bottom;
    ImageView logo;
    TextView tag;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_window);
        top=findViewById(R.id.top);
        bottom=findViewById(R.id.bottom);
        logo=findViewById(R.id.logo);
        tag=findViewById(R.id.tag);
//        logo.setVisibility(View.INVISIBLE);
        top.animate().translationY(2300).setDuration(2000).start();
        bottom.animate().translationY(-2300).setDuration(2000).start();
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        logo.startAnimation(aniFade);
        tag.startAnimation(aniFade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                int check=sp.getInt("Status",0);
                if(check==0){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(intent);
                }
            }
        },2000);

//        logo.setVisibility(View.VISIBLE);
    }
}
