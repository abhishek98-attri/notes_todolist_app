package com.example.mainnoteapp.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mainnoteapp.NoteDetail.CreateNoteActivity;
import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.adapters.NotesAdapter;
import com.example.mainnoteapp.commonutils.Constants;
import com.example.mainnoteapp.database.NotesDatabase;
import com.example.mainnoteapp.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            }
        });
        notesRecyclerView = findViewById(R.id.notesRecylerView);

        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this, this);
        notesRecyclerView.setAdapter(notesAdapter);

        EditText inputSearch = findViewById(R.id.inputsearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                notesAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (noteList.size() != 0){
                    notesAdapter.searchNotes(s.toString());
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotes();
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra(Constants.INTENT_KEY_IS_NOTE_EDIT, true);
        intent.putExtra("note", note);
        startActivity(intent);

    }

    private void getNotes(){
        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getApplicationContext())
                        .noteDao().getAllNotes();

            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                noteList.clear();
                if (noteList.size()==0) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                } else {
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemChanged(0);
                }
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNoteTask().execute();
    }

}
