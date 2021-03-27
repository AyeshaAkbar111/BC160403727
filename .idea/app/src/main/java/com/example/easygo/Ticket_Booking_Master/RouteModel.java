package com.example.easygo.Ticket_Booking_Master;

public class RouteModel {
    String key, SelectVehicle, AddDepartureRoute, AddArrivalRoute, vehicleClass, date, time;
    double TicketPrice;
    double routePrice;
    int TotalSeats;
    long timestamp;

    public RouteModel(){}

    public RouteModel(String selectVehicle, String addDepartureRoute, String addArrivalRoute, String vehicleClass, String date, String time, double ticketPrice, int totalSeats) {
        SelectVehicle = selectVehicle;
        AddDepartureRoute = addDepartureRoute;
        AddArrivalRoute = addArrivalRoute;
        this.vehicleClass = vehicleClass;
        this.date = date;
        this.time = time;
        TicketPrice = ticketPrice;
        TotalSeats = totalSeats;
    }

    public double getRoutePrice() {
        return routePrice;
    }

    public void setRoutePrice(double routePrice) {
        this.routePrice = routePrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSelectVehicle() {
        return SelectVehicle;
    }

    public void setSelectVehicle(String selectVehicle) {
        SelectVehicle = selectVehicle;
    }

    public String getAddDepartureRoute() {
        return AddDepartureRoute;
    }

    public void setAddDepartureRoute(String addDepartureRoute) {
        AddDepartureRoute = addDepartureRoute;
    }

    public String getAddArrivalRoute() {
        return AddArrivalRoute;
    }

    public void setAddArrivalRoute(String addArrivalRoute) {
        AddArrivalRoute = addArrivalRoute;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        TicketPrice = ticketPrice;
    }

    public int getTotalSeats() {
        return TotalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        TotalSeats = totalSeats;
    }
}