package com.example.ruslan;

import android.support.v4.app.Fragment;

/**
 * Created by ruslan on 23.11.2015.
 */

public class NoteListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment () {

        return new NoteListFragment();
    }
}
