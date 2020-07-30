package com.example.mainnoteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mainnoteapp.NotesData.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY date_time DESC")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertnote(Note note);

    @Delete
    void deleteNote(Note note);


    @Query("SELECT * FROM notes WHERE title LIKE :searchkey or sub_title LIKE :searchkey or note_text LIKE :searchkey ORDER BY date_time DESC")
    List<Note> getAllNotesforSearch(String searchkey);
}
