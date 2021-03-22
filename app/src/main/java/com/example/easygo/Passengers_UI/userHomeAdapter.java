package com.example.easygo.Passengers_UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo.R;
import com.example.easygo.Ticket_Booking_Master.vehiclesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class userHomeAdapter extends RecyclerView.Adapter<userHomeAdapter.userViewHolder> {

    List<vehiclesModel> vehiclesModelList;
     Context context;

    public userHomeAdapter(List<vehiclesModel> vehiclesModelList, Context context) {
        this.vehiclesModelList = vehiclesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_item,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
     holder.textView.setText(vehiclesModelList.get(position).getVehicleName());
     Picasso.get().load(vehiclesModelList.get(position).getDownloadurl()).fit().placeholder(R.drawable.ic_default_image).into(holder.imageView);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehiclesModelList.get(position).getVehicleName().equals("Bus")){
                    Intent intent=new Intent(context,SelectRoute.class);
                    intent.putExtra("type","Bus");
                    context.startActivity(intent);
                }
               else if (vehiclesModelList.get(position).getVehicleName().equals("Train")){
                    Intent intent=new Intent(context,SelectRoute.class);
                    intent.putExtra("type","Train");
                    context.startActivity(intent);
                }
                else if (vehiclesModelList.get(position).getVehicleName().equals("Flight")){
                    Intent intent=new Intent(context,SelectRoute.class);
                    intent.putExtra("type","Flight");
                    context.startActivity(intent);
                }


            }
        });





    }

    @Override
    public int getItemCount() {
        return vehiclesModelList.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);

        }
    }

}
