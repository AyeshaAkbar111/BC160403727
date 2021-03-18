package com.example.easygo.Ticket_Booking_Master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.chootdev.recycleclick.RecycleClick;
import com.example.easygo.Login;
import com.example.easygo.Passengers_UI.UserHome;
import com.example.easygo.R;

import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {
    AdminHomeAdapter adminHomeAdapter;
    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        recyclerView = findViewById(R.id.recyclerview);
        goBack=findViewById(R.id.goBack);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        titles.add("Manage Vehicles");
        titles.add("Manage Route");
        titles.add("Manage Tickets");
        titles.add("Manage Reservation");
        titles.add("Manage Transaction");
        titles.add("Manage Time-Table");
        titles.add("Manage Users");
        titles.add("Manage Admin");
        images.add(R.drawable.vehicle);
        images.add(R.drawable.manageroute);
        images.add(R.drawable.ticket);
        images.add(R.drawable.reservation);
        images.add(R.drawable.transaction);
        images.add(R.drawable.tabletime);
        images.add(R.drawable.user);
        images.add(R.drawable.user);
        adminHomeAdapter = new AdminHomeAdapter(this, titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adminHomeAdapter);
        RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (titles.get(position).equals("Manage Vehicles")) {
                    Intent intent = new Intent(AdminHome.this, ManageVehicles.class);
                    intent.putExtra("vehicles", titles.get(position));
                    startActivity(intent);

                } else if (titles.get(position).equals("Manage Route")) {
                    Intent intent = new Intent(AdminHome.this, ManageRoute.class);
                    intent.putExtra("Route", titles.get(position));
                    startActivity(intent);

                } else if (titles.get(position).equals("Manage Tickets")) {

                } else if (titles.get(position).equals("Manage Reservation")) {

                } else if (titles.get(position).equals("Manage Transaction")) {

                } else if (titles.get(position).equals("Manage Time-Table")) {

                } else if (titles.get(position).equals("Manage Users")) {

                } else if (titles.get(position).equals("Manage Admin")) {

                }


            }
        });


    }


    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), UserHome.class));
    }
}