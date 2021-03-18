package com.example.easygo.Ticket_Booking_Master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo.R;

import java.util.List;

public class VehicleTypeAdapter extends RecyclerView.Adapter <VehicleTypeAdapter.VehicleVHolder>{
    List<String> stringList;
    Context context;

    public VehicleTypeAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @NonNull
    @Override
    public VehicleVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item,parent,false);
        return new VehicleVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleVHolder holder, int position) {
     holder.vehicleName.setText(stringList.get(position));
  //   holder.vehicleName.setTextColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class  VehicleVHolder extends RecyclerView.ViewHolder{
        TextView vehicleName;

        public VehicleVHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName=itemView.findViewById(R.id.vehicleName);
        }
    }

}
