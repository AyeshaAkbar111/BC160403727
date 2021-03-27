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

public class SingleListAdapter extends RecyclerView.Adapter<SingleListAdapter.SingleViewHolder>{
    List<vehiclesModel> vehiclesModelList;
    Context context;

    public SingleListAdapter(List<vehiclesModel> vehiclesModelList, Context context) {
        this.vehiclesModelList = vehiclesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item,parent,false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.vehicleName.setText(vehiclesModelList.get(position).getVehicleName());
    }

    @Override
    public int getItemCount() {
        return vehiclesModelList.size();
    }

    public class SingleViewHolder extends RecyclerView.ViewHolder{
        TextView vehicleName;

        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName=itemView.findViewById(R.id.vehicleName);
        }
    }
}
