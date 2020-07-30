package com.example.mainnoteapp.listeners;

import com.example.mainnoteapp.NotesData.Note;

public interface NotesListener {

    void onNoteClicked(Note note, int position);

    void onNoteLongPressed(Note note, int position);
}
