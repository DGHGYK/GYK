package com.inwhiter.inviteapp.project.BusineesH;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by hatice on 02.08.2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
        return  dpd;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int day){
        // Do something with the chosen date
        TextView tv = (TextView) getActivity().findViewById(R.id.tv_bilgi_date);
        String Smonth = null;

        // Create a Date variable/object with user chosen date
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);
        Date chosenDate = cal.getTime();

        switch(month){
            case 0: Smonth="Ocak";
                break;
            case 1: Smonth="Şubat";
                break;
            case 2: Smonth="Mart";
                break;
            case 3: Smonth="Nisan";
                break;
            case 4: Smonth="Mayıs";
                break;
            case 5: Smonth="Haziran";
                break;
            case 6: Smonth="Temmuz";
                break;
            case 7: Smonth="Ağustos";
                break;
            case 8: Smonth="Eylül";
                break;
            case 9: Smonth="Ekim";
                break;
            case 10: Smonth="Kasım";
                break;
            case 11: Smonth="Aralık";
                break;


           
        }
        tv.setText(day+"  "+ Smonth +" , "+year);

    }
}

