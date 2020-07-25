package com.example.mainnoteapp.NotesData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.mainnoteapp.database.NotesDatabase;

public  class SaveNoteTask extends AsyncTask<Void, Void , Void> {

    private SaveNoteTaskCallback mCallback;
    private Context mContext;
    private Note mNote;

    public SaveNoteTask(SaveNoteTaskCallback callback, Context context, Note noteToSave) {
        this.mCallback = callback;
        this.mContext = context;
        this.mNote = noteToSave;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        NotesDatabase.getNotesDatabase(mContext).noteDao().insertnote(mNote);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.noteSaved();
    }

    public interface SaveNoteTaskCallback {
        void noteSaved();
    }
}

