package com.example.ruslan.noteapp;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ruslan on 22.11.2015.
 */

public class NoteFragment extends android.support.v4.app.Fragment {
    private Note mNote;
    private EditText mTitleField;
    private Button mTimeButton;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mSharingButton;
    private Button mPartnerButton;
    private Button mCallPartnerButton;
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    public static final int REQUEST_CONTACT = 2;

    public static NoteFragment newInstance (UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        NoteLab.get(getActivity()).updateNote(mNote);
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID noteId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteId);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        mTitleField = (EditText)v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTimeButton = (Button)v.findViewById(R.id.note_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mNote.getDate());
                dialog.setTargetFragment(NoteFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });
        mDateButton = (Button)v.findViewById(R.id.note_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mNote.getDate());
                dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.note_solved);
        mSolvedCheckBox.setChecked(mNote.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //set the note solved
                mNote.setSolved(b);
            }
        });

        mSharingButton = (Button)v.findViewById(R.id.note_sharing);
        mSharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getNoteSharing())
                        .setSubject(getString(R.string.note_sharing_partner))
                        .setChooserTitle(getString(R.string.send_note))
                        .createChooserIntent();
                startActivity(i);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mPartnerButton = (Button)v.findViewById(R.id.note_partner);
        mPartnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        mCallPartnerButton = (Button)v.findViewById(R.id.note_partner_call);
        mCallPartnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selectClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";

                String[] fields = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                String[] selectParams = {Long.toString(mNote.getPartnerId())};

                Cursor cursor = getActivity().getContentResolver().query(contentUri,
                        fields, selectClause, selectParams, null);

                if(cursor != null && cursor.getCount() > 0) {
                    try {
                        cursor.moveToFirst();
                        String number = cursor.getString(0);
                        Uri phoneNumber = Uri.parse("tel:" + number);
                        Intent intent = new Intent(Intent.ACTION_DIAL, phoneNumber);
                        startActivity(intent);
                    }
                    finally {
                        cursor.close();
                    }
                }
            }
        });

        if (mNote.getPartner() != null) {
            updateCallButton();
            mPartnerButton.setText(mNote.getPartner());
        } else {
            mCallPartnerButton.setEnabled(false);
        }

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) ==
                null) {
            mPartnerButton.setEnabled(false);
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNote.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mNote.setDate(date);
            updateTime();
        }
        else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts._ID
            };
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null,
                    null, null);
            try {
                if (c.getCount() == 0) {
                    return;
                }
                c.moveToFirst();
                String partner = c.getString(0);
                long partnerId = c.getLong(1);
                mNote.setPartner(partner);
                mNote.setPartnerId(partnerId);
                mPartnerButton.setText(partner);
                updateCallButton();
            } finally {
                c.close();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:
                NoteLab.get(getActivity()).deleteNote(mNote);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDate() {
        mDateButton.setText(mNote.formatDatePicker().toString());
    }

    private void updateTime() {
        mTimeButton.setText(mNote.formatTimePicker().toString());
    }

    private String getNoteSharing() {
        String solvedNote;

        if (mNote.isSolved()) {
            solvedNote = getString(R.string.note_solved);
        } else {
            solvedNote = getString(R.string.note_unsolved);
        }

        String dateString = mNote.formatDatePicker().toString();

        String partner = mNote.getPartner();
        if (partner == null) {
            partner = getString(R.string.note_sharing_no_partner);
        } else {
            partner = getString(R.string.note_sharing_partner, partner);
        }

        String sharing = getString(R.string.note_sharing, mNote.getTitle(), dateString, solvedNote,
                partner);

        return sharing;
    }

    private void updateCallButton() {
        mCallPartnerButton.setEnabled(true);
        String notePartnerCall = getString(R.string.note_partner_call_text);
        notePartnerCall = String.format(notePartnerCall, mNote.getPartner());
        mCallPartnerButton.setText(notePartnerCall);
    }
}
