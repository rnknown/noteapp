package com.example.ruslan.noteapp;

import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * Created by ruslan on 23.11.2015.
 */

public class NoteListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment () {

        return new NoteListFragment();
    }
}
