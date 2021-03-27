package com.example.easygo.Passengers_UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easygo.HelperClass;
import com.example.easygo.R;
import com.example.easygo.Ticket_Booking_Master.RouteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PickUpRouteActivity extends AppCompatActivity {

    RecyclerView recyclerRoute;
    DatabaseReference routesRef;
    List<RouteModel> routesModelList;
    String from, to;
    TextView tvNoData;
    ImageButton goBack;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_route);
        recyclerRoute = findViewById(R.id.recyclerRoutes);
        goBack = findViewById(R.id.goBack);
        Intent intent = getIntent();
        tvNoData = findViewById(R.id.noData);
        from = intent.getExtras().getString("from");
        to = intent.getExtras().getString("to");
        selectedDate = intent.getStringExtra("date");
        routesModelList = new ArrayList<>();
        recyclerRoute.setLayoutManager(new LinearLayoutManager(this));
        routesRef = FirebaseDatabase.getInstance().getReference("Route Details");
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getRoutes();
    }

    private void getRoutes(){
        routesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    boolean departureMatch = false;
                    boolean arrivalMatch = false;
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        RouteModel routeModel = dataSnapshot.getValue(RouteModel.class);
                        routeModel.setKey(dataSnapshot.getKey());

                        if (routeModel.getSelectVehicle().toLowerCase().equals("bus")){
                            if (routeModel.getAddDepartureRoute().toLowerCase().contains(from.toLowerCase())){
                                departureMatch = true;
                            }else {
                                departureMatch = false;
                            }
                            if (departureMatch){
                                if (routeModel.getAddArrivalRoute().toLowerCase().contains(to.toLowerCase())){
                                    arrivalMatch = true;
                                }else {
                                    arrivalMatch = false;
                                }
                            }

                            if (departureMatch&&arrivalMatch){
                                if (routeModel.getDate().equals(selectedDate)){
                                    routesModelList.add(routeModel);
                                }
                            }
                        }

                    }

                    if (routesModelList.size()>0){
                        PickUpRoutesAdapter adapter = new PickUpRoutesAdapter(routesModelList, PickUpRouteActivity.this);
                        recyclerRoute.setAdapter(adapter);
                        recyclerRoute.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                    }else {
                        tvNoData.setVisibility(View.VISIBLE);
                        recyclerRoute.setVisibility(View.GONE);
                    }


                }catch (Exception e){
                    Toast.makeText(PickUpRouteActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}