package com.example.easygo.Passengers_UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.easygo.Login;
import com.example.easygo.R;

public class SelectRoute extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TextView date_picker;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        initDatePicker();
        date_picker=findViewById(R.id.date_picker);
        date_picker.setText(getTodayDate());

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTodayDate() {
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date= makeDateString(dayOfMonth,month,year);
                date_picker.setText(date);

            }
        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int style= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
    String makeDateString(int day,int month,int year){
        return getMonthFormat(month)+ " " + day + " "+year;
    }

    private String getMonthFormat(int month) {

        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return null;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), UserHome.class));
    }

    public void BookMe(View view) {
        startActivity(new Intent(getApplicationContext(), SelectSeat.class));
    }
}