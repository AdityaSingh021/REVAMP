package com.example.revamp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.revamp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    ActivityMainBinding binding;
    public static SharedPreferences set;
    public static String uId;
    public int check=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        set=getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
        uId=sp.getString("uId","");
        Starting_window a=new Starting_window();
        a.finish();

        getSupportFragmentManager().beginTransaction().replace(R.id.FL,new DashboardFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.info:
                            selectedFragment = new InfoFragment();
                            break;
                        case R.id.user:
                            selectedFragment = new AccountFragment();
                            break;
                        case R.id.logout:
//                            selectedFragment = new LogOut();
                            check=0;
                            new AlertDialog.Builder(Dashboard.this)
                                    .setTitle("Logout")
                                    .setMessage("Would you like to logout?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            check=1;
                                            FirebaseAuth mAuth=FirebaseAuth.getInstance();
                                            mAuth.getInstance().signOut();
                                            SharedPreferences.Editor edit= Dashboard.set.edit();
                                            edit.putInt("Status",0);
                                            edit.commit();
//                                            Dashboard dashboard= (Dashboard) getApplicationContext();
                                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(i);
                                            finish();


                                            // logout
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // user doesn't want to logout
                                        }
                                    })
                                    .show();
//                            Dashboard dashboard= (Dashboard) getApplicationContext();
//                            if(check==1){
//                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            }


                            return true;
                        default:
                            selectedFragment = new DashboardFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction(). replace(R.id.FL,
                            selectedFragment) .commit();
                    return true;
                }

    };

















//
////        replace(new DashboardFragment());
//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.dashboard:
//                    replace(new DashboardFragment());
//                    break;
//                case R.id.info:
//                    replace(new InfoFragment());
//                    break;
//                case R.id.user:
//                    replace(new AccountFragment());
//                    break;
//                default:
//                    replace(new DashboardFragment());
//            }
//            return true;
//        });



    public void replace(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FL,fragment);
    }
}

