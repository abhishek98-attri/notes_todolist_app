package com.example.mainnoteapp.NoteDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.NotesData.SaveNoteTask;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.commonutils.Constants;
import com.example.mainnoteapp.database.NotesDatabase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity implements SaveNoteTask.SaveNoteTaskCallback, ColorViewManager.ColorViewManagerCallback {

    private EditText inputNoteTitle , inputNoteSubTitle , inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ColorViewManager colorPickerManager;

    private String selectNoteColor;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        setupSubviews();
    }

    private void setupSubviews() {
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubTitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);

        Date now = new Date();
        String timeFormatted = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(now);
        textDateTime.setText(timeFormatted);

        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        selectNoteColor = Constants.DEFAULT_INDICATOR_COLOR;

        initMiscellaneous();
        setSubtitleIndicatorColor();
    }

    private void saveNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note title can't be empty!", Toast.LENGTH_SHORT).show();
            return;
      }else if (inputNoteSubTitle.getText().toString().trim().isEmpty()
                && inputNoteText.getText().toString().trim().isEmpty()){
        Toast.makeText(this,"Note can't be empty", Toast.LENGTH_SHORT).show();
       }

        final Note note = new Note();
        note.title = inputNoteTitle.getText().toString();
        note.subTitle = inputNoteSubTitle.getText().toString();
        note.noteText = inputNoteSubTitle.getText().toString();
        note.color = selectNoteColor;
        note.setTodaysCreationDate();

        SaveNoteTask saveNoteTask = new SaveNoteTask(this, this, note);
        saveNoteTask.execute();

    }

    public void noteSaved() {
        Intent intent = new Intent();
        setResult(RESULT_OK , intent);
        finish();
    }
     private void initMiscellaneous() {
         final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);

         colorPickerManager = new ColorViewManager(layoutMiscellaneous, CreateNoteActivity.this);
     }

     private void setSubtitleIndicatorColor() {
         GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
         gradientDrawable.setColor(Color.parseColor(selectNoteColor));
    }

    @Override
    public void applyColor(int color) {
        selectNoteColor = getResources().getString(color);
        setSubtitleIndicatorColor();
    }
}
