package com.example.sparelaptop3.xylockphone;

/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public class Note {
    private int duration; //TODO
    private int noteID;

    public Note(int noteID){
        this.noteID = noteID;
    }
    public Note(int noteID, int duration){
        this(noteID);
        this.duration = duration;
    }
    public int getNoteID(){
        return noteID;
    }


}
