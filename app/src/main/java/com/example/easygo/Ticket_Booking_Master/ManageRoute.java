package com.example.easygo.Ticket_Booking_Master;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.example.easygo.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;

public class ManageRoute extends AppCompatActivity implements View.OnClickListener{
    EditText selectVehicle,addDepartureRoute,addArrivalRoute,totalSeats,routePrice;
    Button btnAddRoute,pickDate,pickTime;
    ImageView goBack;
    RadioGroup radioGroup;
    vehiclesModel vehiclesModel1;
    boolean trainSelected = false;
    TextView VehicleClass;
    DatabaseReference vehiclesRef,manageRouteRef;
    List<vehiclesModel> vehiclesModelList;
    TextView showDate,showTime,type;
     int mYear,mMonth,mDay,mHour,mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_route);
        selectVehicle=findViewById(R.id.selectVehicle);
        selectVehicle.setFocusable(false);
        selectVehicle.setFocusableInTouchMode(false);
        showTime=findViewById(R.id.showTime);
        btnAddRoute=findViewById(R.id.btnAddRoute);
        vehiclesModelList=new ArrayList<>();
        showDate=findViewById(R.id.showDate);
        pickDate=findViewById(R.id.pickDate);
        pickTime=findViewById(R.id.pickTime);
        VehicleClass=findViewById(R.id.VehicleClass);
        pickDate.setOnClickListener(this);
        pickTime.setOnClickListener(this);
        selectVehicle.setOnClickListener(this);
        btnAddRoute.setOnClickListener(this);
        addDepartureRoute=findViewById(R.id.addDepartureRoute);
        addDepartureRoute.setOnClickListener(this);
        addArrivalRoute=findViewById(R.id.addArrivalRoute);
        addArrivalRoute.setOnClickListener(this);
        totalSeats=findViewById(R.id.totalSeats);
        totalSeats.setOnClickListener(this);
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        VehicleClass.setOnClickListener(this);
        routePrice=findViewById(R.id.routePrice);
        routePrice.setOnClickListener(this);
        manageRouteRef=FirebaseDatabase.getInstance().getReference("Route Details");
        Places.initialize(getApplicationContext(),"AIzaSyCQKtaYzAH69C_aV2P6chZXcM3h0twBzeU");
        PlacesClient placesClient=Places.createClient(this);
        addDepartureRoute.setFocusable(false);
        addDepartureRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,
                       Place.Field.LAT_LNG,Place.Field.NAME);
               Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                       fieldList).build(ManageRoute.this);
               startActivityForResult(intent,100);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectVehicle:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view= LayoutInflater.from(this).inflate(R.layout.select_vehicle_dialog,null);
                RecyclerView recyclerView=view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                builder.setView(view);
                AlertDialog alertDialog=builder.create();
                alertDialog.setTitle("Select vehicle");
                getVehiclesList(recyclerView,alertDialog);
                alertDialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                alertDialog.show();
              //  alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.button_background);
                break;

           case R.id.pickTime:
               final Calendar c=Calendar.getInstance();
               mHour=c.get(Calendar.HOUR_OF_DAY);
               mMinute=c.get(Calendar.MINUTE);
               TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       String strHour=String.valueOf(showTime), strMin=String.valueOf(showTime), strAM_PM;
                       showTime.setText(hourOfDay+":"+minute);
                   }
               },mHour,mMinute,false);
               timePickerDialog.show();
               break;
            case R.id.VehicleClass:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                View view1= LayoutInflater.from(this).inflate(R.layout.select_vehicle_dialog,null);
                RecyclerView recyclerView1=view1.findViewById(R.id.recyclerview);
                recyclerView1.setLayoutManager(new LinearLayoutManager(this));
                builder1.setView(view1);
                AlertDialog alertDialog1=builder1.create();
                alertDialog1.setTitle("Select vehicle Type");
                List<String> strings=new ArrayList<>();
                strings.add("AC");
                strings.add("Non-AC");
                strings.add("Business");
                strings.add("Economy");
                VehicleTypeAdapter vehicleTypeAdapter=new VehicleTypeAdapter(strings,ManageRoute.this);
                recyclerView1.setAdapter(vehicleTypeAdapter);
                RecycleClick.addTo(recyclerView1).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                      VehicleClass.setText(strings.get(i));
                      alertDialog1.dismiss();
                    }
                });
                alertDialog1.show();
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pickDate:
                final Calendar calendar=Calendar.getInstance();
                mYear=calendar.get(Calendar.YEAR);
                mMonth=calendar.get(Calendar.MONTH);
                mDay=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        showDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
                break;
            case R.id.btnAddRoute:
            String SelectVehicle=selectVehicle.getText().toString();
            String AddDepartureRoute=addDepartureRoute.getText().toString();
            String AddArrivalRoute=addArrivalRoute.getText().toString();
            String vehicleClasse=VehicleClass.getText().toString();
            String TotalSeats=totalSeats.getText().toString();
            String TicketPrice=routePrice.getText().toString();
            String date=showDate.getText().toString();
            String time=showTime.getText().toString();

            if (TextUtils.isEmpty(SelectVehicle)){
                selectVehicle.setError("Required !");
                return;
            }
                if (TextUtils.isEmpty(AddDepartureRoute)){
                    addDepartureRoute.setError("Required !");
                    return;
                }
                if (TextUtils.isEmpty(AddArrivalRoute)){
                    addArrivalRoute.setError("Required !");
                    return;
                }
          RouteModel routeModel=new RouteModel(SelectVehicle,AddDepartureRoute,AddArrivalRoute,vehicleClasse,date,time,Double.parseDouble(TicketPrice),Integer.parseInt(TotalSeats));
              routeModel.setTimestamp(System.currentTimeMillis());
                manageRouteRef.push().setValue(routeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ManageRoute.this, "Update Route Details", Toast.LENGTH_SHORT).show();
                         finish();
                    }else {
                        Toast.makeText(ManageRoute.this, "uploading failed", Toast.LENGTH_SHORT).show();
                    }
                    
                    }
                });



                break;
            case R.id.goBack:
                startActivity(new Intent(getApplicationContext(),AdminHome.class));
                break;
        }

    }

    private void getVehiclesList(final RecyclerView recyclerView,AlertDialog alertDialog){
        vehiclesModelList = new ArrayList<>();
        vehiclesRef= FirebaseDatabase.getInstance().getReference("Vehicles");
         vehiclesRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot data: snapshot.getChildren()){
                     vehiclesModel vehiclesModel=data.getValue(vehiclesModel.class);
                     vehiclesModelList.add(vehiclesModel);
                 }
             SingleListAdapter singleListAdapter=new SingleListAdapter(vehiclesModelList,ManageRoute.this);
             recyclerView.setAdapter(singleListAdapter);
                 RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                     @Override
                     public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                         vehiclesModel vehiclesModel= vehiclesModelList.get(i);
                         selectVehicle.setText(vehiclesModel.getVehicleName());
                         alertDialog.dismiss();
                     }
                 });
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==RESULT_OK){
                Place place=Autocomplete.getPlaceFromIntent(data);
                addDepartureRoute.setText(place.getAddress());
            }else {
                Status status=Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}