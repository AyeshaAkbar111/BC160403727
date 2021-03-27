package com.example.easygo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HelperClass {
    public static String convertDate(String date, String inputF, String outputF){
        String newDate="";
        SimpleDateFormat input = new SimpleDateFormat(inputF);
        SimpleDateFormat output = new SimpleDateFormat(outputF);
        try {
            Date oneWayTripDate = input.parse(date);
            newDate = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    public static void datePicker(final TextView edittext, Context context){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        edittext.setText(dateFormat.format(newDate.getTime()));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
