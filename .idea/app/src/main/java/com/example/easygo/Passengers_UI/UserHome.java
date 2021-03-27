package com.example.easygo.Passengers_UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.chootdev.recycleclick.RecycleClick;
import com.example.easygo.Login;
import com.example.easygo.R;
import com.example.easygo.Ticket_Booking_Master.AdminHome;
import com.example.easygo.Ticket_Booking_Master.AdminHomeAdapter;
import com.example.easygo.Ticket_Booking_Master.ManageRoute;
import com.example.easygo.Ticket_Booking_Master.ManageVehicleAdapter;
import com.example.easygo.Ticket_Booking_Master.SingleListAdapter;
import com.example.easygo.Ticket_Booking_Master.vehiclesModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView gmailLink;
    List<vehiclesModel> vehiclesModelList;
    RecyclerView recyclerVehicleView;
    DatabaseReference vehiclesRef;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    userHomeAdapter userHomeAdapter;
    FirebaseAuth mAuth;
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        //add
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        AdView mAdView  = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        sliderView=findViewById(R.id.imageSlider);
        List<Integer> images=new ArrayList<>();
        images.add(R.drawable.slide1);
        images.add(R.drawable.slide2);
        images.add(R.drawable.slide3);
        mAuth = FirebaseAuth.getInstance();
        SliderAdapter sliderAdapter=new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setAutoCycle(true);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        vehiclesRef= FirebaseDatabase.getInstance().getReference("Vehicles");
        recyclerVehicleView=findViewById(R.id.recycler_vehicle);
        vehiclesModelList=new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerVehicleView.setLayoutManager(gridLayoutManager);
        recyclerVehicleView.setAdapter(userHomeAdapter);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EasyGo");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        navigationView.bringToFront();
        actionBarDrawerToggle=new ActionBarDrawerToggle(UserHome.this,
                drawerLayout,
                toolbar,
                R.string.navigation_draw_open,
                R.string.navigation_draw_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();
        getVehiclesList();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    //vehicle model
    private void getVehiclesList(){
        vehiclesModelList.clear();
        vehiclesRef= FirebaseDatabase.getInstance().getReference("Vehicles");
        vehiclesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    vehiclesModel vehiclesModel=data.getValue(vehiclesModel.class);
                    vehiclesModelList.add(vehiclesModel);
                }
                userHomeAdapter adapter=new userHomeAdapter(vehiclesModelList,UserHome.this);
                recyclerVehicleView.setAdapter(adapter);
                RecycleClick.addTo(recyclerVehicleView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                        if (vehiclesModelList.get(i).getVehicleName().equals("Bus")){
                            Intent intent=new Intent(UserHome.this,SelectRoute.class);
                            intent.putExtra("type","Bus");
                            startActivity(intent);
                        }
                        else if (vehiclesModelList.get(i).getVehicleName().equals("Train")){
                            Intent intent=new Intent(UserHome.this,SelectRoute.class);
                            intent.putExtra("type","Train");
                            startActivity(intent);
                        }
                        else if (vehiclesModelList.get(i).getVehicleName().equals("Flight")){
                            Intent intent=new Intent(UserHome.this,SelectRoute.class);
                            intent.putExtra("type","Flight");
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserHome.this, "Confirm Cancellation ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //nav click

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),UserHome.class));
                break;

            case R.id.about:
                startActivity(new Intent(getApplicationContext(),AboutUs.class));
                break;
            case R.id.share:
                ApplicationInfo api=getApplicationContext().getApplicationInfo();
                String apkpath=api.sourceDir;
                Intent shareIntent=new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("application/vnd.android.package-archive ");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                startActivity(Intent.createChooser(shareIntent,"share via"));
                break;
            case R.id.logout:

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_warning)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mAuth.signOut();
                                Intent intent = new Intent(UserHome.this, Login.class);
                                startActivity(intent);
                                System.exit(0);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();

                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;









    }
}