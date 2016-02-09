package com.example.ruslan.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ruslan on 23.11.2015.
 */

public class NoteLab {
    private static NoteLab sNoteLab;
    private List<Note> mNotes;

    public static NoteLab get (Context context)
    {
        if (sNoteLab == null)
            sNoteLab = new NoteLab(context);
        return sNoteLab;
    }

    private NoteLab(Context context)
    {
        mNotes = new ArrayList<>();
    }

    public void addNote (Note n) {
        mNotes.add(n);
    }

    public List<Note> getNotes()
    {
        return mNotes;
    }

    public Note getCrime (UUID id) {
        for (Note note : mNotes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }
}
