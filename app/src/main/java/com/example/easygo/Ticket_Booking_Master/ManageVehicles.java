package com.example.easygo.Ticket_Booking_Master;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easygo.Passengers_UI.UserHome;
import com.example.easygo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.*;

public class ManageVehicles extends AppCompatActivity implements View.OnClickListener{
    ManageVehicleAdapter manageVehicleAdapter;
    List<vehiclesModel> vehiclesModelList;
    List<String> stringList;
    ImageView goBack;
    Button uploadImage;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseReference vehiclesRef;
    ImageView addImage;
    String fileUrls;
    Uri imageUri;
    StorageReference storageRef=FirebaseStorage.getInstance().getReference().child("images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicles);
        String text=getIntent().getExtras().getString("vehicles");
//        makeText(this, ""+text, LENGTH_SHORT).show();
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(this);
        vehiclesModelList=new ArrayList<>();
        stringList=new ArrayList<>();
        fileUrls="";
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        vehiclesRef= FirebaseDatabase.getInstance().getReference("Vehicles");
        getVehicles();
    }

private void getVehicles(){
       manageVehicleAdapter=new ManageVehicleAdapter(vehiclesModelList,this);
       recyclerView.setAdapter(manageVehicleAdapter);
       vehiclesRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               vehiclesModelList.clear();
               for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                   vehiclesModel vm =  postSnapshot.getValue(vehiclesModel.class);
                   vehiclesModelList.add(vm);
                   stringList.add(snapshot.getKey());
                   manageVehicleAdapter.notifyDataSetChanged();

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(ManageVehicles.this, "Confirm Cancellation ", Toast.LENGTH_SHORT).show();
           }
       });
   }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                fab.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view=LayoutInflater.from(this).inflate(R.layout.add_new_vehicle_dialog,null);
                EditText etText= view.findViewById(R.id.etText);
                Button btnCancel= view.findViewById(R.id.cancel);
                ImageButton btnUploadImg = view.findViewById(R.id.AddImage);
                addImage=view.findViewById(R.id.AddImage);
                ImageView cancelAction=view.findViewById(R.id.cancelAction);
                Button btnAdd=view.findViewById(R.id.btnAdd);
                builder.setView(view);
                final AlertDialog alertDialog=builder.create();

                cancelAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        fab.setVisibility(View.VISIBLE);
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        fab.setVisibility(View.VISIBLE);
                    }
                });

                btnUploadImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"),2);

                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (imageUri!=null){
                            ProgressDialog progressDialog = new ProgressDialog(ManageVehicles.this);
                            progressDialog.setTitle("Uploading Image...");
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            File imageFile=new File(imageUri.toString());
                            storageRef.child(imageFile.getName()).putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                                storageRef.child(imageFile.getName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        fileUrls = uri.toString();
                                        String key=vehiclesRef.push().getKey();
                                        String name=etText.getText().toString();
                                        vehiclesModel VehiclesModel=new vehiclesModel();
                                        VehiclesModel.setVehicleID(key);
                                        VehiclesModel.setVehicleName(name);
                                        VehiclesModel.setDownloadurl(fileUrls);
                                        if (!etText.getText().toString().equals("")){
                                            vehiclesRef.child(key).setValue(VehiclesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    if (task.isSuccessful()){
                                                        makeText(ManageVehicles.this, "update vehicle", LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                        fab.setVisibility(View.VISIBLE);
                                                    }else{
                                                        makeText(ManageVehicles.this, "failed", LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            });
                        }else {
                            makeText(ManageVehicles.this, "Please select an image !", LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
            case R.id.goBack:
                startActivity(new Intent(getApplicationContext(),AdminHome.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri=data.getData();
            makeText(this, "Image selected", LENGTH_SHORT).show();
        }
    }
}


