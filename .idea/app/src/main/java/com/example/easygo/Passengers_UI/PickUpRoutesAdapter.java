package com.example.easygo.Passengers_UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo.R;
import com.example.easygo.Ticket_Booking_Master.RouteModel;

import java.util.List;

public class PickUpRoutesAdapter extends RecyclerView.Adapter<PickUpRoutesAdapter.PVholder> {

    List<RouteModel> routeModelList;
    Context context;

    public PickUpRoutesAdapter(List<RouteModel> routeModelList, Context context) {
        this.routeModelList = routeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PVholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_detail2, parent, false);
        return new PVholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PVholder holder, int position) {
        RouteModel routeModel = routeModelList.get(position);
        holder.tvDeparture.setText(routeModelList.get(position).getAddDepartureRoute());
        holder.tvArrival.setText(routeModelList.get(position).getAddArrivalRoute());
        String fareString = routeModelList.get(position).getTicketPrice()+"Rs.";
        holder.fare.setText(fareString);
        holder.vehicleType.setText(routeModelList.get(position).getVehicleClass());
        holder.departureTime.setText(routeModelList.get(position).getTime());
        holder.seats.setText(routeModelList.get(position).getTotalSeats()+"");
        holder.linearBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectBusSeat.class);
                intent.putExtra("routeKey",routeModel.getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return routeModelList.size();
    }

    public class PVholder extends RecyclerView.ViewHolder {

        TextView tvDeparture, tvArrival, fare, vehicleType, departureTime, seats;
        LinearLayout linearBook;
        public PVholder(@NonNull View itemView) {
            super(itemView);
            tvDeparture = itemView.findViewById(R.id.departure);
            tvArrival = itemView.findViewById(R.id.arrival);
            fare = itemView.findViewById(R.id.fare);
            vehicleType = itemView.findViewById(R.id.vehicleType);
            departureTime = itemView.findViewById(R.id.time);
            seats = itemView.findViewById(R.id.seats);
            linearBook = itemView.findViewById(R.id.book);
        }
    }
}
