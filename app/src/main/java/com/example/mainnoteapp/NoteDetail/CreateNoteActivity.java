package com.example.mainnoteapp.NoteDetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainnoteapp.NotesData.Note;
import com.example.mainnoteapp.NotesData.SaveNoteTask;
import com.example.mainnoteapp.R;
import com.example.mainnoteapp.commonutils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity implements SaveNoteTask.SaveNoteTaskCallback, ColorViewManager.ColorViewManagerCallback {

    private EditText inputNoteTitle , inputNoteSubTitle , inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ColorViewManager colorPickerManager;
    private String selectNoteColor;
    private Note alreadyAvailableNote;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        setupSubviews();
    }

    @Override
    public void onBackPressed() {
        saveNote();
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
        setTextDateLabel(now);


        selectNoteColor = Constants.DEFAULT_INDICATOR_COLOR;

        if (getIntent() !=null) {
            if (getIntent().getBooleanExtra(Constants.INTENT_KEY_IS_NOTE_EDIT, false)){
                alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
                setViewOrUpdateNote();
            }
        }

        initMiscellaneous();
        setSubtitleIndicatorColor();
        setupEditText();
    }

    private void setTextDateLabel(Date date) {
        String timeFormatted = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(date);
        textDateTime.setText(timeFormatted);
    }

    private void setupEditText() {

        inputNoteText.setLinksClickable(true);
        inputNoteText.setAutoLinkMask(Linkify.WEB_URLS);
        inputNoteText.setMovementMethod(CustomeMovementMethod.getInstance());
        //If the edit text contains previous text with potential links
        Linkify.addLinks(inputNoteText, Linkify.WEB_URLS);

        inputNoteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);

            }

        });
    }

    private void setViewOrUpdateNote() {
        inputNoteTitle.setText(alreadyAvailableNote.getTitle());
        inputNoteSubTitle.setText(alreadyAvailableNote.getSubTitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        setTextDateLabel(alreadyAvailableNote.getDate());
        selectNoteColor = alreadyAvailableNote.color;

    }

   private void saveNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty() &&
            inputNoteSubTitle.getText().toString().trim().isEmpty() &&
            inputNoteText.getText().toString().trim().isEmpty()) {

            finish();
            return;

        }

        final Note note = new Note();
        note.setTitle( inputNoteTitle.getText().toString());
        note.setSubTitle(inputNoteSubTitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.color = selectNoteColor;
        note.setTodaysCreationDate();

        if (alreadyAvailableNote != null) {
            note.setId(alreadyAvailableNote.getId());
        }

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
