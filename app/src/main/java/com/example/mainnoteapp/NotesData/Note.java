package com.example.mainnoteapp.NotesData;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "notes")

public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    @ColumnInfo(name = "sub_title")
    public String subTitle;

    @ColumnInfo(name = "note_text")
    public String noteText;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "web_link")
    public String webLink;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTodaysCreationDate() {
        setDateWithCalendar(Calendar.getInstance());
    }

    public void setDate(Date date) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        setDateWithCalendar(dateCal);
    }
    public Date getDate() {
        long dateInMillisec = Long.parseLong(this.dateTime);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTimeInMillis(dateInMillisec);
        return dateCal.getTime();
    }

    private void setDateWithCalendar(Calendar dateCal) {
        long dateInMillisec = dateCal.getTimeInMillis();
        this.dateTime = Long.toString(dateInMillisec);
    }

    @NonNull
    @Override
    public String toString() {
        return title +  " : " + dateTime;
    }

}

