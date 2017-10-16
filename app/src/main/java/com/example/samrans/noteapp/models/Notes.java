package com.example.samrans.noteapp.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Samrans on 16-10-2017.
 */

public class Notes implements Serializable{
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=2;
    public static final int CHECKBOX_TYPE=1;

    String headerNote;
    String details;
    String colorNote;
    int NoteColor=-1;
    boolean oneField;

    public int type;
    boolean checkTypeNote;
    String image;
    boolean hasFinishedCheck;
    Date noteDate;

    public boolean isOneField() {
        return oneField;
    }

    public void setOneField(boolean oneField) {
        this.oneField = oneField;
    }
    public int getNoteColor() {
        return NoteColor;
    }

    public void setNoteColor(int noteColor) {
        NoteColor = noteColor;
    }
    public String getHeaderNote() {
        return headerNote;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setHeaderNote(String headerNote) {
        this.headerNote = headerNote;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isCheckTypeNote() {
        return checkTypeNote;
    }

    public void setCheckTypeNote(boolean checkTypeNote) {
        this.checkTypeNote = checkTypeNote;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isHasFinishedCheck() {
        return hasFinishedCheck;
    }

    public void setHasFinishedCheck(boolean hasFinishedCheck) {
        this.hasFinishedCheck = hasFinishedCheck;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteDates() {
        return noteDates;
    }

    public void setNoteDates(String noteDates) {
        this.noteDates = noteDates;
    }

    String noteDates;
}
