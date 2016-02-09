package com.example.ruslan.noteapp;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ruslan on 22.11.2015.
 */

public class Note {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Note() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String formatDate() {
        DateFormat df = new DateFormat();
        return df.format("k:mm, EEEE, d MMM yyyy", mDate).toString();
    }

    public String formatDatePicker() {
        DateFormat df = new DateFormat();
        return df.format("EEEE, d MMM yyyy", mDate).toString();
    }

    public String formatTimePicker() {
        DateFormat df = new DateFormat();
        return df.format("k:mm", mDate).toString();
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
