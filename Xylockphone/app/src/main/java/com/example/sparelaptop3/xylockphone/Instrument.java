package com.example.sparelaptop3.xylockphone;

/**
 * Created by SPARELAPTOP1 on 10/03/2017.
 */

public abstract class Instrument {
    private String name;
    public abstract void Play(Note note);

    public String getName(){
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String noteIDToName(int noteID){
        return "A";
    }
}
