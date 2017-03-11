package com.example.sparelaptop3.xylockphone;

/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class Note {

    private byte noteNumber;
    private int duration;

    public Note(byte noteNum, int duration){
        this.noteNumber = noteNum;
        this.duration = duration;
    }

    public Note(byte noteNum){
        this.noteNumber = noteNum;
    }

    public byte getNote() {
        return noteNumber;
    }

}
