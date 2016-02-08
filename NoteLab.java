package com.example.ruslan.noteapp;

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
        for (int i = 1; i < 100; i++) {
            Note note = new Note();
            note.setTitle("Note #" + i);
            note.setSolved(i % 2 == 0);
            mNotes.add(note);
        }
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
