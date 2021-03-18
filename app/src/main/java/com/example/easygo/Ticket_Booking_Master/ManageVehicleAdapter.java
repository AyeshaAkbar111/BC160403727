package com.example.easygo.Ticket_Booking_Master;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ManageVehicleAdapter extends RecyclerView.Adapter <ManageVehicleAdapter.VehicleViewHolder>{
    List<vehiclesModel> vehiclesModelList;
    Context context;


    public ManageVehicleAdapter(List<vehiclesModel> vehiclesModelList, Context context) {
        this.vehiclesModelList = vehiclesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicles_recycler_item,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
      holder.vehicleName.setText(vehiclesModelList.get(position).getVehicleName());
        Picasso.get().load(vehiclesModelList.get(position).getDownloadurl()).placeholder(R.drawable.ic_default_image).fit().into(holder.imageView);
        
    }


    @Override
    public int getItemCount() {
        return vehiclesModelList.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder{
        TextView vehicleName;
        ImageView imageView;
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName=itemView.findViewById(R.id.vehicleName);
            imageView=itemView.findViewById(R.id.image);

        }
    }
}
