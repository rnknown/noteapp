package com.example.ruslan.noteapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by rusla on 06.02.2016.
 */

public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.ruslan.criminalintent.date";
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance (Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        final Date date = (Date)getArguments().getSerializable(ARG_DATE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        setDate(mDatePicker, date);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, updateDate(mDatePicker, date), EXTRA_DATE);
                    }
                })
                .create();
    }

    private void sendResult (int resultCode, Date date, String extra)
    {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(extra, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void setDate (DatePicker datePicker, Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, null);
    }

    private Date updateDate (DatePicker datePicker, Date d) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        Date date = new GregorianCalendar(year, month, day, calendar.HOUR, calendar.MINUTE)
                .getTime();
        return date;
    }
}
