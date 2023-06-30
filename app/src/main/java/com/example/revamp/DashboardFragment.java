package com.example.revamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    View cv1;
    View cv2;
    View cv3;
    View cv4;
    TextView tv;
    ImageView image1;
    CircleImageView user_img;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://revamp1-7b80b-default-rtdb.firebaseio.com");
        View v=inflater.inflate(R.layout.fragment_dashboard, container, false);
        image1=v.findViewById(R.id.user_img);
        cv1=v.findViewById(R.id.cv1);
        cv2=v.findViewById(R.id.cv2);
        cv3=v.findViewById(R.id.cv3);
        cv4=v.findViewById(R.id.cv4);
        tv=v.findViewById(R.id.tv1);
        user_img=v.findViewById(R.id.user_img);
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String filePath = prefs.getString("profile_picture_path", null);

        // Load the Bitmap from the file
        if (filePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            user_img.setImageBitmap(bitmap);
        }
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv.setText(snapshot.child("users").child(Dashboard.uId).child("username").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        YoYo.with(Techniques.SlideInLeft).duration(1000).repeat(0).playOn(cv1);
        YoYo.with(Techniques.SlideInLeft).duration(1000).repeat(0).playOn(cv2);
        YoYo.with(Techniques.SlideInLeft).duration(1000).repeat(0).playOn(cv3);
        YoYo.with(Techniques.SlideInLeft).duration(1000).repeat(0).playOn(cv4);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Control_window.class);
                startActivity(intent);
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Control_window.class);
                startActivity(intent);
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Control_window.class);
                startActivity(intent);
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Control_window.class);
                startActivity(intent);
            }
        });
        return v;


    }
}