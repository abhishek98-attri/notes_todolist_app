package com.example.mainnoteapp.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.commonutils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView textTitle, textSubTitle,textDateTime, noteBodyText;
    LinearLayout layoutNote;

    View mainIndicatorNotesList;

    NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        textTitle = itemView.findViewById(R.id.textTitle);
        textSubTitle = itemView.findViewById(R.id.textSubTitle);
        textDateTime = itemView.findViewById(R.id.textDateTime);
        layoutNote = itemView.findViewById(R.id.layoutNote);
        noteBodyText = itemView.findViewById(R.id.noteBodyText);
        mainIndicatorNotesList = itemView.findViewById(R.id.mainIndicatorNotesList);

    }

    void setNote(Note note){
        if (note.getTitle().trim().isEmpty()) {
            textTitle.setText("No title");
        } else {
            textTitle.setText(note.getTitle().trim());
        }
        if (note.subTitle.trim().isEmpty()) {
            textSubTitle.setVisibility(View.GONE);
        } else {
            textSubTitle.setVisibility(View.VISIBLE);
            textSubTitle.setText(note.subTitle);
        }
        noteBodyText.setText(note.getNoteText().trim());
        if (note.getNoteText().trim().length() == 0) {
            noteBodyText.setVisibility(View.GONE);
        } else {
            noteBodyText.setVisibility(View.VISIBLE);
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