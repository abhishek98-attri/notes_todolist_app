package com.example.mainnoteapp.adapters;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.commonutils.Constants;
import com.example.mainnoteapp.listeners.NotesListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> notes;
    private NotesListener notesListener;

    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
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
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textSubTitle,textDateTime;
        LinearLayout layoutNote;
        View mainIndicatorNotesList;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubTitle = itemView.findViewById(R.id.textSubTitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            mainIndicatorNotesList = itemView.findViewById(R.id.mainIndicatorNotesList);

        }

        void setNote(Note note){
            textTitle.setText(note.title);
            if (note.subTitle.trim().isEmpty()) {
                textSubTitle.setVisibility(View.GONE);
            } else {
                textSubTitle.setText(note.subTitle);
            }
            Date noteCreationDate = note.getDate();
            String timeFormatted = new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(noteCreationDate);
            textDateTime.setText(timeFormatted);

            GradientDrawable gradientDrawable = (GradientDrawable) mainIndicatorNotesList.getBackground();
            if (note.color != null) {
                gradientDrawable.setColor(Color.parseColor(note.color));
            }else {
                gradientDrawable.setColor(Color.parseColor(Constants.DEFAULT_INDICATOR_COLOR));
            }
        }
    }

}
