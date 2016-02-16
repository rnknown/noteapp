package com.example.ruslan.noteapp.database;

/**
 * Created by rusla on 10.02.2016.
 */
public class NoteDbSchema {
    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String PARTNER = "partner";
            public static final String PARTNER_ID = "partner_id";

        }
    }
}
