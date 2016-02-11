package com.example.ruslan.noteapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.ruslan.noteapp.Note;
import com.example.ruslan.noteapp.database.NoteDbSchema.NoteTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rusla on 10.02.2016.
 */
public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(NoteTable.Cols.SOLVED));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setDate(new Date(date));
        note.setSolved(isSolved != 0);

        return note;
    }

}
