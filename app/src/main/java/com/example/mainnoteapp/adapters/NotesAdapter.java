package com.example.mainnoteapp.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainnoteapp.Home.MainActivity;
import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.database.NotesDatabase;
import com.example.mainnoteapp.listeners.NotesListener;

import java.util.List;
import java.util.Timer;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> notes;
    private NotesListener notesListener;
    private Timer timer;
    private List<Note> notesSource;
    private Context mContext;

    public NotesAdapter(List<Note> notes, Context context, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        this.mContext = context;
        notesSource = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
            holder.setNote(notes.get(position));
            holder.layoutNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notesListener.onNoteClicked(notes.get(position), position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    notesListener.onNoteLongPressed(notes.get(position), position);
                    return true;
                }
            });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void searchNotes(final String searchkeyword) {
        getNotesForSearch(searchkeyword);
    }

    private void getNotesForSearch(final String search){
        @SuppressLint("StaticFieldLeak")
        class GetNoteForSearchTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                if (search.length() == 0) {
                    return notesSource;
                } else {
                    String searchKey = "%" + search + "%";
                    return NotesDatabase
                            .getNotesDatabase(mContext)
                            .noteDao().getAllNotesforSearch(searchKey);
                }

            }

            @Override
            protected void onPostExecute(List<Note> notesListNew) {
                super.onPostExecute(notes);

                notes = notesListNew;
                notifyDataSetChanged();
            }
        }
        new GetNoteForSearchTask().execute();
    }

}
