 package com.example.easygo.Passengers_UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easygo.R;
import com.example.easygo.SeatModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectBusSeat extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    LinearLayout linearLayout;
    FirebaseAuth mAuth;
    List<SeatModel> seatModelList;
    DatabaseReference bookedSeatRef;
    ArrayList<Integer> ImageId=new ArrayList<>(Arrays.asList(
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1,
            R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1, R.drawable.seatno_1));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
        gridView=(GridView) findViewById(R.id.grid_view);
        linearLayout=findViewById(R.id.chooseSeats);
        bookedSeatRef= FirebaseDatabase.getInstance().getReference("booked seat");
        seatModelList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        seatModelList.add(new SeatModel(R.drawable.seatno_1));
        gridView.setAdapter(new SelectSeatAdapter(seatModelList,this));
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (!seatModelList.get(position).isSelected()){
                   int item_pos=ImageId.get(position);
                   imageView = (ImageView) view;
                   seatModelList.get(position).setSelected(true);
                   ShowDialogBox(item_pos, position);
               }

           }
       });

    }

    public void  ShowDialogBox(int item_pos, int poition){
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.select_seat_dialog);
        TextView seatNo=dialog.findViewById(R.id.seat_No);
        ImageView male=dialog.findViewById(R.id.male);
        ImageView female=dialog.findViewById(R.id.fe_male);
        String title=getResources().getResourceName(item_pos);
         //extracting name
        int index=title.indexOf("/");
        String name=title.substring(index+1,title.length());
        seatNo.setText(name);
        seatModelList.get(poition).setSeatId(name);
        bookedSeatRef.push().child(name);
         male.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 imageView.setImageResource(R.drawable.men_seat);
                 seatModelList.get(poition).setSelectedBy("male");
                 seatModelList.get(poition).setuId(mAuth.getCurrentUser().getUid());
                 dialog.dismiss();
                 Snackbar snackbar=Snackbar.make(linearLayout,"want to cancel",Snackbar.LENGTH_LONG)
                         .setAction("Undo", new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 imageView.setImageResource(R.drawable.seatno_1);
                             }

                         }).setActionTextColor(Color.RED);


                 snackbar.show();
             }
         });
         female.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 imageView.setImageResource(R.drawable.women_seat);
                 seatModelList.get(poition).setSelectedBy("female");
                 dialog.dismiss();
                 Snackbar snackbar=Snackbar.make(linearLayout,"want to cancel",Snackbar.LENGTH_LONG)
                         .setAction("Undo", new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 imageView.setImageResource(R.drawable.seatno_1);
                             }

                         }).setActionTextColor(Color.RED);


                 snackbar.show();
             }
         });

     dialog.show();

    }
    public void goBack(View view) {
        finish();
    }
}