package com.example.ruslan.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by ruslan on 23.11.2015.
 */

public class NoteListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.note_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI () {
        NoteLab noteLab = NoteLab.get(getActivity());
        List<Note> notes = noteLab.getNotes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(notes);
        }
            mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Note mNote;

        public UUID selectedItemId() {
            return mNote.getId();
        }

        public void bindCrime (Note note) {
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());
            mDateTextView.setText(mNote.formatDate());
            mSolvedCheckBox.setChecked(mNote.isSolved());
        }

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_note_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_note_date_text_view);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_note_solved_check_box);
        }

        @Override
        public void onClick (View v) {
            Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getId());
            startActivity(intent);
            mAdapter.notifyItemChanged(mAdapter.mNotes.indexOf(mNote));
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Note> mNotes;
        public CrimeAdapter (List<Note> notes) {
            mNotes = notes;
        }

        @Override
        public CrimeHolder onCreateViewHolder (ViewGroup parent, int type) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_note, parent,
                    false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder (CrimeHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bindCrime(note);
        }

        @Override
        public int getItemCount () {
            return mNotes.size();
        }
    }
}
