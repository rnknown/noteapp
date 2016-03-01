package com.example.ruslan.noteapp;

import android.content.Context;
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
    private String mPartner;
    private long mPartnerId;

    public long getPartnerId() {
        return mPartnerId;
    }

    public void setPartnerId(long partnerId) {
        mPartnerId = partnerId;
    }

    public String getPartner() {
        return mPartner;
    }

    public void setPartner(String partner) {
        mPartner = partner;
    }

    public Note() {
        // Generate unique identifier
        this(UUID.randomUUID());
    }

    public Note(UUID id) {
        mId = id;
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

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

}
