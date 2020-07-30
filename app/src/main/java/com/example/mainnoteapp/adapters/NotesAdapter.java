package com.example.mainnoteapp.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
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
import com.example.mainnoteapp.database.NotesDatabase;
import com.example.mainnoteapp.listeners.NotesListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

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
            layoutNote = itemView.findViewById(R.id.layoutNote);
            mainIndicatorNotesList = itemView.findViewById(R.id.mainIndicatorNotesList);

        }

        void setNote(Note note){
            textTitle.setText(note.getTitle());
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
//    public void searchNotes(final String searchkeyword) {
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (searchkeyword.trim().isEmpty()){
//                    notes = notesSource;
//                } else {
//                    ArrayList<Note> temp = new ArrayList<>();
//                    for (Note note : notesSource) {
//                        if (note.getTitle().toLowerCase().contains(searchkeyword.toLowerCase())
//                                || note.getSubTitle().toLowerCase().contains(searchkeyword.toLowerCase())
//                                || note.getNoteText().toLowerCase().contains(searchkeyword.toLowerCase())){
//                            temp.add(note);
//                        }
//                    }
//                    notes = temp;
//                }
//                new Handler(Looper.getMainLooper()).post(new Runnable()  {
//                    @Override
//                            public void run() {
//                        notifyDataSetChanged();
//                    }
//                });
//
//            }
//        }, 300);
//    }
//    public void cancelTimer() {
//        if (timer != null){
//            timer.cancel();
//        }
//    }

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
