package com.example.ruslan.noteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by rusla on 21.02.2016.
 */
public class ZoomDialogFragment extends DialogFragment{
    public static final String EXTRA_NOTE_IMAGE= "com.example.ruslan.noteapp.zoom";

    public static ZoomDialogFragment newInstance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NOTE_IMAGE, photoFile);

        ZoomDialogFragment fragment = new ZoomDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        File photoFile = (File)getArguments().getSerializable(EXTRA_NOTE_IMAGE);
        Bitmap image = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.zoom_fragment, null);

        ImageView imageView = (ImageView)v.findViewById(R.id.contact_image);
        imageView.setImageBitmap(image);

        return new AlertDialog.Builder(getActivity()).setView(imageView).create();
    }


}
