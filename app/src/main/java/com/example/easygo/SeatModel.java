package com.example.easygo;

public class SeatModel {
    int seatImg;
    String uId,seatId;
    boolean selected = false;
    String selectedBy;

    public String getSelectedBy() {
        return selectedBy;
    }

    public void setSelectedBy(String selectedBy) {
        this.selectedBy = selectedBy;
    }

    public SeatModel(int seatImg) {
        this.seatImg = seatImg;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getSeatImg() {
        return seatImg;
    }

    public void setSeatImg(int seatImg) {
        this.seatImg = seatImg;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
